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

import it.siw.model.Cart;
import it.siw.model.User;
import it.siw.service.CartService;

/**
 * Servlet implementation class CartController
 */
@WebServlet("/cart")
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
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
	// create the session to handle the cart
	HttpSession session = request.getSession();
	Cart cart = (Cart) session.getAttribute("cart");
	if (cart == null) {
	    cart = new Cart();
	    session.setAttribute("cart", cart);
	    User tmp = new User();
	    tmp.setSession_id(session.getId());
	    cart.setUser(tmp);
	}
	User user = (User) session.getAttribute("user");
	BufferedReader br = new BufferedReader(request.getReader());
	String json = null;
	if (br != null) {
	    json = br.readLine();
	}
	String action = request.getParameter("action");
	JsonObject result = new JsonObject();

	switch (action) {
	case "add":
	    new CartService().addItem(json, cart, result);
	    break;
	case "remove":
	    new CartService().removeItem(json, cart, result);
	    break;
	case "checkout":
	    new CartService().buy(cart, user, result);
	    break;
	case "clear":
	    new CartService().clear(cart, result);
	    break;
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
