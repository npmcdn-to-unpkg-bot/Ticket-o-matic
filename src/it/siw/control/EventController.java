package it.siw.control;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import it.siw.model.User;
import it.siw.service.EventService;

/**
 * Servlet implementation class EventController
 */
@WebServlet("/event")
public class EventController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	HttpSession session = request.getSession();
	BufferedReader br = new BufferedReader(request.getReader());
	String json = "";
	if (br != null) {
	    json = br.readLine();
	}
	String action = request.getParameter("action");
	JsonObject result = new JsonObject();

	User user = (User) session.getAttribute("user");
	switch (action) {
	case "create":
	    new EventService().createEvent(json, user, result);
	    break;
	case "update":
	    EventService eventService1 = new EventService();
	    response.getWriter().append("GOOD JOB");
	    eventService1.updateEvent(action);
	    break;
	}
	response.getWriter().write(result.toString());
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
