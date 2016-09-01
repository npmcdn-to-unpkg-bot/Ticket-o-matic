package it.siw.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

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
	int limit = 10;
	int offset = 0;
	if (json != null) {
	    JsonObject params = new JsonParser().parse(json).getAsJsonObject();
	    limit = params.get("limit").getAsInt();
	    offset = params.get("offset").getAsInt();
	}
	Map<Integer, Event> events = null;
	switch (filter) {
	case "bydate": {
	    String date = request.getParameter("date");
	    events = new SearchService().getByDate(date, limit, offset);
	    break;
	}
	case "byprice": {
	    String lower = request.getParameter("pricelower");
	    String upper = request.getParameter("priceupper");
	    events = new SearchService().getByPrice(lower, upper, limit, offset);
	    break;
	}
	case "bytitle": {
	    String title = request.getParameter("search");
	    events = new SearchService().getByTitle(title, limit, offset);
	    break;
	}
	case "byloc": {
	    String loc = request.getParameter("search");
	    events = new SearchService().getByLoc(loc, limit, offset);
	    break;
	}
	case "bycat": {
	    int cat = Integer.parseInt(request.getParameter("search"));
	    events = new SearchService().getByCat(cat, limit, offset);
	    break;
	}
	case "byguest": {
	    String guest = request.getParameter("search");
	    events = new SearchService().getByGuest(guest, limit, offset);
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
	    Type type = new TypeToken<Map<Integer, Event>>() {
	    }.getType();
	    JsonElement element = new Gson().toJsonTree(events, type);
	    result = element.getAsJsonObject();
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
