package it.siw.persistence.dao;

import java.util.Map;

import it.siw.model.Sell;
import it.siw.model.User;

public interface SellDAO {
    public void create(Sell modelObject);

    public void delete(Sell s);

    public void update(Sell s);

    public Map<Integer, Sell> findByEvent(int id);

    public Sell reserveSell(User user, Sell sell);

    public boolean removeReservation(Sell sell);

}
