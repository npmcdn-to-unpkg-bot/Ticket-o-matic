package it.siw.persistence.dao;

import java.sql.Date;
import java.util.Map;

import it.siw.model.Gift;
import it.siw.model.Order;
import it.siw.model.Sell;
import it.siw.model.Ticket;
import it.siw.model.User;

public interface SellDAO {
    public void create(Sell modelObject);

    public void delete(Sell s);

    public void update(Sell s);

    public Integer findLastId(Order o);

    public Sell findById(Long Id);

    public Map<Integer, Sell> findByUser(User u);

    public Map<Integer, Sell> findByUser(User u, Integer offset, Integer limit);

    public Map<Integer, Sell> findByTicket(Ticket t);

    public Map<Integer, Sell> findByTicket(Ticket t, Integer offset, Integer limit);

    public Map<Integer, Sell> findByDate(Date d);

    public Map<Integer, Sell> findByDate(Date d, Integer offset, Integer limit);

    public Map<Integer, Sell> findByPrice(float price);

    public Map<Integer, Sell> findByPrice(float price, Integer offset, Integer limit);

    public Map<Integer, Sell> findByOrder(Order o);

    public Map<Integer, Sell> findByOrder(Order o, Integer offset, Integer limit);

    public Map<Integer, Sell> findByGift(Gift g);

    public Map<Integer, Sell> findByGift(Gift g, Integer offset, Integer limit);

    public Map<Integer, Sell> findByEvent(int id);

    public Integer reserveById(int user_id, int ticket_id);

}
