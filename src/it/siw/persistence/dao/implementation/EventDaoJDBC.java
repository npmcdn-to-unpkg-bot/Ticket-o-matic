package it.siw.persistence.dao.implementation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Event;
import it.siw.model.EventCategory;
import it.siw.model.Guest;
import it.siw.model.User;
import it.siw.persistence.DAOUtility;
import it.siw.persistence.dao.EventDAO;

public class EventDaoJDBC implements EventDAO {
    HikariDataSource datasource;

    public EventDaoJDBC(HikariDataSource datasource) {
	this.datasource = datasource;
    }

    @Override
    public boolean create(Event modelObject) {
	int operations = 4;
	Connection connection = null;
	String[] query = new String[operations];
	query[0] = "insert into Event(Name,Location,Date,Description,Category_id,Organizer_id,Image) values(?,?,?,?,?,?,?) RETURNING idevent";
	query[1] = "insert into ticket(price,ticketcategory_id,event_id) VALUES(?,?,?) RETURNING idticket";
	query[2] = "insert into usersellticket(user_id,ticket_id,price) VALUES(?,?,?)";
	query[3] = "select guestid from check_guest(?,?,?) as guestid";
	PreparedStatement[] statement = new PreparedStatement[operations];
	ResultSet[] result = new ResultSet[2];
	try {
	    connection = datasource.getConnection();
	    for (int i = 0; i < operations; i++) {
		statement[i] = connection.prepareStatement(query[i]);
	    }
	    statement[0].setString(1, modelObject.getName());
	    statement[0].setString(2, modelObject.getLocation());
	    statement[0].setDate(3, java.sql.Date.valueOf(modelObject.getDate()));
	    statement[0].setString(4, modelObject.getDescription());
	    statement[0].setInt(5, modelObject.getCategory().getId());
	    statement[0].setInt(6, modelObject.getOrganizer().getId());
	    statement[0].setString(7, modelObject.getImage());
	    result[0] = statement[0].executeQuery();
	    if (result[0].next()) {
		modelObject.setId(result[0].getInt(1));
		for (Integer ticket_id : modelObject.getTicket().keySet()) {
		    statement[1].setFloat(1, modelObject.getTicket().get(ticket_id).getPrice());
		    statement[1].setInt(2, modelObject.getTicket().get(ticket_id).getCategory().getId());
		    statement[1].setFloat(3, modelObject.getId());
		    result[1] = statement[1].executeQuery();
		    if (result[1].next()) {
			statement[2].setInt(1, modelObject.getOrganizer().getId());
			statement[2].setInt(2, result[1].getInt(1));
			statement[2].setFloat(3, modelObject.getTicket().get(ticket_id).getPrice());
			statement[2].executeUpdate();
		    }
		}
		for (Integer guest_id : modelObject.getGuests().keySet()) {
		    statement[3].setString(1, modelObject.getGuests().get(guest_id).getName());
		    statement[3].setString(2, modelObject.getGuests().get(guest_id).getImage());
		    statement[3].setInt(3, modelObject.getId());
		    statement[3].executeQuery();
		}
	    }
	    return true;

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    for (int i = 0; i < operations; i++) {
		DAOUtility.close(statement[i]);
	    }
	    DAOUtility.close(result[0]);
	    DAOUtility.close(result[1]);
	}
	return false;

    }

