package it.siw.persistence.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Guest;
import it.siw.persistence.DAOUtility;
import it.siw.persistence.dao.GuestDAO;

public class GuestDAOJDBC implements GuestDAO {
    HikariDataSource dataSource;

    public GuestDAOJDBC(HikariDataSource datasource2) {
	this.dataSource = datasource2;
    }

    @Override
    public void create(Guest modelObject) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;

	try {
	    connection = dataSource.getConnection();

	    query = "insert into Guest(idGuest,Name,Image) values(?,?,?)";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, modelObject.getId());
	    statement.setString(2, modelObject.getName());
	    statement.setString(3, modelObject.getImage());
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}

    }

    @Override
    public void update(Guest g) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Update Guest SET idGuest=?, Name=?,Image=?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, g.getId());
	    statement.setString(2, g.getName());
	    statement.setString(3, g.getImage());
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
    public void delete(Guest g) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Delete From Guest WHERE idGuest = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, g.getId());
	    statement.executeUpdate();

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}

    }

    @Override
    public Map<Integer, Guest> findByName(String name) {
	Connection connection = null;
	String query = null;
	Map<Integer, Guest> guests = new HashMap<>();
	Guest guest = null;
	PreparedStatement statement = null;
	ResultSet result = null;

	try {
	    connection = dataSource.getConnection();
	    query = "Select G.idGuest,G.Name";
	    query += "FROM  Guest as G";
	    query += "WHERE G.Name LIKE ?";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, name);
	    result = statement.executeQuery();
	    while (result.next()) {
		guest = new Guest();
		guest.setId(result.getInt("idGuest"));
		guest.setName(result.getString("Name"));
		guests.put(guest.getId(), guest);

	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}

	return guests;
    }

    @Override
    public Guest findById(Long id) {
	Connection connection = null;
	String query = null;
	Map<Integer, Guest> guests = new HashMap<>();
	Guest guest = null;
	PreparedStatement statement = null;
	ResultSet result = null;

	try {
	    connection = dataSource.getConnection();
	    query = "Select G.idGuest,G.Name";
	    query += "FROM  Guest as G";
	    query += "WHERE G.idGuest LIKE ?";
	    statement = connection.prepareStatement(query);
	    statement.setLong(1, id);
	    result = statement.executeQuery();
	    while (result.next()) {
		guest = new Guest();
		guest.setId(result.getInt("idGuest"));
		guest.setName(result.getString("Name"));
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}

	return guest;

    }

    @Override
    public Map<Integer, Guest> findTop() {
	Connection connection = null;
	String query = null;
	Map<Integer, Guest> guests = new HashMap<>();
	Guest guest = null;
	PreparedStatement statement = null;
	ResultSet result = null;

	try {
	    connection = dataSource.getConnection();
	    query = "Select G.idGuest,G.Name, G.image ";
	    query += "FROM  Guest as G join top_artist() as t on g.idguest = t";
	    statement = connection.prepareStatement(query);
	    result = statement.executeQuery();
	    while (result.next()) {
		guest = new Guest();
		guest.setId(result.getInt("idGuest"));
		guest.setName(result.getString("Name"));
		guest.setImage(result.getString("image"));
		guests.put(guest.getId(), guest);

	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}

	return guests;
    }

}
