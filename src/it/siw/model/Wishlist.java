package it.siw.model;

import java.util.Map;

public class Wishlist {
    private int id;
    private String title;
    private User user;
    private Map<Integer, Event> events;

    public Wishlist() {
	// TODO Auto-generated constructor stub
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public Map<Integer, Event> getEvents() {
	return events;
    }

    public void setEvents(Map<Integer, Event> events) {
	this.events = events;
    }
}
