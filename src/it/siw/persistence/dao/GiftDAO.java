package it.siw.persistence.dao;

import java.util.Date;
import java.util.Map;

import it.siw.model.Gift;
import it.siw.model.Ticket;
import it.siw.model.User;

public interface GiftDAO 
{
	public void create (Gift modelObject);
	
	public void update(Gift g);
	
	public void delete (Gift g);
	
	public Map<Integer,Gift> findByName(String name);
	
	public Map<Integer,Gift> findByPrice(Long min,Long max);
	
	public Gift findById(Long id);
	
	public Map<Integer,Gift> findBySender(User s);
	
	public Map<Integer,Gift> findByReceiver(User s);
	
	public Map<Integer,Gift> findByTicket(Ticket t);
	
	public Map<Integer,Gift> findByDate(Date d);
	
	
	
	
}
