package it.siw.model;

public class Review {
    private int id;
    private User user;
    private Event event;
    private String title;
    private String text;
    private int feedback;

    public Review() {
	// TODO Auto-generated constructor stub
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public Event getEvent() {
	return event;
    }

    public void setEvent(Event event) {
	this.event = event;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public int getFeedback() {
	return feedback;
    }

    public void setFeedback(int feedback) {
	this.feedback = feedback;
    }
}
