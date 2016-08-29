package it.siw.persistence.dao;

import java.util.Date;
import java.util.Map;

import it.siw.model.Event;
import it.siw.model.User;

public interface EventDAO {

    public boolean create(Event modelObject);

    public Event findById(Integer id);

    public Event findGuest(Integer id);

    public Event findTicket(Integer id);

    public Integer findLastId(User o);

    public Map<Integer, Event> findByName(String name);

    public Map<Integer, Event> findByName(String name, Integer offset, Integer limit);

    public Map<Integer, Event> findByDate(Date date);

    public Map<Integer, Event> findByDate(Date date, Integer offset, Integer limit);

    public Map<Integer, Event> findByPrice(float min, float max);

    public Map<Integer, Event> findByPrice(Long min, Long max, Integer offset, Integer limit);

    public Map<Integer, Event> findByLocation(String location);

    public Map<Integer, Event> findByLocation(String location, Integer offset, Integer limit);

    public Map<Integer, Event> findByGuest(String guest);

    public Map<Integer, Event> findByGuest(String guest, Integer offset, Integer limit);

    public Map<Integer, Event> findByCategory(String category);

    public Map<Integer, Event> findByCategory(String category, Integer offset, Integer limit);

    public Map<Integer, Event> findTop();

    public void update(Event e);

    public void delete(Event e);
}
