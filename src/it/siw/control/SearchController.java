package it.siw.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.siw.model.Event;
import it.siw.service.SearchService;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/search")
public class SearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchController() {
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
	BufferedReader br = new BufferedReader(request.getReader());
	String json = "";
	if (br != null) {
	    json = br.readLine();
	}
	String filter = request.getParameter("filters");
	JsonObject result = new JsonObject();
	Map<Integer, Event> events = null;

	switch (filter) {
	case "bydate": {
	    String date = request.getParameter("date");
	    events = new SearchService().getByDate(date, 10, 0);
	    break;
	}
	case "byprice": {
	    String lower = request.getParameter("pricelower");
	    String upper = request.getParameter("priceupper");
	    events = new SearchService().getByPrice(lower, upper, 10, 0);
	    break;
	}
	case "bytitle": {
	    String title = request.getParameter("search");
	    events = new SearchService().getByTitle(title, 10, 0);
	    break;
	}
	case "byloc": {
	    String loc = request.getParameter("search");
	    events = new SearchService().getByLoc(loc, 10, 10);
	    break;
	}
	case "bycat": {
	    int cat = Integer.parseInt(request.getParameter("search"));
	    events = new SearchService().getByCat(cat, 10, 0);
	    break;
	}
	default:
	    break;
	}

	if (json == null) {
	    request.setAttribute("results", events);
	    request.setAttribute("page", "content/search.jsp");
	    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/home.jsp");
	    dispatcher.forward(request, response);
	} else {
	    response.getWriter().write(result.toString());
	}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	doGet(request, response);
    }

}
