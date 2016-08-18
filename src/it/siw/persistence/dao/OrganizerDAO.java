package it.siw.persistence.dao;

import java.util.Map;

import it.siw.model.Event;
import it.siw.model.Organizer;

public interface OrganizerDAO 
{
	public void create(Organizer modelObject);
	
	public void delete(Organizer o);
	
	public void update(Organizer o);
	
	public Map<Integer,Organizer> setEvents(Event e);
	
	public Map<Integer,Organizer> getEvents(Event e);
	
	
}