    @Override
    public void delete(Event modelObject) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = datasource.getConnection();
	    query = "Delete From Event WHERE idEvent = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, modelObject.getId());
	    statement.executeUpdate();

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}

    }

    @Override
    public Map<Integer, Event> findByCategory(int category_id, int limit, int offset) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	CallableStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "SELECT *,cat.name as category FROM event_by_category(?,?,?) as s "
		    + "JOIN event as e on s = e.idevent "
		    + "JOIN eventcategory as cat on e.category_id = cat.ideventcategory";
	    statement = connection.prepareCall(query);
	    statement.setInt(1, category_id);
	    statement.setInt(2, limit);
	    statement.setInt(3, offset);
	    result = statement.executeQuery();
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("idEvent"));
		event.setName(result.getString("Name"));
		event.setLocation(result.getString("Location"));
		event.setImage(result.getString("image"));
		event.setDate(result.getDate("Date").toLocalDate());
		event.setSuspended(result.getBoolean("Suspended"));
		EventCategory category = new EventCategory();
		category.setId(result.getInt("idEventCategory"));
		category.setName(result.getString("category"));
		event.setCategory(category);
		events.put(event.getId(), event);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return events;
    }

    @Override
    public Map<Integer, Event> findByGuest(String guest, int limit, int offset) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "SELECT e.idevent as id,e.name as name,e.location,e.date,e.image,ecat.name as category "
		    + "FROM event_by_guest(?,?,?) as s " + "JOIN event as e ON s = e.idevent "
		    + "JOIN eventcategory as ecat ON e.category_id = ecat.ideventcategory";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, guest);
	    statement.setInt(2, limit);
	    statement.setInt(3, offset);
	    result = statement.executeQuery();
	    int i = 1;
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("id"));
		event.setName(result.getString("Name"));
		event.setLocation(result.getString("location"));
		event.setImage(result.getString("image"));
		event.setDate(result.getDate("Date").toLocalDate());
		EventCategory category = new EventCategory();
		category.setName(result.getString("category"));
		event.setCategory(category);
		events.put(i, event);
		i++;
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return events;
    }

    @Override
    public Map<Integer, Event> findByDate(LocalDate date, int limit, int offset) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "SELECT e.idevent as id,e.name as name,e.location,e.date,e.image,ecat.name as category "
		    + "FROM event_by_date(?,?,?) as s " + "JOIN event as e ON s = e.idevent "
		    + "JOIN eventcategory as ecat ON e.category_id = ecat.ideventcategory";
	    statement = connection.prepareStatement(query);
	    statement.setDate(1, Date.valueOf(date));
	    statement.setInt(2, limit);
	    statement.setInt(3, offset);
	    result = statement.executeQuery();
	    int i = 1;
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("id"));
		event.setName(result.getString("Name"));
		event.setLocation(result.getString("location"));
		event.setImage(result.getString("image"));
		event.setDate(result.getDate("Date").toLocalDate());
		EventCategory category = new EventCategory();
		category.setName(result.getString("category"));
		event.setCategory(category);
		events.put(i, event);
		i++;
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return events;
    }

    @Override
    public Event findById(Integer id) {
	Event event = null;
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select e.*,u.username,ecat.name as category,idguest,g.name as gname,g.image as gimage "
		    + "from event as e join users as u on e.organizer_id = u.iduser "
		    + "join eventcategory as ecat on e.category_id = ecat.ideventcategory "
		    + "join event_has_guest as ehg on e.idevent=ehg.event_idevent "
		    + "join guest as g on ehg.guest_idguest=g.idguest " + "where idevent = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    Guest guest;
	    if (result.next()) {
		event = new Event();
		event.setId(result.getInt("idevent"));
		event.setName(result.getString("name"));
		event.setDescription(result.getString("description"));
		event.setDate(result.getDate("date").toLocalDate());
		event.setLocation(result.getString("location"));
		event.setImage(result.getString("image"));
		User user = new User();
		user.setUsername(result.getString("username"));
		event.setOrganizer(user);
		EventCategory category = new EventCategory();
		category.setName(result.getString("category"));
		event.setCategory(category);
		event.setGuests(new HashMap<>());
		guest = new Guest();
		guest.setId(result.getInt("idguest"));
		guest.setName(result.getString("gname"));
		guest.setImage(result.getString("gimage"));
		event.getGuests().put(guest.getId(), guest);
	    }
	    while (result.next()) {
		guest = new Guest();
		guest.setId(result.getInt("idguest"));
		guest.setName(result.getString("gname"));
		guest.setImage(result.getString("gimage"));
		event.getGuests().put(guest.getId(), guest);
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return event;
    }

    @Override
    public Map<Integer, Event> findByLocation(String location, int limit, int offset) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "SELECT e.idevent as id,e.name as name,e.location,e.date,e.image,ecat.name as category "
		    + "FROM event_by_location(?,?,?) as s " + "JOIN event as e ON s = e.idevent "
		    + "JOIN eventcategory as ecat ON e.category_id = ecat.ideventcategory";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, location);
	    statement.setInt(2, limit);
	    statement.setInt(3, offset);
	    result = statement.executeQuery();
	    int i = 1;
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("id"));
		event.setName(result.getString("Name"));
		event.setLocation(result.getString("location"));
		event.setImage(result.getString("image"));
		event.setDate(result.getDate("Date").toLocalDate());
		EventCategory category = new EventCategory();
		category.setName(result.getString("category"));
		event.setCategory(category);
		events.put(i, event);
		i++;
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return events;
    }

    @Override
    public Map<Integer, Event> findByName(String name, int limit, int offset) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "SELECT e.idevent as id,e.name as name,e.location,e.date,e.image,ecat.name as category "
		    + "FROM event_by_title(?,?,?) as s " + "JOIN event as e ON s = e.idevent "
		    + "JOIN eventcategory as ecat ON e.category_id = ecat.ideventcategory";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, name);
	    statement.setInt(2, limit);
	    statement.setInt(3, offset);
	    result = statement.executeQuery();
	    int i = 1;
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("id"));
		event.setName(result.getString("Name"));
		event.setLocation(result.getString("location"));
		event.setImage(result.getString("image"));
		event.setDate(result.getDate("Date").toLocalDate());
		EventCategory category = new EventCategory();
		category.setName(result.getString("category"));
		event.setCategory(category);
		events.put(i, event);
		i++;
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return events;
    }

    @Override
    public Map<Integer, Event> findByOrganizer(User u) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "SELECT e.idevent,e.name,e.date,e.location,e.image,e.suspended FROM event as e WHERE e.organizer_id = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, u.getId());
	    result = statement.executeQuery();

	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("idevent"));
		event.setName(result.getString("name"));
		event.setDate(result.getDate("date").toLocalDate());
		event.setLocation(result.getString("location"));
		event.setImage(result.getString("image"));
		event.setSuspended(result.getBoolean("suspended"));
		events.put(event.getId(), event);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return events;

    }

    @Override
    public Map<Integer, Event> findByPrice(float min, float max, int limit, int offset) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "SELECT e.idevent as id,e.name as name,e.location,e.date,e.image,ecat.name as category "
		    + "FROM event_by_price(?,?,?,?) as s " + "JOIN event as e ON s = e.idevent "
		    + "JOIN eventcategory as ecat ON e.category_id = ecat.ideventcategory";
	    statement = connection.prepareStatement(query);
	    statement.setFloat(1, min);
	    statement.setFloat(2, max);
	    statement.setInt(3, limit);
	    statement.setInt(4, offset);
	    result = statement.executeQuery();
	    int i = 1;
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("id"));
		event.setName(result.getString("Name"));
		event.setLocation(result.getString("location"));
		event.setImage(result.getString("image"));
		event.setDate(result.getDate("Date").toLocalDate());
		EventCategory category = new EventCategory();
		category.setName(result.getString("category"));
		event.setCategory(category);
		events.put(i, event);
		i++;
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return events;
    }

    @Override
    public Map<Integer, Event> findTop() {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "SELECT e.idevent,e.name,e.image FROM top_events() as t join event as e on t.event_id = e.idevent "
		    + "WHERE e.date >= now() ";
	    statement = connection.prepareStatement(query);
	    result = statement.executeQuery();
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("idevent"));
		event.setName(result.getString("name"));
		event.setImage(result.getString("image"));
		events.put(event.getId(), event);
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
	return events;
    }

    @Override
    public void update(Event modelObject) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = datasource.getConnection();
	    query = "Update Event SET Name=?, Location=?,Date=?,Suspended=?,Category_id=?,Organizer_id=?,Image=? WHERE idEvent = ?";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, modelObject.getName());
	    statement.setString(2, modelObject.getLocation());
	    statement.setDate(3, new java.sql.Date(modelObject.getDate().toEpochDay()));
	    statement.setBoolean(4, modelObject.getSuspended());
	    statement.setInt(5, modelObject.getCategory().getId());
	    statement.setInt(6, modelObject.getOrganizer().getId());
	    statement.setString(7, modelObject.getImage());
	    statement.setInt(8, modelObject.getId());
	    statement.executeUpdate();

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}

    }

    @Override
    public boolean suspend(Event event, User user) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = datasource.getConnection();
	    query = "Update Event SET Suspended=? WHERE idEvent = ? AND organizer_id = ?";
	    statement = connection.prepareStatement(query);
	    statement.setBoolean(1, !event.getSuspended());
	    statement.setInt(2, event.getId());
	    statement.setInt(3, user.getId());
	    return (statement.executeUpdate() > 0) ? true : false;

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
	return false;
    }

}
