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

import it.siw.model.Order;
import it.siw.model.Type;
import it.siw.model.User;
import it.siw.model.Wishlist;
import it.siw.service.UserService;

/**
 * <pre>
 * Servlet implementation class homeController
 * Which request this servlet handle:
 * 1. pages switch
 *
 * Pages:
 * 	- main
 * 	- account
 * 	-- details
 * 	-- wishlist
 * 	-- orders
 * 	-- create event // organizer only
 * 	- cart
 * 	- event
 * 	- search
 * 	- registration
 * </pre>
 */
@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
	super();
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

	User user = (User) session.getAttribute("user");
	UserService service;

	switch (action) {
	case "wishlist":
	    page = "wishlist";
	    service = new UserService();
	    Map<Integer, Wishlist> wishlists = service.showWishlist(user.getId(), 0, 10);
	    request.setAttribute("wishlists", wishlists);
	    break;
	case "order":
	    page = "order";
	    service = new UserService();
	    Map<Integer, Order> orders = service.showOrders(user.getId(), 0, 10);
	    request.setAttribute("orders", orders);
	    break;
	case "create":
	    if (Type.Organizer.equals(user.getType())) {
		page = "details";
	    } else {
		page = "details";
	    }
	    break;
	case "cart":
	    page = "cart";
	    break;
	case "event":
	    page = "event";
	    break;
	case "registration":
	    page = "registration";
	    break;
	case "search":
	    page = "search";
	    break;
	default:
	    page = "main";
	}

	request.setAttribute("page", "content/" + page + ".html");

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
