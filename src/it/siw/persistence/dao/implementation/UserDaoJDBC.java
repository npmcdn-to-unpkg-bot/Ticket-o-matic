package it.siw.persistence.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Event;
import it.siw.model.Gift;
import it.siw.model.Order;
import it.siw.model.Review;
import it.siw.model.Sell;
import it.siw.model.Ticket;
import it.siw.model.Type;
import it.siw.model.User;
import it.siw.model.Wishlist;
import it.siw.persistence.DAOUtility;
import it.siw.persistence.dao.UserDAO;

public class UserDaoJDBC implements UserDAO {
    HikariDataSource datasource;

    public UserDaoJDBC(HikariDataSource datasource) {
	this.datasource = datasource;
    }

    @Override
    public boolean create(User modelObject) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = datasource.getConnection();
	    query = "insert into Users(Username,Password,Email,Name,Surname,UserType,Coins) values(?,?,?,?,?,?::user_type,?)";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, modelObject.getUsername());
	    statement.setString(2, modelObject.getPassword());
	    statement.setString(3, modelObject.getEmail());
	    statement.setString(4, modelObject.getName());
	    statement.setString(5, modelObject.getSurname());
	    statement.setString(6, modelObject.getType().name());
	    statement.setFloat(7, modelObject.getCoins());
	    return (statement.executeUpdate() > 0) ? true : false;
	} catch (SQLException e) {
	    System.err.println("Error");
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
	return false;
    }

    @Override
    public User findById(Integer id) {
	User user = new User();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select U.idUser, U.username, U.password, U.email, U.name , U.surname, U.usertype, U.coins "
		    + "FROM Users as U " + "WHERE U.idUser = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    if (result.next()) {
		user.setId(result.getInt("idUser"));
		user.setUsername(result.getString("username"));
		user.setPassword(result.getString("password"));
		user.setEmail(result.getString("email"));
		user.setName(result.getString("name"));
		user.setSurname(result.getString("surname"));
		user.setType(Type.valueOf(result.getString("usertype")));
		user.setCoins(result.getFloat("coins"));
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
	return user;
    }

    @Override
    public User findWishlist(Integer id) {
	User user = new User();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select idWishlist, Title,idEvent,E.Name,E.Image ";
	    query += "FROM Wishlist as W ";
	    query += "INNER JOIN Wishlist_has_Event as joinWE ON joinWE.Wishlist_idWhislist=W.idWishlist ";
	    query += "INNER JOIN Event as E ON E.idEvent=joinWE.Event_idEvent ";
	    query += "WHERE W.User_idUser = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    Integer temp_id = null;
	    Wishlist wish = null;
	    user.setId(id);
	    while (result.next()) {
		if (temp_id == null || temp_id != result.getInt("idWishlist")) {
		    temp_id = result.getInt("idWishlist");
		    wish = new Wishlist();
		    wish.setId(temp_id);
		    wish.setTitle(result.getString("Title"));
		    user.getWhishlists().put(temp_id, wish);
		}
		Event event = new Event();
		event.setId(result.getInt("idEvent"));
		event.setName(result.getString("name"));
		event.setImage(result.getString("image"));
		wish.getEvents().put(event.getId(), event);

	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return user;
    }

    @Override
    public User findOrder(Integer id) {
	User user = new User();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select idOrder, O.Date as OrderDate,S.idSell,S.Price,";
	    query += "G.idGift.U.idUSer,U.Username,";
	    query += "T.idTicket,T.Sector,T.Row,T.Seat,T.Empty,E.idEvent,E.Name,E.Date as EventDate,E.Image ";
	    query += "FROM Orders as O ";
	    query += "INNER JOIN UserSellTicket as S ON S.Order_id=O.idOrder ";
	    query += "LEFT JOIN Gift as G ON G.idGift=S.Gift_id ";
	    query += "LEFT JOIN Users as U ON U.idUser=G.User_idUser ";
	    query += "INNER JOIN Ticket as T ON T.Ticket=S.Ticket_id ";
	    query += "INNER JOIN Event as E ON E.idEvent=T.Event_id ";
	    query += "WHERE O.User_id = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    Integer temp_id = null;
	    Order order = null;
	    user.setId(id);
	    while (result.next()) {
		if (temp_id == null || temp_id != result.getInt("idOrder")) {
		    temp_id = result.getInt("idOrder");
		    order = new Order();
		    order.setId(temp_id);
		    long time = result.getDate("OrderDate").getTime();
		    order.setDate(new Date(time));
		    user.getOrders().put(temp_id, order);
		}
		Sell sell = new Sell();
		sell.setId(result.getInt("S.idSell"));
		sell.setPrice(result.getFloat("S.Price"));
		order.getSells().put(sell.getId(), sell);
		if (result.getInt("G.idGift") != 0) {
		    Gift gift = new Gift();
		    gift.setId(result.getInt("G.idGift"));
		    gift.setSource(user);
		    User gifted = new User();
		    gifted.setId(result.getInt("U.idUSer"));
		    gifted.setUsername(result.getString("U.Username"));
		    gift.setDestination(gifted);
		    sell.setGift(gift);
		}
		Ticket ticket = new Ticket();
		ticket.setId(result.getInt("T.idTicket"));
		ticket.setSector(result.getString("T.Sector"));
		ticket.setRow(result.getString("T.Row"));
		ticket.setSeat(result.getString("T.Seat"));
		ticket.setEmpty(result.getBoolean("T.Empty"));
		sell.setTicket(ticket);
		Event event = new Event();
		event.setId(result.getInt("E.idEvent"));
		event.setName(result.getString("E.Name"));
		event.setImage(result.getString("E.Image"));

		event.setDate(result.getDate("EventDate").toLocalDate());
		ticket.setEvent(event);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return user;
    }

    @Override
    public User findReview(Integer id) {
	User user = new User();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select idReview,Title,Text,Feedback,E.idEvent,E.Name,E.Image ";
	    query += "FROM Review as R ";
	    query += "INNER JOIN Event as E ON R.Event_id=E.idEvent ";
	    query += "WHERE R.USer_id = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    user.setId(id);
	    while (result.next()) {
		Review review = new Review();
		review.setId(result.getInt("idReview"));
		review.setTitle(result.getString("Title"));
		review.setText(result.getString("Text"));
		review.setFeedback(result.getInt("Feedback"));
		Event event = new Event();
		event.setId(result.getInt("idEvent"));
		event.setName(result.getString("Name"));
		event.setImage(result.getString("Image"));
		review.setEvent(event);
		user.getReviews().put(review.getId(), review);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return user;
    }

    @Override
    public User findSells(Integer id) {
	User user = new User();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select idSell, S.Date,S.Price,E.idEvent,E.Name,E.Image,O.idOrder,U.idUser,U.Username ";
	    query += "T.idTicket,T.Sector,T.Row,T.Seat,T.Empty ";
	    query += "FROM UserSellTicket as S ";
	    query += "INNER JOIN Ticket as T ON S.Ticket_id=T.idTicket ";
	    query += "INNER JOIN Event as E ON T.Event_id=E.idEvent ";
	    query += "LEFT JOIN Orders as O ON S.Order_id=O.idOrder ";
	    query += "LEFT JOIN Users as U ON O.User_id=U.idUser ";
	    query += "WHERE S.User_id = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    user.setId(id);
	    while (result.next()) {
		Sell sell = new Sell();
		sell.setId(result.getInt("idSell"));
		user.getSells().put(sell.getId(), sell);
		long time = result.getDate("Date").getTime();
		sell.setDate(new Date(time));
		sell.setPrice(result.getFloat("Price"));
		Ticket ticket = new Ticket();
		sell.setTicket(ticket);
		ticket.setId(result.getInt("idTicket"));
		ticket.setSector(result.getString("Sector"));
		ticket.setRow(result.getString("Row"));
		ticket.setSeat(result.getString("Seat"));
		ticket.setEmpty(result.getBoolean("Empty"));
		Event event = new Event();
		event.setId(result.getInt("idEvent"));
		event.setName(result.getString("Name"));
		event.setImage(result.getString("Image"));
		ticket.setEvent(event);
		if (result.getInt("idOrder") != 0) {
		    Order order = new Order();
		    order.setId(result.getInt("idOrder"));
		    sell.setOrder(order);
		    User buyer = new User();
		    buyer.setId(result.getInt("idUser"));
		    buyer.setUsername(result.getString("Username"));
		    order.setUser(buyer);
		}
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return user;
    }

    @Override
    public User findGift(Integer id) {
	User user = new User();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select idGift, T.idTicket,T.Sector,T.Row,T.Seat,E.idEvent,E.Name,E.Image,O.Date,U.idUser,U.Username,U.Email ";
	    query += "FROM Gift as G ";
	    query += "INNER JOIN UserSellTicket as S ON G.idGift=S.Gift_id ";
	    query += "INNER JOIN Orders as O ON S.Order_id=O.idOrder ";
	    query += "INNER JOIN Users as U ON O.User_id=U.idUser ";
	    query += "INNER JOIN Ticket as T ON S.Ticket_id=T.idTicket ";
	    query += "INNER JOIN Event as E ON T.Event_id=E.idEvent ";
	    query += "WHERE G.User_idUser = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    user.setId(id);
	    while (result.next()) {
		Gift gift = new Gift();
		gift.setId(result.getInt("idGift"));
		user.getGifts().put(gift.getId(), gift);
		long time = result.getDate("Date").getTime();
		gift.setDate(new Date(time));
		gift.setDestination(user);
		User source = new User();
		source.setId(result.getInt("idUser"));
		source.setUsername(result.getString("Username"));
		source.setEmail(result.getString("Email"));
		gift.setSource(source);
		Ticket ticket = new Ticket();
		ticket.setId(result.getInt("idTicket"));
		ticket.setSector(result.getString("Sector"));
		ticket.setRow(result.getString("Seat"));
		ticket.setSeat(result.getString("Seat"));
		gift.setTicket(ticket);
		Event event = new Event();
		event.setId(result.getInt("idEvent"));
		event.setName(result.getString("Name"));
		event.setImage(result.getString("Image"));
		ticket.setEvent(event);

	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return user;
    }

    @Override
    public boolean update(User modelObject) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = datasource.getConnection();
	    query = "Update Users SET Password=?,Email=?,Name=?,Surname=? WHERE idUser = ?";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, modelObject.getPassword());
	    statement.setString(2, modelObject.getEmail());
	    statement.setString(3, modelObject.getName());
	    statement.setString(4, modelObject.getSurname());
	    statement.setInt(5, modelObject.getId());
	    return (statement.executeUpdate() > 0) ? true : false;

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return false;
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}

    }

    @Override
    public void delete(User modelObject) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = datasource.getConnection();
	    query = "Delete From Users WHERE idUser = ?";
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
    public User findByUsername(String username) {
	User user = new User();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select U.idUser, U.username, U.password, U.email, U.name , U.surname, U.usertype, U.coins "
		    + "FROM Users as U " + "WHERE U.username = ?";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, username);
	    result = statement.executeQuery();
	    if (result.next()) {
		user.setId(result.getInt("idUser"));
		user.setUsername(result.getString("username"));
		user.setPassword(result.getString("password"));
		user.setEmail(result.getString("email"));
		user.setName(result.getString("name"));
		user.setSurname(result.getString("surname"));
		user.setType(Type.valueOf(result.getString("usertype")));
		user.setCoins(result.getLong("coins"));
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
	return user;
    }

    @Override
    public User findByEmail(String email) {
	User user = new User();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = datasource.getConnection();
	    query = "Select U.idUser, U.username, U.password, U.email, U.name , U.surname, U.usertype, U.coins "
		    + "FROM Users as U " + "WHERE U.email = ?";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, email);
	    result = statement.executeQuery();
	    if (result.next()) {
		user.setId(result.getInt("idUser"));
		user.setUsername(result.getString("username"));
		user.setPassword(result.getString("password"));
		user.setEmail(result.getString("email"));
		user.setName(result.getString("name"));
		user.setSurname(result.getString("surname"));
		user.setType(Type.valueOf(result.getString("usertype")));
		user.setCoins(result.getLong("coins"));
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
	return user;
    }

}
