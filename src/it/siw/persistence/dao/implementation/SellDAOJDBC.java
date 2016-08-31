package it.siw.persistence.dao.implementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Sell;
import it.siw.model.Ticket;
import it.siw.model.TicketCategory;
import it.siw.model.User;
import it.siw.persistence.DAOUtility;
import it.siw.persistence.dao.SellDAO;

public class SellDAOJDBC implements SellDAO {
    HikariDataSource dataSource;

    public SellDAOJDBC(HikariDataSource datasource) {
	this.dataSource = datasource;
    }

    @Override
    public void create(Sell modelObject) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;

	try {
	    connection = dataSource.getConnection();
	    query = "insert into UserSellTicket(IdSell,User_id,Ticket_id,Date,Price,Order_id,Gift_id) values(?,?,?,?,?,?)";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, modelObject.getId());
	    statement.setInt(2, modelObject.getSeller().getId());
	    statement.setInt(3, modelObject.getTicket().getId());
	    statement.setDate(4, (Date) modelObject.getDate());
	    statement.setDouble(5, modelObject.getPrice());
	    statement.setInt(6, modelObject.getOrder().getId());
	    statement.setInt(7, modelObject.getGift().getId());
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
    public void delete(Sell s) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Delete From UserSellTicket WHERE idSell = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, s.getId());
	    statement.executeUpdate();

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}
    }

    @Override
    public void update(Sell s) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Update UserSellTicket SET idSell=?, User_id=?,Ticket_id=?, Date=?, Price=?,Order_id=?,Gift_id=?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, s.getId());
	    statement.setInt(2, s.getSeller().getId());
	    statement.setInt(3, s.getTicket().getId());
	    statement.setDate(4, (Date) s.getDate());
	    statement.setDouble(5, s.getPrice());
	    statement.setInt(6, s.getOrder().getId());
	    statement.setInt(7, s.getGift().getId());

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
    public Map<Integer, Sell> findByEvent(int id) {
	Map<Integer, Sell> sells = new HashMap<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Select count(*) as quantity ,s.price as price,tc.idticketcategory, tc.name as category  from event as e  "
		    + "join ticket as t on e.idevent = t.event_id "
		    + "join ticketcategory as tc on t.ticketcategory_id = tc.idticketcategory "
		    + "join usersellticket as s on t.idticket = s.ticket_id "
		    + "where t.event_id = ? AND s.order_id is NULL AND (s.reserved_by is NULL or age(localtimestamp,s.reserved) > interval '30 minutes') "
		    + "GROUP BY idticketcategory,s.price " + "ORDER BY s.price ASC";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    int i = 0;
	    while (result.next()) {
		Sell sell = new Sell();
		sell.setPrice(result.getFloat("price"));
		Ticket ticket = new Ticket();
		TicketCategory category = new TicketCategory();
		category.setId(result.getInt("idticketcategory"));
		category.setName(result.getString("category"));
		ticket.setCategory(category);
		ticket.setQuantity(result.getInt("quantity"));
		sell.setTicket(ticket);
		sells.put(i++, sell);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return sells;
    }

    @Override
    public Sell reserveSell(User user, Sell sell) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Select sellid from ticket_reservation(?,?,?,?) as sellid";
	    statement = connection.prepareStatement(query);
	    statement.setString(1, user.getSession_id());
	    statement.setFloat(2, sell.getPrice());
	    statement.setInt(3, sell.getTicket().getCategory().getId());
	    statement.setInt(4, sell.getTicket().getEvent().getId());
	    result = statement.executeQuery();
	    if (result.next()) {
		sell.setId(result.getInt("sellid"));
	    }

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	    DAOUtility.close(result);
	}
	return sell;
    }

    @Override
    public boolean removeReservation(Sell sell) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;

	try {
	    connection = dataSource.getConnection();
	    query = "UPDATE usersellticket SET reserved = ?, reserved_by = ? where idsell = ?";
	    statement = connection.prepareStatement(query);
	    statement.setNull(1, Types.TIMESTAMP);
	    statement.setNull(2, Types.VARCHAR);
	    statement.setInt(3, sell.getId());
	    statement.executeUpdate();
	    return true;
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
