package it.siw.model;

import java.util.Map;

public class Organizer extends User {
    private Map<Integer, Event> events;

    public Organizer() 
    {
    	// TODO Auto-generated constructor stub
    }

    public Map<Integer, Event> getEvents() {
	return events;
    }

    public void setEvents(Map<Integer, Event> events) {
	this.events = events;
    }
}
