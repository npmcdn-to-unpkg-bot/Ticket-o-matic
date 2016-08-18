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
import it.siw.service.UserService;

/**
 * Servlet implementation class ClientController
 */
@WebServlet("/user")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
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
	HttpSession session = request.getSession();
	BufferedReader br = new BufferedReader(request.getReader());
	String json = "";
	if (br != null) {
	    json = br.readLine();
	}
	String action = request.getParameter("action");
	JsonObject result = new JsonObject();

	User user = (User) session.getAttribute("user");
	if (user == null) {
	    // TODO goto ERROR PAGE - you're not an user
	}
	switch (action) {
	case "update": {
	    UserService service = new UserService();
	    User tmp = service.updateUser(json, user.getId(), result);
	    if (tmp != null) {
		session.setAttribute("user", tmp);
	    }
	    break;
	}
	case "addwishlist": {
	    UserService service = new UserService();
	    service.addWishlist(json, user, result);
	    break;
	}
	case "showwishlist": {
	    Integer offset = Integer.getInteger(request.getParameter("offset"));
	    Integer limit = Integer.getInteger(request.getParameter("limit"));
	    UserService service = new UserService();
	    service.showWishlist(user.getId(), offset, limit, result);
	    break;
	}
	case "showevent": {
	    UserService service = new UserService();
	    result = service.showWishlistEvent(json, result);
	    break;
	}
	case "addevent": {
	    UserService service = new UserService();
	    service.addEventWishlist(json, result);
	    break;
	}
	case "showorder": {
	    Integer offset = Integer.getInteger(request.getParameter("offset"));
	    Integer limit = Integer.getInteger(request.getParameter("limit"));
	    UserService service = new UserService();
	    service.showOrders(user.getId(), offset, limit, result);
	    break;
	}
	case "showitems": {
	    UserService service = new UserService();
	    result = service.showOrderItems(json, result);
	    break;
	}
	default:
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
