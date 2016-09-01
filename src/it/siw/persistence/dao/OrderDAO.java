package it.siw.persistence.dao;

import java.util.List;
import java.util.Map;

import it.siw.model.Order;
import it.siw.model.User;

public interface OrderDAO {

    public List<Integer> create(Order modelObject);

    public void delete(Order o);

    public void update(Order o);

    public Map<Integer, Order> findByUser(Integer id);

    public Order findById(Integer id);

    public Integer findLastId(User u);

}
