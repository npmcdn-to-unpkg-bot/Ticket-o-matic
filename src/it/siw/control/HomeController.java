package it.siw.control;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import it.siw.model.Cart;
import it.siw.model.Event;
import it.siw.model.Order;
import it.siw.model.Sell;
import it.siw.model.Type;
import it.siw.model.User;
import it.siw.model.Wishlist;
import it.siw.persistence.DAOFactory;
import it.siw.service.EventService;
import it.siw.service.SearchService;
import it.siw.service.UserService;

/**
 * <pre>
 * Servlet implementation class homeController
 * Which request this servlet handle:
 * 1. pages switch
 * 2. first data request
 * Pages:
 * 	- main
 * 	- account
 * 	-- details
 * 	-- wishlist
 * 	-- orders
 * 	-- create event // organizer only
 * 	- cart
 * 	- event
 * 	- registration
 * </pre>
 */
@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void destroy() {

	DAOFactory.getDaoFactory(DAOFactory.POSTGRES).destroyDataSource();
	System.err.println("Destroying !");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	HttpSession session = request.getSession();
	String action = request.getParameter("action");

	action = (action == null) ? "" : action;
	String page;
	String account_content = null;

	User user = (User) session.getAttribute("user");
	switch (action) {
	case "details":
	    account_content = "details";
	    page = "account";
	    break;
	case "wishlist": {
	    account_content = "wishlist";
	    page = "account";
	    UserService service = new UserService();
	    Map<Integer, Wishlist> wishlists = service.showWishlist(user.getId(), 0, 10);
	    request.setAttribute("wishlists", wishlists);
	    break;
	}
	case "order": {
	    account_content = "order";
	    page = "account";
	    UserService service = new UserService();
	    Map<Integer, Order> orders = service.showOrders(user.getId(), 0, 10);
	    request.setAttribute("orders", orders);
	    break;
	}
	case "create":
	    if (Type.Organizer.equals(user.getType())) {
		EventService service = new EventService();
		request.setAttribute("ticketcategory", service.getTicketsCategory());
		request.setAttribute("eventcategory", service.getEventsCategory());
		account_content = "create_event";
		page = "account";
	    } else {
		account_content = "details";
		page = "account";
	    }
	    break;
	case "eventlist":
	    if (Type.Organizer.equals(user.getType())) {
		Map<Integer, Event> events = new UserService().showOrganizerEvents(user, 0, 0, new JsonObject());
		request.setAttribute("events", events);
		account_content = "eventlist";
		page = "account";
	    } else {
		account_content = "details";
		page = "account";
	    }
	    break;
	case "cart": {
	    Cart cart = (Cart) session.getAttribute("cart");
	    if (cart == null) {
		cart = new Cart();
		session.setAttribute("cart", cart);
	    }
	    page = "cart";
	    break;
	}

	case "event": {
	    int id = Integer.parseInt(request.getParameter("e"));
	    EventService service = new EventService();
	    Event event = service.getEvent(id);
	    request.setAttribute("event", event);
	    Map<Integer, Sell> sells = service.getTicketAvaible(id);
	    request.setAttribute("sells", sells);
	    if (user != null) {
		Map<Integer, Wishlist> wishlists = new UserService().showWishlist(user.getId(), 0, 10);
		request.setAttribute("wishlists", wishlists);
	    }
	    page = "event";
	    break;
	}
	case "registration":
	    page = "registration";
	    break;
	default: {
	    SearchService search = new SearchService();
	    Map<Integer, Event> events = search.getTop();
	    request.setAttribute("tops", events);
	    page = "main";
	}
	}

	if ("account".equals(page) && account_content != null) {
	    request.setAttribute("account_content", account_content + ".jsp");
	}
	request.setAttribute("page", "content/" + page + ".jsp");

	// this servlet has to forward only to the home.jsp
	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/home.jsp");
	dispatcher.forward(request, response);
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
