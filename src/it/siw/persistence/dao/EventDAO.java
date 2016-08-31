package it.siw.persistence.dao;

import java.time.LocalDate;
import java.util.Map;

import it.siw.model.Event;
import it.siw.model.User;

public interface EventDAO {

    public boolean create(Event modelObject);

    public void delete(Event e);

    public Map<Integer, Event> findByCategory(int cat, int limit, int offset);

    public Map<Integer, Event> findByDate(LocalDate date, int limit, int offset);

    public Map<Integer, Event> findByGuest(String guest);

    public Event findById(Integer id);

    public Map<Integer, Event> findByLocation(String location, int limit, int offset);

    public Map<Integer, Event> findByName(String name, int limit, int offset);

    public Map<Integer, Event> findByOrganizer(User u);

    public Map<Integer, Event> findByPrice(float min, float max, int limit, int offset);

    public Map<Integer, Event> findTop();

    public void update(Event e);

}
