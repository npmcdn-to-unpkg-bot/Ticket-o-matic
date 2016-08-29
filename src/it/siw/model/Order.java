package it.siw.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private int id;
    private Date date;
    private User user;
    private float total;

    private Map<Integer, Sell> sells;

    public Order() {
	// TODO Auto-generated constructor stub
    }

    public Order(Cart cart, User user) throws NullPointerException {
	this.user = user;
	this.sells = new HashMap<>(cart.getTickets());
	this.date = new Date(System.currentTimeMillis());
	this.total = cart.getTotal();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public float getTotal() {
	return total;
    }

    public void setTotal(float total) {
	this.total = total;
    }

    public Map<Integer, Sell> getSells() {
	return sells;
    }

    public void setSells(Map<Integer, Sell> sells) {
	this.sells = sells;
    }
}
