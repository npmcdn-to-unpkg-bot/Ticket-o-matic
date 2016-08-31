package it.siw.persistence.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Event;
import it.siw.model.Organizer;
import it.siw.persistence.DAOUtility;
import it.siw.persistence.dao.OrganizerDAO;

public class OrganizerDAOJDBC implements OrganizerDAO {
    HikariDataSource dataSource;

    @Override
    public void create(Organizer modelObject) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;

	try {
	    connection = dataSource.getConnection();
	    query = "insert into User(idUser,Username,Password,Email,Name,Surname,Type,Coins) values(?,?,?,?,?,?,?,?)";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, modelObject.getId());
	    statement.setString(2, modelObject.getUsername());
	    statement.setString(3, modelObject.getPassword());
	    statement.setString(4, modelObject.getEmail());
	    statement.setString(5, modelObject.getName());
	    statement.setString(6, modelObject.getSurname());
	    statement.setString(7, modelObject.getType().name());
	    statement.setFloat(8, modelObject.getCoins());
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
    public void delete(Organizer o) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;

	try {
	    connection = dataSource.getConnection();
	    query = "Delete From User WHERE idUser=?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, o.getId());

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
    }

    @Override
    public void update(Organizer o) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Update User SET Username=?, Password=?,Email=?,Name=?,Surname=?,Type=?,Coins=? WHERE idUser = ?";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, o.getUsername());
	    statement.setString(2, o.getPassword());
	    statement.setString(3, o.getEmail());
	    statement.setString(4, o.getName());
	    statement.setString(5, o.getSurname());
	    statement.setString(6, o.getType().name());
	    statement.setFloat(7, o.getCoins());
	    statement.setInt(8, o.getId());
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
    public Map<Integer, Organizer> setEvents(Event e) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Organizer> getEvents(Event e) {
	// TODO Auto-generated method stub
	return null;
    }

}
