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
import it.siw.model.EventCategory;
import it.siw.model.Review;
import it.siw.model.User;
import it.siw.persistence.DAOUtility;
import it.siw.persistence.dao.ReviewDAO;

public class ReviewDAOJDBC implements ReviewDAO
{	HikariDataSource dataSource;
	
	@Override
	public void create(Review modelObject) 
	{
		Connection connection=null;
		String query=null;
		PreparedStatement statement=null;
		
		try {
			connection=dataSource.getConnection();
			
			query="insert into Review(idReview,User_id,Event_id,Title,Text,Feedback) values(?,?,?,?,?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, modelObject.getId());
			statement.setInt(2, modelObject.getUser().getId());
			statement.setInt(3, modelObject.getEvent().getId());
			statement.setString(4, modelObject.getTitle());
			statement.setString(5, modelObject.getText());
			statement.setInt(6, modelObject.getFeedback());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			DAOUtility.close(connection);
			DAOUtility.close(statement);
		}
	}

	@Override
	public void delete(Review r) 
	{
	
		Connection connection = null;
		String query = null;
		PreparedStatement statement = null;
		try {
		    connection = dataSource.getConnection();
		    query = "Delete From Review WHERE idReview=?";
		    statement = connection.prepareStatement(query);
		    statement.setInt(1, r.getId());
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
	public void update(Review r) 
	{
		Connection connection = null;
		String query = null;
		PreparedStatement statement = null;
		try {
		    connection = dataSource.getConnection();
		    query = "Update User SET idReview=?, User_id=?,Event_id=?,Title=?,Text=?,Feedback=?";
		    statement = connection.prepareStatement(query);
			statement.setInt(1, r.getId());
			statement.setInt(2, r.getUser().getId());
			statement.setInt(3, r.getEvent().getId());
			statement.setString(4, r.getTitle());
			statement.setString(5, r.getText());
			statement.setInt(6, r.getFeedback());
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
	public Map<Integer, Review> findById(Long id) {
		Map<Integer, Review> reviews = new HashMap<>();
		Connection connection = null;
		String query = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
		    connection = dataSource.getConnection();
		    query = "Select R.idReview,R.User_id,R.Event_id,R.Title,R.Text,R.Feedback";
		    query += "FROM Review as R";
		    query += "WHERE R.idReview LIKE ?";
		    statement = connection.prepareStatement(query);
		    
		    result = statement.executeQuery();
		    while (result.next()) {
		    Review review = new Review();
			review.setId(result.getInt("R.idReview"));
			reviews.put(review.getId(), review);
		    }
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
		    DAOUtility.close(connection);
		    DAOUtility.close(statement);
		    DAOUtility.close(result);
		}
		return reviews;
	}

	@Override
	public Map<Integer, Review> findByUser(User name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Review> findByTitle(String name) {
		Map<Integer, Review> reviews = new HashMap<>();
		Connection connection = null;
		String query = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
		    connection = dataSource.getConnection();
		    query = "Select R.idReview,R.User_id,R.Event_id,R.Title,R.Text,R.Feedback";
		    query += "FROM Review as R";
		    query += "WHERE R.idReview LIKE ?";
		    statement = connection.prepareStatement(query);
		    
		    result = statement.executeQuery();
		    while (result.next()) {
		    Review review = new Review();
			review.setId(result.getInt("R.idReview"));
			review.setTitle(result.getString("R.Title"));
			reviews.put(review.getId(), review);
		    }
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
		    DAOUtility.close(connection);
		    DAOUtility.close(statement);
		    DAOUtility.close(result);
		}
		return reviews;
		
	}

	@Override
	public Map<Integer, Review> findByEvent(Event name) {
		Map<Integer, Review> reviews = new HashMap<>();
		Connection connection = null;
		String query = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
		    connection = dataSource.getConnection();
		    query = "Select R.idReview,R.User_id,R.Event_id,R.Title,R.Text,R.Feedback";
		    query += "FROM Review as R";
		    query += "WHERE R.idReview LIKE ?";
		    statement = connection.prepareStatement(query);
		    
		    result = statement.executeQuery();
		    while (result.next()) {
		    Review review = new Review();
			review.setId(result.getInt("R.idReview"));
			Event event=new Event();
			event.setId(result.getInt("R.Event_id"));
			review.setEvent(event);
			reviews.put(review.getId(), review);
		    }
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
		    DAOUtility.close(connection);
		    DAOUtility.close(statement);
		    DAOUtility.close(result);
		}
		return reviews;
		
	}

	@Override
	public Map<Integer, Review> findByFeedback(Long feedback) {
		Map<Integer, Review> reviews = new HashMap<>();
		Connection connection = null;
		String query = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
		    connection = dataSource.getConnection();
		    query = "Select R.idReview,R.User_id,R.Event_id,R.Title,R.Text,R.Feedback";
		    query += "FROM Review as R";
		    query += "WHERE R.idReview LIKE ?";
		    statement = connection.prepareStatement(query);
		    
		    result = statement.executeQuery();
		    while (result.next()) {
		    Review review = new Review();
			review.setId(result.getInt("R.idReview"));
			review.setFeedback(result.getInt("R.Feedback"));
			reviews.put(review.getId(), review);
		    }
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
		    DAOUtility.close(connection);
		    DAOUtility.close(statement);
		    DAOUtility.close(result);
		}
		return reviews;
		
	}

}
