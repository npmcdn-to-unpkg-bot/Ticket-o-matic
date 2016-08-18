package it.siw.Test;

import java.util.Date;

import com.google.gson.Gson;

import it.siw.model.Event;
import it.siw.model.EventCategory;
import it.siw.model.User;
import it.siw.persistence.DAOFactory;
import it.siw.persistence.dao.EventDAO;
import it.siw.persistence.dao.UserDAO;

public class Test {
    public static void main(String[] args) {
	DAOFactory postgres = DAOFactory.getDaoFactory(DAOFactory.POSTGRES);

	// test user insert
	User user = new User();
	user.setName("Antonio");
	user.setSurname("Fortino");
	user.setEmail("thoniorf@gmail.com");
	user.setPassword("1234");

	User user2 = new User();
	user2.setName("Marco");
	user2.setSurname("Mancuso");

	Gson gson = new Gson();

	String userjson = gson.toJson(user2, User.class);
	User user3 = gson.fromJson(userjson, User.class);
	UserDAO userdao = postgres.getUserDAO();
	userdao.create(user3);

	UserDAO dao = postgres.getUserDAO();
	// FIND USER
	User u = dao.findById(5);
	// crea categorie
	EventCategory rock = new EventCategory();
	rock.setName("Rock and Pop");
	rock.setId(20);

	Event event = new Event();
	event.setName("Rock in Roma");
	event.setLocation("Circus Maximus , Rome , Italy");
	event.setImage("Not Found");
	event.setDescription("LoRem Ipso , Bello bellissimo ! ");
	event.setOrganizer(u);
	event.setSuspended(Boolean.FALSE);
	event.setCategory(rock);
	event.setDate(new Date(System.currentTimeMillis()));
	EventDAO eventdao = postgres.getEventDAO();
	eventdao.create(event);
	System.out.println(u);
    }

}