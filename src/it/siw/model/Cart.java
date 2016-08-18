package it.siw.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private User user;
    private Map<Integer, Ticket> tickets;
    private Long total;

    public Cart() {
	user = null;
	tickets = new HashMap<Integer, Ticket>();
	total = 0L;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public Map<Integer, Ticket> getTickets() {
	return tickets;
    }

    public void setTickets(Map<Integer, Ticket> tickets) {
	this.tickets = tickets;
    }

    public Long getTotal() {
	return total;
    }

    public void setTotal(Long total) {
	this.total = total;
    }

}
