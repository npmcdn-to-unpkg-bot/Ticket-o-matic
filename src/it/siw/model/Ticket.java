package it.siw.model;

public class Ticket {
    private int id;
    private float price;
    private String sector;
    private String row;
    private String seat;
    private TicketCategory category;
    private Event event;
    private boolean empty;

    public Ticket() {
	// TODO Auto-generated constructor stub
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public float getPrice() {
	return price;
    }

    public void setPrice(float price) {
	this.price = price;
    }

    public String getSector() {
	return sector;
    }

    public void setSector(String sector) {
	this.sector = sector;
    }

    public String getRow() {
	return row;
    }

    public void setRow(String row) {
	this.row = row;
    }

    public String getSeat() {
	return seat;
    }

    public void setSeat(String seat) {
	this.seat = seat;
    }

    public TicketCategory getCategory() {
	return category;
    }

    public void setCategory(TicketCategory category) {
	this.category = category;
    }

    public Event getEvent() {
	return event;
    }

    public void setEvent(Event event) {
	this.event = event;
    }

    public boolean isEmpty() {
	return empty;
    }

    public void setEmpty(boolean empty) {
	this.empty = empty;
    }

}
