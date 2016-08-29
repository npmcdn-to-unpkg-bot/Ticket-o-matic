package it.siw.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private User user;
    private Map<Integer, Sell> tickets;
    private float total;

    public Cart() {
	user = null;
	tickets = new HashMap<Integer, Sell>();
	total = 0;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public Map<Integer, Sell> getTickets() {
	return tickets;
    }

    public void setTickets(Map<Integer, Sell> tickets) {

	this.tickets = tickets;
    }

    public float getTotal() {
	return total;
    }

    public void setTotal(float f) {
	BigDecimal bc = new BigDecimal(f).setScale(2, BigDecimal.ROUND_HALF_UP);
	f = bc.floatValue();
	this.total = f;
    }

}
