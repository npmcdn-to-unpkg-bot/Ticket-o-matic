package it.siw.model;

import java.util.Date;

public class Sell {
    private int id;
    private User seller;
    private Ticket ticket;
    private Date date;
    private float price;
    private Order order;
    private Gift gift;

    public Sell() {
	// TODO Auto-generated constructor stub
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public User getSeller() {
	return seller;
    }

    public void setSeller(User seller) {
	this.seller = seller;
    }

    public Ticket getTicket() {
	return ticket;
    }

    public void setTicket(Ticket ticket) {
	this.ticket = ticket;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public float getPrice() {
	return price;
    }

    public void setPrice(float price) {
	this.price = price;
    }

    public Order getOrder() {
	return order;
    }

    public void setOrder(Order order) {
	this.order = order;
    }

    public Gift getGift() {
	return gift;
    }

    public void setGift(Gift gift) {
	this.gift = gift;
    }
}
