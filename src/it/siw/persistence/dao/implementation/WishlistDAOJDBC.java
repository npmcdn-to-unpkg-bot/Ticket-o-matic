
package it.siw.persistence.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Event;
import it.siw.model.User;
import it.siw.model.Wishlist;
import it.siw.persistence.DAOUtility;
import it.siw.persistence.dao.WishlistDAO;

public class WishlistDAOJDBC implements WishlistDAO {
    HikariDataSource dataSource;

    public WishlistDAOJDBC(HikariDataSource datasource) {
	this.dataSource = datasource;
    }

    @Override
    public int create(Wishlist modelObject) {

	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;

	try {
	    connection = dataSource.getConnection();
	    query = "insert into Wishlist(Title,User_idUser)  values(?,?)";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, modelObject.getTitle());
	    statement.setInt(2, modelObject.getUser().getId());

	    return statement.executeUpdate();

	} catch (

	SQLException e) {
	    e.printStackTrace();
	    return 0;
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
    }

    @Override
    public void delete(Wishlist wishlist) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;

	try {
	    connection = dataSource.getConnection();
	    query = "Delete From Wishlist Where idWishlist=?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, wishlist.getId());
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
    public void update(Wishlist wishlist) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Update Wishlist SET idWishlist=?, Title=?,User_idUser=?";
	    statement.setInt(1, wishlist.getId());
	    statement.setString(2, wishlist.getTitle());
	    statement.setInt(3, wishlist.getUser().getId());
	    statement = connection.prepareStatement(query);
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
    }

    @Override
    public Wishlist findById(Integer id) {
	Wishlist wishlist = null;
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Select E.idevent, E.name, E.date, E.location ,E.Image ";
	    query += "FROM Wishlist as W ";
	    query += "INNER JOIN wishlist_has_event as WHE ON W.idwishlist = WHE.wishlist_idwishlist ";
	    query += "INNER JOIN event as E ON WHE.event_idevent = E.idevent ";
	    query += "WHERE idwishlist=? ";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    if (result.isBeforeFirst()) {
		wishlist = new Wishlist();
		wishlist.setEvents(new HashMap<Integer, Event>());
		while (result.next()) {
		    Event event = new Event();
		    event.setId(result.getInt("idevent"));
		    event.setName(result.getString("name"));
		    long time = result.getDate("date").getTime();
		    event.setDate(new Date(time));
		    event.setLocation(result.getString("location"));
		    event.setImage(result.getString("image"));
		    wishlist.getEvents().put(event.getId(), event);
		}
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return null;
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return wishlist;
    }

    @Override
    public Map<Integer, Wishlist> findByTitle(String title) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Wishlist> findByUser(User name) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer findLastId(User u) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Wishlist> findByTitle(String title, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Wishlist> findByUser(Integer id, Integer offset, Integer limit) {
	HashMap<Integer, Wishlist> wishlists = null;
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Select idwishlist, title ";
	    query += "FROM Wishlist ";
	    query += "WHERE user_iduser=? ";
	    query += "OFFSET ? LIMIT ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    statement.setInt(2, offset);
	    statement.setInt(3, limit);
	    result = statement.executeQuery();
	    if (result.isBeforeFirst()) {
		wishlists = new HashMap<>();
		while (result.next()) {
		    Wishlist wishlist = new Wishlist();
		    wishlist.setId(result.getInt("idwishlist"));
		    wishlist.setTitle(result.getString("Title"));
		    wishlists.put(wishlist.getId(), wishlist);
		}
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return wishlists;
    }

    @Override
    public boolean updateEvent(Wishlist wishlist, Event event) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Insert into wishlist_has_event(wishlist_idwishlist,event_idevent) values(?,?)";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, wishlist.getId());
	    statement.setInt(2, event.getId());
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

}
