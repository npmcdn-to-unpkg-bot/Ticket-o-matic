package it.siw.persistence.dao;

import java.util.Map;

import it.siw.model.EventCategory;

public interface EventCategoryDAO {

    public boolean create(EventCategory modelobject);

    public EventCategory findById(Integer id);

    public EventCategory findByName(String name);

    public Map<Integer, EventCategory> findChildren(Integer id);

    public boolean update(EventCategory modelobject);

    public boolean delete(EventCategory modelobject);

    public Map<Integer, EventCategory> findAll();
}
