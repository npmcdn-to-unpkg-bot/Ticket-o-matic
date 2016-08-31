package it.siw.service;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import it.siw.model.Cart;
import it.siw.model.Order;
import it.siw.model.Sell;
import it.siw.model.User;
import it.siw.persistence.DAOFactory;
import it.siw.persistence.dao.OrderDAO;
import it.siw.persistence.dao.SellDAO;

public class CartService {

    public CartService() {
	// TODO Auto-generated constructor stub
    }

    public void addItem(String json, Cart cart, JsonObject result) {
	DAOFactory postgres = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	SellDAO selldao = postgres.getSellDAO();
	Gson gson = new Gson();
	Sell item = new Sell();
	item = gson.fromJson(json, Sell.class);
	item = selldao.reserveSell(cart.getUser(), item);
	if (item.getId() != 0) {
	    cart.getTickets().put(item.getId(), item);
	    cart.setTotal(cart.getTotal() + item.getPrice());
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message", item.getTicket().getEvent().getName() + " ticket added to the cart !");
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Something went wrong try again!");

	}
    }

    public void removeItem(String json, Cart cart, JsonObject result) {
	DAOFactory postgres = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	SellDAO selldao = postgres.getSellDAO();

	Gson gson = new Gson();
	Sell item = gson.fromJson(json, Sell.class);
	selldao.removeReservation(item);
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
		String reason = "Ops, your reservation for the items below is expired and they are not longer available<br>";
		Boolean error = Boolean.FALSE;
		DAOFactory factory = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
		OrderDAO orderdao = factory.getOrderDAO();
		Order order = new Order(cart, user);
		List<Integer> items = orderdao.create(order);
		reason += "<ul>";
		for (Integer itemid : order.getSells().keySet()) {
		    if (items.contains(itemid)) {
			reason += "<li>" + order.getSells().get(itemid).getTicket().getEvent().getName() + " "
				+ order.getSells().get(itemid).getPrice() + "</li>";
		    }
		}
		reason += "</ul>";
		if (error) {
		    result.addProperty("result", "FAIL");
		    result.addProperty("reason", reason);
		} else {
		    result.addProperty("result", "SUCCESS");
		    result.addProperty("message", "Congratulations, your order has been successfully registered !");
		}
	    } else {
		result.addProperty("result", "FAIL");
		result.addProperty("reason", "Your cart is empty, we are sorry");
	    }
	    cart.getTickets().clear();
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
