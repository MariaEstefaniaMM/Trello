package memm.org.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import memm.org.utilities.PropertiesReader;

/**
 * Servlet implementation class SessionDestroy
 */
@WebServlet(
		name="Logout",
		urlPatterns="/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		PropertiesReader pr = PropertiesReader.getInstance();
	
		System.out.println(session);
		
		//if(session.isNew()) { 
			//json.put("status", 304).put("response", "You are not logged in");
			//System.out.println("Not logged");
			//session.invalidate();
		//}
		//else { 
			json.put("status",200).put("response", pr.getValue("mssg_session"));
			System.out.println(pr.getValue("mssg_session"));
			session.invalidate();
		//}
		out.print(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
