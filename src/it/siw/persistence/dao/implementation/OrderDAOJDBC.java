package it.siw.persistence.dao.implementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Event;
import it.siw.model.Order;
import it.siw.model.Sell;
import it.siw.model.Ticket;
import it.siw.model.TicketCategory;
import it.siw.model.User;
import it.siw.persistence.DAOUtility;
import it.siw.persistence.dao.OrderDAO;

public class OrderDAOJDBC implements OrderDAO {
    HikariDataSource dataSource;

    public OrderDAOJDBC(HikariDataSource datasource) {
	this.dataSource = datasource;
    }

    @Override
    public List<Integer> create(Order modelObject) {
	List<Integer> items = new ArrayList<>();
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "SELECT ticket_id FROM order_checkout(?,?,?,?) as ticket_id";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, modelObject.getUser().getId());
	    statement.setString(2, modelObject.getUser().getSession_id());
	    statement.setArray(3, connection.createArrayOf("INT", modelObject.getSells().keySet().toArray()));
	    statement.setFloat(4, modelObject.getTotal());
	    result = statement.executeQuery();
	    while (result.next()) {
		items.add(result.getInt(1));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}

	return items;
    }

    @Override
    public void delete(Order o) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Delete From Order WHERE idOrder = ?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, o.getId());
	    statement.executeUpdate();

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DAOUtility.close(connection);
	    DAOUtility.close(statement);
	}

    }

    @Override
    public void update(Order o) {
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Update Order SET idOrder=?, Date=?,User_id=?,TotalCost=?";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, o.getId());
	    statement.setDate(2, (Date) o.getDate());
	    statement.setInt(3, o.getUser().getId());
	    statement.setFloat(4, o.getTotal());
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
    public Order findById(Integer id) {
	Order order = null;
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Select T.idticket,TC.name as t_cat,E.idevent, E.name,E.date,E.location, E.image, U.iduser, U.username, s.price, s.idsell ";
	    query += "FROM orders as O ";
	    query += "INNER JOIN usersellticket as S ON O.idorder = S.order_id ";
	    query += "INNER JOIN ticket as T ON S.ticket_id = T.idticket ";
	    query += "INNER JOIN ticketcategory as TC ON T.ticketcategory_id = TC.idticketcategory ";
	    query += "INNER JOIN event as E ON T.event_id = E.idevent ";
	    query += "INNER JOIN users as U on S.user_id = U.iduser ";
	    query += "WHERE O.idorder=? ";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    if (result.isBeforeFirst()) {
		order = new Order();
		order.setSells(new HashMap<Integer, Sell>());
		while (result.next()) {
		    Sell sell = new Sell();
		    sell.setId(result.getInt("idsell"));
		    sell.setPrice(result.getFloat("price"));
		    User user = new User();
		    user.setId(result.getInt("iduser"));
		    user.setUsername(result.getString("username"));
		    sell.setSeller(user);
		    Ticket ticket = new Ticket();
		    ticket.setId(result.getInt("idticket"));
		    TicketCategory category = new TicketCategory();
		    category.setName(result.getString("t_cat"));
		    ticket.setCategory(category);
		    sell.setTicket(ticket);
		    Event event = new Event();
		    event.setId(result.getInt("idevent"));
		    event.setName(result.getString("name"));
		    event.setDate(result.getDate("date").toLocalDate());
		    event.setLocation(result.getString("location"));
		    event.setImage(result.getString("image"));
		    ticket.setEvent(event);
		    order.getSells().put(sell.getId(), sell);
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
	return order;
    }

    @Override
    public Map<Integer, Order> findByUser(Integer id) {
	HashMap<Integer, Order> orders = null;
	Connection connection = null;
	String query = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
	    connection = dataSource.getConnection();
	    query = "Select idorder, o.date, totalcost,count(idsell) as items ";
	    query += "FROM orders as o ";
	    query += "INNER JOIN usersellticket as S ON o.idorder = s.order_id ";
	    query += "WHERE o.user_id = ? ";
	    query += "GROUP BY idorder ";
	    statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    result = statement.executeQuery();
	    if (result.isBeforeFirst()) {
		orders = new HashMap<>();
		while (result.next()) {
		    Order order = new Order();
		    order.setId(result.getInt("idorder"));
		    long time = result.getDate("date").getTime();
		    order.setDate(new java.util.Date(time));
		    order.setTotal(result.getFloat("totalcost"));
		    orders.put(order.getId(), order);
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
	return orders;
    }

    @Override
    public Integer findLastId(User u) {
	// TODO Auto-generated method stub
	return null;
    }

}
