package it.siw.persistence.dao.implementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Gift;
import it.siw.model.Order;
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
	    query = "Select s.idsell as id,s.price as price,tc.idticketcategory, tc.name as category from event as e  "
		    + "join ticket as t on e.idevent = t.event_id "
		    + "join ticketcategory as tc on t.ticketcategory_id = tc.idticketcategory "
		    + "join usersellticket as s on t.idticket = s.ticket_id "
		    + "where t.event_id = ? ORDER BY s.price ASC";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    int i = 0;
	    while (result.next()) {
		Sell sell = new Sell();
		sell.setId(result.getInt("id"));
		sell.setPrice(result.getFloat("price"));
		Ticket ticket = new Ticket();
		TicketCategory category = new TicketCategory();
		category.setId(result.getInt("idticketcategory"));
		category.setName(result.getString("category"));
		ticket.setCategory(category);
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
    public Integer reserveById(int user_id, int ticket_id) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "UPDATE usersellticket as s SET reserved = localtimestamp, reserved_by = ? WHERE s.ticket_id = ? AND (s.reserved_by is NULL OR s.reserved_by = ? OR age(localtimestamp,s.reserved) > interval '30 minutes')); ";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, user_id);
	    statement.setInt(2, ticket_id);
	    statement.setInt(3, user_id);
	    return statement.executeUpdate();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);

	}
	return -1;
    }

    @Override
    public Map<Integer, Sell> findByUser(User u) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByTicket(Ticket t) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByDate(Date d) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByPrice(float price) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByOrder(Order o) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByGift(Gift g) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer findLastId(Order o) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Sell findById(Long Id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByUser(User u, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByTicket(Ticket t, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByDate(Date d, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByPrice(float price, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByOrder(Order o, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<Integer, Sell> findByGift(Gift g, Integer offset, Integer limit) {
	// TODO Auto-generated method stub
	return null;
    }

}
