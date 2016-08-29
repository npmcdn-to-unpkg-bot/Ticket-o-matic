package it.siw.service;

import java.util.Map;

import it.siw.model.Event;
import it.siw.persistence.DAOFactory;
import it.siw.persistence.dao.EventDAO;

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

}
