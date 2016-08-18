package it.siw.service;

import java.util.Map;

import javax.annotation.sql.DataSourceDefinition;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariDataSource;

import it.siw.model.Event;
import it.siw.persistence.DAOFactory;
import it.siw.persistence.dao.EventDAO;
import it.siw.persistence.dao.implementation.EventDaoJDBC;

public class EventService {
    Gson gson;
    String json;
    Event event;

    public EventService(String json) {
	this.json = json;
	this.gson = new Gson();
	event = gson.fromJson(json, Event.class);
    }

    public boolean createEvent() 
    {
	// create the dao that save event on the db
    	EventDAO eventDAO = DAOFactory.getDaoFactory(DAOFactory.POSTGRES).getEventDAO();
    	eventDAO.create(event);
    	return true;
    }
    public void updateEvent(String data)
    {
    	DAOFactory factory= DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
    	EventDAO eventDAO =factory.getEventDAO();
    	Gson gson= new Gson();
    	Event event=gson.fromJson(data, Event.class);
    	eventDAO.update(event);
    }
    public Map<Integer, Event> showEvent(String data)
    {
    	
    	DAOFactory factory= DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
    	EventDAO eventDAO =factory.getEventDAO();
    	return eventDAO.findByName(data);
    	
    	
    }

}
