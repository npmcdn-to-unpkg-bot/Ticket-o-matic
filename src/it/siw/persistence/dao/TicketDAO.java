package it.siw.persistence.dao;

import java.util.Map;

import it.siw.model.Event;
import it.siw.model.Ticket;
import it.siw.model.TicketCategory;

public interface TicketDAO 
{
	public void create(Ticket modelObject);
	
	public void delete(Ticket t);
	
	public void update (Ticket t);
	
	public Ticket findById(Long id);
	
	public Map<Integer,Ticket> findByPrice(float price);
	
	public Map<Integer,Ticket> findBySector(Long Sector);
	
	public Map<Integer,Ticket> findByTicketCategory(TicketCategory tc);
	
	public Map<Integer,Ticket> findByEvent(Event e);
	
	public Map<Integer,Ticket> findByEmpty(boolean empty);
	
	public void updateEmpty(boolean e);
}
