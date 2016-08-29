package it.siw.persistence.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Event;
import it.siw.model.Ticket;
import it.siw.model.TicketCategory;
import it.siw.persistence.DAOUtility;
import it.siw.persistence.dao.TicketDAO;

public class TicketDAOJDBC implements TicketDAO {
    HikariDataSource dataSource;

    public TicketDAOJDBC(HikariDataSource datasource) {
	this.dataSource = datasource;
    }

    @Override
    public void create(Ticket modelObject) {

	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;

	try {
	    connection = dataSource.getConnection();
	    query = "insert into TIcket(idTicket,Price,Sector,Row,Seat,TicketCategory,Event_id,Empty) values(?,?,?,?,?,?,?,?)";
	    statement = connection.prepareStatement(query);

	    statement.setInt(1, modelObject.getId());
	    statement.setFloat(2, modelObject.getPrice());
	    statement.setString(3, modelObject.getSector());
	    statement.setString(4, modelObject.getRow());
	    statement.setString(5, modelObject.getSeat());
	    statement.setInt(6, modelObject.getCategory().getId());
	    statement.setInt(7, modelObject.getEvent().getId());
	    statement.setBoolean(8, modelObject.isEmpty());

	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
    }

    @Override
    public void delete(Ticket t) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Delete From Ticket WHERE idTicket = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, t.getId());
	    statement.executeUpdate();

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}

    }

    @Override
    public void update(Ticket t) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Update Ticket SET idTicket=?, Price=?,Sector=?,Row=?,Seat=?,TicketCategory=?,Event_id=?,Empty=?";

	    statement = connection.prepareStatement(query);
	    statement.setInt(1, t.getId());
	    statement.setFloat(2, t.getPrice());
	    statement.setString(3, t.getSector());
	    statement.setString(4, t.getRow());
	    statement.setString(5, t.getSeat());
	    statement.setInt(6, t.getCategory().getId());
	    statement.setInt(7, t.getEvent().getId());
	    statement.setBoolean(8, t.isEmpty());
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
    public Ticket findById(Long id) {

	Connection connection = null;
	String query = null;
	Ticket ticket = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Select T.idTicket,";
	    query += "FROM Ticket as T";
	    query += "WHERE T.idTicket LIKE ?";
	    statement = connection.prepareStatement(query);

	    result = statement.executeQuery();
	    while (result.next()) {
		ticket = new Ticket();
		ticket.setId(result.getInt("T.idTicket"));

	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return ticket;

    }

    @Override
    public Map<Integer, Ticket> findByPrice(float price) {
	Map<Integer, Ticket> tickets = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Select T.idTicket,T.Price";
	    query += "FROM Ticket as T";
	    query += "WHERE T.Price LIKE ?";
	    statement = connection.prepareStatement(query);

	    result = statement.executeQuery();
	    while (result.next()) {
		Ticket ticket = new Ticket();
		ticket.setId(result.getInt("T.idTicket"));
		ticket.setPrice(result.getFloat("T.Price"));
		tickets.put(ticket.getId(), ticket);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return tickets;

    }

    @Override
    public Map<Integer, Ticket> findBySector(Long Sector) {
	Map<Integer, Ticket> tickets = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Select T.idTicket,T.Sector";
	    query += "FROM Ticket as T";
	    query += "WHERE T.Sector LIKE ?";
	    statement = connection.prepareStatement(query);
	    result = statement.executeQuery();
	    while (result.next()) {
		Ticket ticket = new Ticket();
		ticket.setId(result.getInt("T.idTicket"));
		ticket.setSector(result.getString("T.Sector"));
		tickets.put(ticket.getId(), ticket);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return tickets;

    }

    @Override
    public Map<Integer, Ticket> findByTicketCategory(TicketCategory tc) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Ticket> findByEvent(Event e) {
	Map<Integer, Ticket> tickets = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Select ";
	    statement = connection.prepareStatement(query);
	    result = statement.executeQuery();
	    while (result.next()) {
		Ticket ticket = new Ticket();
		ticket.setId(result.getInt("idticket"));
	    }
	} catch (SQLException e1) {
	    e1.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return null;
    }

    @Override
    public Map<Integer, Ticket> findByEmpty(boolean empty) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void updateEmpty(boolean e) {
	// TODO Auto-generated method stub

    }

}
