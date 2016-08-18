package it.siw.persistence.dao;

import it.siw.model.Event;
import it.siw.model.Review;
import it.siw.model.User;

import java.util.*;

public interface ReviewDAO 
{
	public void create(Review modelObject);
	
	public void delete(Review r);
	
	public void update(Review r);
	
	public Map<Integer,Review> findById(Long id);
	
	public Map<Integer,Review> findByUser(User name);
	
	public Map<Integer,Review> findByTitle(String name);
	
	public Map<Integer,Review> findByEvent(Event name);
	
	public Map<Integer,Review> findByFeedback(Long feedback);
	
	
}

