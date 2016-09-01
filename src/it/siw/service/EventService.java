package it.siw.service;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import it.siw.model.Event;
import it.siw.model.EventCategory;
import it.siw.model.Sell;
import it.siw.model.TicketCategory;
import it.siw.model.User;
import it.siw.persistence.DAOFactory;
import it.siw.persistence.dao.EventCategoryDAO;
import it.siw.persistence.dao.EventDAO;
import it.siw.persistence.dao.SellDAO;
import it.siw.persistence.dao.TicketCategoryDAO;

public class EventService {

    public EventService() {

    }

    public void createEvent(String json, User user, JsonObject result) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventDAO = factory.getEventDAO();
	Event event = new Gson().fromJson(json, Event.class);
	event.setOrganizer(user);
	if (eventDAO.create(event)) {
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message", "Great ! The event has been created !");
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Something weird happened, try again !");
	}
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

    public Map<Integer, EventCategory> getEventsCategory() {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventCategoryDAO catdao = factory.getEventCategoryDAO();
	return catdao.findAll();

    }

    public Map<Integer, TicketCategory> getTicketsCategory() {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	TicketCategoryDAO catdao = factory.getTicketCategoryDAO();
	return catdao.findAll();

    }

    public void suspendEvent(String json, User user, JsonObject result) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventdao = factory.getEventDAO();
	Event event = new Gson().fromJson(json, Event.class);
	if (eventdao.suspend(event, user)) {
	    result.addProperty("result", "SUCCESS");
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Something weird happened, try again !");
	}

    }

}
