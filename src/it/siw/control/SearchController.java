package it.siw.control;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.siw.service.SearchService;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/SearchController")
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
	// TODO Auto-generated method stub

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub

	BufferedReader br = new BufferedReader(request.getReader());
	String json = "";
	if (br != null) {
	    json = br.readLine();
	}

	response.setContentType("text/html");
	String action = request.getParameter("action");
	JsonObject result = new JsonObject();

	switch (action) 
	{
		case "searchById":
		{
			SearchService searchService = new SearchService();
			searchService.getEventById(action,result);
		    break;
		}
		case "searchByGuest":
		{   
			SearchService searchService = new SearchService();
			searchService.getEventbyfindGuest(action,result);
			break;
		}
		case "searchByticket":
		{
			SearchService searchService = new SearchService();
			searchService.geEventfindTicket(action,result);  
			break;	
		}
		case "searchByLastid":
		{
			SearchService searchService = new SearchService();
			searchService.getEventfindLastId(action,result);
			break;
		}
		case "findByName":
		{
			SearchService searchService = new SearchService();
			searchService.getEventFindByName(action, result);
			break;
		}
		case "findByDate":
		{
			SearchService searchService = new SearchService();
			searchService.getEventFindByDate(action, result);
			break;
		}
		case "findByPrice":
		{
			SearchService searchService = new SearchService();
			searchService.getEventFindByPrice(action, result);
			break;
		}
		case "findByLocation":
		{
			SearchService searchService = new SearchService();
			searchService.getEventFindByLocation(action ,result);  
			break;
		}
		case "findByGuest":
		{
			SearchService searchService = new SearchService();
			searchService.getEventFindByGuest(action,result);
			break;
		}
		case "findByCategory":
		{
			SearchService searchService = new SearchService();
			searchService.getEventFindByCategory(action,result);  
			break;
		}
		default:
		    break;
	}
	response.getWriter().append("Served at: ").append(request.getContextPath());
    }

}

