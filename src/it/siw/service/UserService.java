package it.siw.service;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import it.siw.model.Event;
import it.siw.model.Order;
import it.siw.model.Sell;
import it.siw.model.User;
import it.siw.model.Wishlist;
import it.siw.persistence.DAOFactory;
import it.siw.persistence.dao.OrderDAO;
import it.siw.persistence.dao.UserDAO;
import it.siw.persistence.dao.WishlistDAO;

/**
 * <pre>
 * What action this service allow:
 *  1. Update User profile
 *  2. Add a new user wishlist
 *  3. Show all the wishlist
 *  4. Show target wishlist events
 *  5. Add an item to the target wishlist
 *  6. Show User orders
 *  7. Show target order items
 * </pre>
 */
public class UserService {

    public UserService() {
	// TODO Auto-generated constructor stub
    }

    public User updateUser(String json, Integer id, JsonObject result) {
	DAOFactory postgres = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	UserDAO userdao = postgres.getUserDAO();
	Gson gson = new Gson();
	User user = gson.fromJson(json, User.class);
	user.setId(id);
	if (userdao.update(user)) {
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message", "Profile updated with success !");
	    return user;
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Something weird happened, please try again later");
	    return null;
	}

    }

    public void addWishlist(String json, User user, JsonObject result) {
	DAOFactory postgres = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	WishlistDAO wishdao = postgres.getWishlistDAO();
	Gson gson = new Gson();
	Wishlist wishlist = gson.fromJson(json, Wishlist.class);
	wishlist.setUser(user);
	Integer id = wishdao.create(wishlist);
	if (id > 0) {
	    wishlist.setId(id);
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message", "The Wishlist has been added !");
	    result.addProperty("idWishlist", id);
	    result.addProperty("titleWishlist", wishlist.getTitle());
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Something weird happened, please try again later  !");
	}
    }

    public Map<Integer, Wishlist> showWishlist(Integer id, Integer offset, Integer limit) {
	JsonObject object = new JsonObject();
	return showWishlist(id, offset, limit, object);
    }

    public Map<Integer, Wishlist> showWishlist(Integer id, Integer offset, Integer limit, JsonObject result) {
	if (offset == null) {
	    offset = 0;
	}
	if (limit == null) {
	    limit = 5;
	}
	DAOFactory postgres = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	WishlistDAO wishlistdao = postgres.getWishlistDAO();
	Gson gson = new Gson();
	Map<Integer, Wishlist> wishlists = wishlistdao.findByUser(id, offset, limit);
	if (wishlists != null) {
	    Type type = new TypeToken<Map<Integer, Wishlist>>() {
	    }.getType();
	    JsonElement element = gson.toJsonTree(wishlists, type);
	    result = element.getAsJsonObject();
	} else {
	    result.addProperty("result", "FAIL");
	    if (offset > 0) {
		result.addProperty("reason", "No more Wishlists !");
	    } else {
		result.addProperty("reason", "No Wishlists yet !");
	    }
	}
	return wishlists;
    }

    public JsonObject showWishlistEvent(String json, JsonObject result) {
	result = new JsonObject();
	DAOFactory postgres = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	WishlistDAO wishlistdao = postgres.getWishlistDAO();
	Gson gson = new Gson();
	Wishlist source = gson.fromJson(json, Wishlist.class);
	Wishlist tmp = wishlistdao.findById(source.getId());
	if (tmp != null) {
	    Type type = new TypeToken<Map<Integer, Event>>() {
	    }.getType();
	    JsonElement element = gson.toJsonTree(tmp.getEvents(), type);
	    result = element.getAsJsonObject();
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "We are sorry, no events yet, add new one");
	}
	return result;
    }

    public void addEventWishlist(String json, JsonObject result) {
	System.out.println(json);
	DAOFactory postgres = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	WishlistDAO wishlistdao = postgres.getWishlistDAO();
	Gson gson = new Gson();
	JsonParser parser = new JsonParser();
	JsonElement element = parser.parse(json);
	JsonObject object = element.getAsJsonObject();
	Wishlist wishlist = new Wishlist();
	wishlist.setId(object.get("wishlist").getAsInt());
	Event event = new Event();
	event.setId(object.get("event").getAsInt());
	if (wishlistdao.updateEvent(wishlist, event)) {
	    result.addProperty("result", "SUCCESS");
	    result.addProperty("message", "The Event has been added to the wishlist!");
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "We are sorry, you cannot add this event to the selected wishlist");
	}
    }

    public Map<Integer, Order> showOrders(Integer id, Integer offset, Integer limit) {
	return showOrders(id, offset, limit, new JsonObject());
    }

    public Map<Integer, Order> showOrders(Integer id, Integer offset, Integer limit, JsonObject result) {
	if (offset == null) {
	    offset = 0;
	}
	if (limit == null) {
	    limit = 5;
	}
	DAOFactory postgres = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	OrderDAO orderdao = postgres.getOrderDAO();
	Gson gson = new Gson();
	Map<Integer, Order> orders = orderdao.findByUser(id, offset, limit);
	if (orders != null) {
	    Type type = new TypeToken<Map<Integer, Order>>() {
	    }.getType();
	    JsonElement element = gson.toJsonTree(orders, type);
	    result = element.getAsJsonObject();
	} else {
	    result.addProperty("result", "FAIL");
	    if (offset > 0) {
		result.addProperty("reason", "No more Orders !");
	    } else {
		result.addProperty("reason", "No Orders yet !");
	    }
	}
	return orders;
    }

    public JsonObject showOrderItems(String json, JsonObject result) {
	DAOFactory postgres = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
	OrderDAO orderdao = postgres.getOrderDAO();
	Gson gson = new Gson();
	Order source = gson.fromJson(json, Order.class);
	Order tmp = orderdao.findById(source.getId());
	if (tmp != null) {
	    Type type = new TypeToken<Map<Integer, Sell>>() {
	    }.getType();
	    JsonElement element = gson.toJsonTree(tmp.getSells(), type);
	    result = element.getAsJsonObject();
	} else {
	    result.addProperty("result", "FAIL");
	    result.addProperty("reason", "Something weird happend, we are sorry");
	}
	return result;
    }

}
