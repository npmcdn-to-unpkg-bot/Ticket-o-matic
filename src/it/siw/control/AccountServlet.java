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
import it.siw.service.AccountService;

/**
 * <pre>
 * Servlet implementation class AccountServlet
 * - sign in
 * - sign out
 * - sign up
 * </pre>
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountServlet() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	// read the ajax request
	BufferedReader br = new BufferedReader(request.getReader());
	String json = "";
	if (br != null) {
	    json = br.readLine();
	}

	// get the action
	String action = request.getParameter("action");
	// inizialize json response object;
	JsonObject result = new JsonObject();

	switch (action) {
	case "signup":
	    AccountService.signUp(json, result);
	    break;
	case "signin":
	    User user = AccountService.signIn(json, result);
	    if (user != null) {
		HttpSession session = request.getSession();
		user.setSession_id(session.getId());
		session.setAttribute("user", user);
	    }
	    break;
	case "logout":
	    request.getSession().invalidate();
	    response.sendRedirect("home");
	    break;
	default:
	    result.addProperty("result", "FAIL");
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
	doGet(request, response);
    }

}
