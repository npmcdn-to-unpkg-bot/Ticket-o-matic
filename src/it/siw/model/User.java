package it.siw.model;

import java.util.HashMap;
import java.util.Map;

public class User {

    private int id;
    private String session_id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private Type type;
    private float coins;

    private Map<Integer, Order> orders;
    private Map<Integer, Gift> gifts;
    private Map<Integer, Wishlist> wishlist;
    private Map<Integer, Review> reviews;
    private Map<Integer, Sell> sells;

    public User() {
	username = "";
	password = "";
	email = "";
	name = "";
	surname = "";
	coins = 0L;
	orders = new HashMap<>();
	wishlist = new HashMap<>();
	type = Type.Customer;

    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getSession_id() {
	return session_id;
    }

    public void setSession_id(String session_id) {
	this.session_id = session_id;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSurname() {
	return surname;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }

    public float getCoins() {
	return coins;
    }

    public void setCoins(float coins) {
	this.coins = coins;
    }

    public Map<Integer, Order> getOrders() {
	return orders;
    }

    public void setOrders(Map<Integer, Order> orders) {
	this.orders = orders;
    }

    public Map<Integer, Gift> getGifts() {
	return gifts;
    }

    public void setGifts(Map<Integer, Gift> gifts) {
	this.gifts = gifts;
    }

    public Map<Integer, Wishlist> getWhishlists() {
	return wishlist;
    }

    public void setWhishlists(Map<Integer, Wishlist> whishlists) {
	this.wishlist = whishlists;
    }

    public Map<Integer, Review> getReviews() {
	return reviews;
    }

    public void setReviews(Map<Integer, Review> reviews) {
	this.reviews = reviews;
    }

    public Map<Integer, Sell> getSells() {
	return sells;
    }

    public void setSells(Map<Integer, Sell> sells) {
	this.sells = sells;
    }

    public Type getType() {
	// TODO Auto-generated method stub
	return type;
    }

    public void setType(Type type) {
	this.type = type;
    }

    @Override
    public String toString() {
	return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", name="
		+ name + ", surname=" + surname + ", type=" + type + ", coins=" + coins + "]";
    }

}
