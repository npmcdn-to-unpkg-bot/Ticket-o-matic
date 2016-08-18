package it.siw.persistence.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Review;
import it.siw.model.TicketCategory;
import it.siw.persistence.DAOUtility;
import it.siw.persistence.dao.TicketCategoryDAO;

public class TicketCategoryDAOJDBC implements TicketCategoryDAO
{	HikariDataSource dataSource;

	@Override
	public void create(TicketCategory modelObject) 
	{
		Connection connection=null;
		String query=null;
		PreparedStatement statement=null;
		
		try 	{
			connection=dataSource.getConnection();
			query="insert into TicketCategory(idTicketCategory,Name) values(?,?)";
			statement=connection.prepareStatement(query);
			statement.setInt(1, modelObject.getId());
			statement.setString(2, modelObject.getName());
			statement.executeUpdate();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DAOUtility.close(connection);
			DAOUtility.close(statement);
		}
		
	}

	@Override
	public void delete(TicketCategory tc) {
		Connection connection = null;
		String query = null;
		PreparedStatement statement = null;
		try {
		    connection = dataSource.getConnection();
		    query = "Delete From TicketCategory WHERE idTicketCategory = ?";
		    statement = connection.prepareStatement(query);
		    statement.setInt(1, tc.getId());
		    statement.executeUpdate();

		} 
		catch (SQLException e) 
		{
		    e.printStackTrace();
		} 
		finally 
		{
		    DAOUtility.close(connection);
		    DAOUtility.close(statement);
		}
		
	}

	@Override
	public void update(TicketCategory tc) {
		Connection connection = null;
		String query = null;
		PreparedStatement statement = null;
		try {
		    connection = dataSource.getConnection();
		    query = "Update TicketCategory SET idTicketCategory=?, Name=?";
		    statement = connection.prepareStatement(query);
		    statement.setInt(1, tc.getId());
		    statement.setString(2, tc.getName());
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
	public Map<Integer, TicketCategory> findById(Long id) 
	{
		Map<Integer, TicketCategory> ticketCategorys = new HashMap<>();
		Connection connection = null;
		String query = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
		    connection = dataSource.getConnection();
		    query = "Select TC.idTicketCategory,TC.Name";
		    query += "FROM TicketCategory as TC";
		    query +="INNER JOIN Ticket as T ON T.idTicket = TC.idTicketCategory";
		    statement = connection.prepareStatement(query);
		   
		    result = statement.executeQuery();
		    while (result.next()) {
		    TicketCategory ticketCategory = new TicketCategory();
			ticketCategory.setId(result.getInt("TC.idTicketCategory"));
			ticketCategorys.put(ticketCategory.getId(), ticketCategory);
		    }
		} catch (SQLException e) {
		    
		    e.printStackTrace();
		} finally {
		    DAOUtility.close(connection);
		    DAOUtility.close(statement);
		    DAOUtility.close(result);
		}
		return ticketCategorys;
	}

	@Override
	public Map<Integer, TicketCategory> findByName(String name) {
		Map<Integer, TicketCategory> ticketCategorys = new HashMap<>();
		Connection connection = null;
		String query = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
		    connection = dataSource.getConnection();
		    query = "Select TC.idTicketCategory,TC.Name";
		    query += "FROM TicketCategory as TC";
		    query += "WHERE TC.Name LIKE ?";
		    statement = connection.prepareStatement(query);
		    
		    result = statement.executeQuery();
		    while (result.next()) {
		    TicketCategory ticketCategory = new TicketCategory();
			ticketCategory.setId(result.getInt("TC.idTicketCategory"));
			ticketCategory.setName(result.getString("TC.Name"));
			ticketCategorys.put(ticketCategory.getId(), ticketCategory);
		    }
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
		    DAOUtility.close(connection);
		    DAOUtility.close(statement);
		    DAOUtility.close(result);
		}
		return ticketCategorys;
		
	}

}
