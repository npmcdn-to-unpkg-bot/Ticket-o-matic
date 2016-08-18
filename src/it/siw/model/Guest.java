package it.siw.model;

import java.util.Map;

public class Guest {
    private int id;
    private String name;
    private String image;

    private Map<Integer, Event> events;

    public Guest() {
	// TODO Auto-generated constructor stub
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public Map<Integer, Event> getEvents() {
	return events;
    }

    public void setEvents(Map<Integer, Event> events) {
	this.events = events;
    }
}
