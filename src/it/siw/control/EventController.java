package it.siw.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.siw.service.EventService;

/**
 * Servlet implementation class EventController
 */
@WebServlet("/EventController")
public class EventController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventController() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String json = ""; // parse request in json format
	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	if (br != null) {
	    json = br.readLine();
	}
	response.setContentType("text/html");
	String action = request.getParameter("action");
	switch (action) {
	case "CREATE":
	    EventService eventService = new EventService(json);
	    if (eventService.createEvent()) {
		response.getWriter().append("GOOD JOB");
		System.out.println("Event created!");
	    }
	    break;
	case "UPDATE":
		EventService eventService1 =new EventService(json);
		response.getWriter().append("GOOD JOB");
		eventService1.updateEvent(action);
	    break;
	case "SHOW":
		EventService eventService2 = new EventService(json);
		response.getWriter().append("GOOD JOB");
		eventService2.showEvent(json);
	    break;
	}

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(request, response);
    }

}
