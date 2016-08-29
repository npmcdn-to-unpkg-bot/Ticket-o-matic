package it.siw.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import it.siw.model.Cart;
import it.siw.model.Sell;
import it.siw.model.User;

public class CartService {

    public CartService() {
	// TODO Auto-generated constructor stub
    }

    public void addItem(String json, Cart cart, JsonObject result) {
	Gson gson = new Gson();
	Sell item = new Sell();
	item = gson.fromJson(json, Sell.class);
	cart.getTickets().put(item.getId(), item);
	cart.setTotal(cart.getTotal() + item.getPrice());
	result.addProperty("result", "SUCCESS");
	result.addProperty("message", item.getTicket().getEvent().getName() + " ticket added to the cart !");
    }

    public void removeItem(String json, Cart cart, JsonObject result) {
	Gson gson = new Gson();
	Sell item = gson.fromJson(json, Sell.class);
	if ((item = cart.getTickets().remove(item.getId())) != null) {
	    cart.setTotal(cart.getTotal() - item.getPrice());
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message",
		    "Successfully removed " + item.getTicket().getEvent().getName() + " ticket from the cart !");
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Something went wrong !");
	}
    }

    public void buy(Cart cart, User user, JsonObject result) {
	if (user != null) {
	    if (!cart.getTickets().isEmpty()) {
		// TODO THE ORDER PLACEMENT CODE HERE !!
		result.addProperty("result", "SUCCESS");
		result.addProperty("message", " Congratulations, your order has successfully been registered !");
	    } else {

	    }
	} else {
	    result.addProperty("callback", "SIGNIN");
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Can't checkout until loggedin, sign in now");
	}

    }

    public void clear(Cart cart, JsonObject result) {
	if (!cart.getTickets().isEmpty()) {
	    cart.getTickets().clear();
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message", " Cart cleared ! ");
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", " Your cart is empty !");
	}

    }
}
