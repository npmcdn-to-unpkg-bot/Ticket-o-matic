package it.siw.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import it.siw.model.Cart;
import it.siw.model.Ticket;

public class CartService {

    public CartService() {
	// TODO Auto-generated constructor stub
    }

    public void addItem(String json, Cart cart, JsonObject result) {
	if (json == null) {
	    return;
	}
	Gson gson = new Gson();
	Ticket item = gson.fromJson(json, Ticket.class);
	if (cart.getTickets().put(item.getId(), item) != null) {
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message", "Successfully added " + item.getEvent().getName() + " ticket to the cart !");
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Something went wrong !");
	}
    }

    public void removeItem(String json, Cart cart, JsonObject result) {
	Gson gson = new Gson();
	Ticket item = gson.fromJson(json, Ticket.class);
	if (cart.getTickets().remove(item.getId()) != null) {
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message",
		    "Successfully removed " + item.getEvent().getName() + " ticket from the cart !");
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Something went wrong !");
	}
    }

    public void buy(Cart cart, JsonObject result) {
	if (!cart.getTickets().isEmpty()) {
	    // TODO THE ORDER PLACEMENT CODE HERE !!
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message", " Congratulations, your order has successfully been registered !");
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Your cart is empty, add something to buy !");
	}
    }

    public void clear(Cart cart, JsonObject result) {
	if (!cart.getTickets().isEmpty()) {
	    cart.getTickets().clear();
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message", " Cart cleared !");
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", " Your cart is empty !");
	}
    }
}
