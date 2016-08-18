package it.siw.model;

import java.util.HashMap;
import java.util.Map;

public class EventCategory {

    private int id;
    private String name;
    private EventCategory anchestor;
    private Map<Integer, EventCategory> children;

    public EventCategory() {
	anchestor = null;
	children = new HashMap<>();
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

    public EventCategory getAnchestor() {
	return anchestor;
    }

    public void setAnchestor(EventCategory anchestor) {
	this.anchestor = anchestor;
    }
}
