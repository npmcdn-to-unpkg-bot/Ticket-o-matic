package it.siw.service;

import java.util.Map;

import com.google.gson.Gson;

import it.siw.model.Event;
import it.siw.model.Sell;
import it.siw.persistence.DAOFactory;
import it.siw.persistence.dao.EventDAO;
import it.siw.persistence.dao.SellDAO;

public class EventService {
    Gson gson;
    String json;
    Event event;

    public EventService(String json) {
	this.json = json;
	this.gson = new Gson();
	event = gson.fromJson(json, Event.class);
    }

    public EventService() {

    }

    public boolean createEvent() {
	// create the dao that save event on the db
	EventDAO eventDAO = DAOFactory.getDaoFactory(DAOFactory.POSTGRES).getEventDAO();
	eventDAO.create(event);
	return true;
    }

    public void updateEvent(String data) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventDAO = factory.getEventDAO();
	Gson gson = new Gson();
	Event event = gson.fromJson(data, Event.class);
	eventDAO.update(event);
    }

    public Event getEvent(int id) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventdao = factory.getEventDAO();
	Event event = eventdao.findById(id);
	return event;
    }

    public Map<Integer, Sell> getTicketAvaible(int eventid) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	SellDAO selldao = factory.getSellDAO();
	return selldao.findByEvent(eventid);
    }

}
