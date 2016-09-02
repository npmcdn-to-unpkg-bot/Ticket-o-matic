package it.siw.service;

import java.time.LocalDate;
import java.util.Map;

import it.siw.model.Event;
import it.siw.model.Guest;
import it.siw.persistence.DAOFactory;
import it.siw.persistence.dao.EventDAO;
import it.siw.persistence.dao.GuestDAO;

public class SearchService {
    public SearchService() {
    }

    public Map<Integer, Event> getTop() {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventdao = factory.getEventDAO();
	Map<Integer, Event> events;
	events = eventdao.findTop();
	return events;

    }

    public Map<Integer, Guest> getTopGuest() {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	GuestDAO guestdao = factory.getGuestDAO();
	return guestdao.findTop();
    }

    public Map<Integer, Event> getByDate(String date, int limit, int offset) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventdao = factory.getEventDAO();
	Map<Integer, Event> events;
	events = eventdao.findByDate(LocalDate.parse(date), limit, offset);
	return events;

    }

    public Map<Integer, Event> getByPrice(String lower, String upper, int limit, int offset) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventdao = factory.getEventDAO();
	Map<Integer, Event> events;
	events = eventdao.findByPrice(Float.parseFloat(lower), Float.parseFloat(upper), limit, offset);
	return events;

    }

    public Map<Integer, Event> getByTitle(String name, int limit, int offset) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventdao = factory.getEventDAO();
	Map<Integer, Event> events;
	events = eventdao.findByName(name, limit, offset);
	return events;

    }

    public Map<Integer, Event> getByLoc(String loc, int limit, int offset) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventdao = factory.getEventDAO();
	Map<Integer, Event> events;
	events = eventdao.findByLocation(loc, limit, offset);
	return events;

    }

    public Map<Integer, Event> getByCat(int cat, int limit, int offset) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventdao = factory.getEventDAO();
	Map<Integer, Event> events;
	events = eventdao.findByCategory(cat, limit, offset);
	return events;
    }

    public Map<Integer, Event> getByGuest(String guest, int limit, int offset) {
	DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	EventDAO eventdao = factory.getEventDAO();
	Map<Integer, Event> events;
	events = eventdao.findByGuest(guest, limit, offset);
	return events;
    }

}
