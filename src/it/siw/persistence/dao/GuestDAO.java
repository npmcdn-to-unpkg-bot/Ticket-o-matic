package it.siw.persistence.dao;

import java.util.Map;

import it.siw.model.Guest;

public interface GuestDAO 
{
	public void create (Guest modelObject);
	
	public void update(Guest g);
	
	public void delete(Guest g);
	
	public Map<Integer,Guest> findByName(String name);
	
	public Guest findById(Long id);
	
	
}
