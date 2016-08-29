package it.siw.persistence.dao.implementation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Event;
import it.siw.model.EventCategory;
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
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = datasource.getConnection();
	    query = "insert into Event(Name,Location,Date,Description,Suspended,Category_id,Organizer_id,Image) values(?,?,?,?,?,?,?,?)";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, modelObject.getName());
	    statement.setString(2, modelObject.getLocation());
	    long time = modelObject.getDate().toEpochDay();
	    statement.setDate(3, new java.sql.Date(time));
	    statement.setString(4, modelObject.getDescription());
	    statement.setBoolean(5, modelObject.getSuspended());
	    statement.setInt(6, modelObject.getCategory().getId());
	    statement.setInt(7, modelObject.getOrganizer().getId());
	    statement.setString(8, modelObject.getImage());
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

    @Override
    public Event findById(Integer id) {
	Event event = null;
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select * from event where idevent = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    while (result.next()) {
		event = new Event();
		event.setId(result.getInt("idevent"));
		event.setName(result.getString("name"));
		event.setDescription(result.getString("description"));
		event.setDate(result.getDate("date").toLocalDate());
		event.setLocation(result.getString("location"));
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
    public Event findGuest(Integer id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Event findTicket(Integer id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Event> findByName(String name) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select E.idEvent,E.Name,E.Location,E.Description,E.Date,E.Suspended,EC.idEventCategory,EC.Name as categoryname,U.idUser,U.Username,E.Image ";
	    query += "FROM Event as E ";
	    query += "INNER JOIN EventCategory as EC ON E.Category_id=EC.idEventCategory ";
	    query += "INNER JOIN User as U ON E.Organizer_id=U.idUser ";
	    query += "WHERE E.Name LIKE ?";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, name);
	    result = statement.executeQuery();
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("idEvent"));
		event.setName(result.getString("Name"));
		event.setLocation(result.getString("Location"));
		event.setDescription(result.getString("Description"));
		event.setDate(result.getDate("Date").toLocalDate());
		event.setSuspended(result.getBoolean("Suspended"));
		EventCategory category = new EventCategory();
		category.setId(result.getInt("idEventCategory"));
		category.setName(result.getString("categoryname"));
		event.setCategory(category);
		User organizer = new User();
		organizer.setId(result.getInt("idUser"));
		organizer.setUsername(result.getString("Username"));
		event.setOrganizer(organizer);
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
    public Map<Integer, Event> findByDate(Date date) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select E.idEvent,E.Name,E.Location,E.Date,E.Suspended,EC.idEventCategory,EC.Name,U.idUser,U.Username,E.Image ";
	    query += "FROM Event as E ";
	    query += "INNER JOIN EventCategory as EC ON E.Category_id=EC.idEventCategory ";
	    query += "INNER JOIN User as U ON E.Organizer_id=U.idUser ";
	    query += "WHERE E.Date LIKE ?";
	    statement = connection.prepareStatement(query);
	    statement.setDate(1, new java.sql.Date(date.getTime()));
	    result = statement.executeQuery();
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("E.idEvent"));
		event.setName(result.getString("E.Name"));
		event.setLocation(result.getString("E.Location"));
		event.setDate(result.getDate("E.Date").toLocalDate());
		event.setSuspended(result.getBoolean("E.Suspended"));
		EventCategory category = new EventCategory();
		category.setId(result.getInt("EC.idEventCategory"));
		category.setName(result.getString("EC.Name"));
		event.setCategory(category);
		User organizer = new User();
		organizer.setId(result.getInt("U.idUser"));
		organizer.setUsername(result.getString("U.Username"));
		event.setOrganizer(organizer);
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
    public Map<Integer, Event> findByPrice(float min, float max) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select E.idEvent,E.Name,E.Location,E.Date,E.Suspended,EC.idEventCategory,EC.Name,U.idUser,U.Username,E.Image,S.Price ";
	    query += "FROM Event as E ";
	    query += "INNER JOIN Ticket as T ON E.idEvent=T.Event_id ";
	    query += "INNER JOIN UserSellTicket as S ON T.idTicket=S.Ticket_it ";
	    query += "INNER JOIN EventCategory as EC ON E.Category_id=EC.idEventCategory ";
	    query += "INNER JOIN User as U ON E.Organizer_id=U.idUser ";
	    query += "WHERE S.Price >= ? || S.Price <= ?";
	    statement = connection.prepareStatement(query);
	    statement.setFloat(1, min);
	    statement.setFloat(2, max);
	    result = statement.executeQuery();
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("E.idEvent"));
		event.setName(result.getString("E.Name"));
		event.setLocation(result.getString("E.Location"));

		event.setDate(result.getDate("E.Date").toLocalDate());
		event.setSuspended(result.getBoolean("E.Suspended"));
		EventCategory category = new EventCategory();
		category.setId(result.getInt("EC.idEventCategory"));
		category.setName(result.getString("EC.Name"));
		event.setCategory(category);
		User organizer = new User();
		organizer.setId(result.getInt("U.idUser"));
		organizer.setUsername(result.getString("U.Username"));
		event.setOrganizer(organizer);
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
    public Map<Integer, Event> findByLocation(String location) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select E.idEvent,E.Name,E.Location,E.Date,E.Suspended,EC.idEventCategory,EC.Name,U.idUser,U.Username,E.Image ";
	    query += "FROM Event as E ";
	    query += "INNER JOIN EventCategory as EC ON E.Category_id=EC.idEventCategory ";
	    query += "INNER JOIN User as U ON E.Organizer_id=U.idUser ";
	    query += "WHERE E.Location LIKE ?";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, location);
	    result = statement.executeQuery();
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("E.idEvent"));
		event.setName(result.getString("E.Name"));
		event.setLocation(result.getString("E.Location"));

		event.setDate(result.getDate("E.Date").toLocalDate());
		event.setSuspended(result.getBoolean("E.Suspended"));
		EventCategory category = new EventCategory();
		category.setId(result.getInt("EC.idEventCategory"));
		category.setName(result.getString("EC.Name"));
		event.setCategory(category);
		User organizer = new User();
		organizer.setId(result.getInt("U.idUser"));
		organizer.setUsername(result.getString("U.Username"));
		event.setOrganizer(organizer);
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
    public Map<Integer, Event> findByGuest(String guest) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select E.idEvent,E.Name,E.Location,E.Date,E.Suspended,EC.idEventCategory,EC.Name,U.idUser,U.Username,E.Image ";
	    query += "FROM Event as E ";
	    query += "INNER JOIN EventCategory as EC ON E.Category_id=EC.idEventCategory ";
	    query += "INNER JOIN User as U ON E.Organizer_id=U.idUser ";
	    query += "INNER JOIN Event_has_Guest as HG ON E.idEvent=HG.Event_idEvent ";
	    query += "INNER JOIN Guest as G ON HG.Guest_idGuest=G.idGuest ";
	    query += "WHERE G.Name LIKE ?";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, guest);
	    result = statement.executeQuery();
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("E.idEvent"));
		event.setName(result.getString("E.Name"));
		event.setLocation(result.getString("E.Location"));

		event.setDate(result.getDate("E.Date").toLocalDate());
		event.setSuspended(result.getBoolean("E.Suspended"));
		EventCategory category = new EventCategory();
		category.setId(result.getInt("EC.idEventCategory"));
		category.setName(result.getString("EC.Name"));
		event.setCategory(category);
		User organizer = new User();
		organizer.setId(result.getInt("U.idUser"));
		organizer.setUsername(result.getString("U.Username"));
		event.setOrganizer(organizer);
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
    public Map<Integer, Event> findByCategory(String category_name) {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	CallableStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "SELECT * FROM findEventByCategory(?)";
	    statement = connection.prepareCall(query);
	    statement.setString(1, category_name);
	    result = statement.executeQuery();
	    while (result.next()) {
		Event event = new Event();
		event.setId(result.getInt("E.idEvent"));
		event.setName(result.getString("E.Name"));
		event.setLocation(result.getString("E.Location"));

		event.setDate(result.getDate("E.Date").toLocalDate());
		event.setSuspended(result.getBoolean("E.Suspended"));
		EventCategory category = new EventCategory();
		category.setId(result.getInt("EC.idEventCategory"));
		category.setName(result.getString("EC.Name"));
		event.setCategory(category);
		User organizer = new User();
		organizer.setId(result.getInt("U.idUser"));
		organizer.setUsername(result.getString("U.Username"));
		event.setOrganizer(organizer);
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
    public Map<Integer, Event> findTop() {
	Map<Integer, Event> events = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "SELECT e.idevent,e.name,e.image FROM top_events() as t join event as e on t.event_id = e.idevent ;";
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
    public Integer findLastId(User o) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Event> findByName(String name, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Event> findByDate(Date date, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Event> findByPrice(Long min, Long max, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Event> findByLocation(String location, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Event> findByGuest(String guest, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Event> findByCategory(String category, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

}
