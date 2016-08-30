package it.siw.persistence.dao;

import java.util.Map;

import it.siw.model.TicketCategory;

public interface TicketCategoryDAO {
    public void create(TicketCategory modelObject);

    public void delete(TicketCategory tc);

    public void update(TicketCategory tc);

    public Map<Integer, TicketCategory> findById(Long id);

    public Map<Integer, TicketCategory> findByName(String name);

    public Map<Integer, TicketCategory> findAll();
}
