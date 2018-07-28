package memm.org.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import memm.org.DataBase;
import memm.org.utilities.PropertiesReader;

/**
 * Servlet implementation class Test
 */
@WebServlet(
		name="Signup",
		urlPatterns="/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		
		out.print(json.toString());
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();
		
		String email = reqBody.getString("email"); 
 		String password = reqBody.getString("password");
 		String name = reqBody.getString("name"); 
 		String lastname = reqBody.getString("lastname");
 		String username = reqBody.getString("username");		
		
		System.out.println(email+password+name+lastname+username);
		
		try {			
	 			if(db.checkSignup(email, username)==0) {
	 		    	db.newAccount(name,lastname,username,password,email);
	 		    	json.put("response", pr.getValue("mssg_signUp")).put("status", 200);
	 		    	System.out.println(pr.getValue("mssg_successfulSU"));
	 			}else {
	 		    	json.put("response", pr.getValue("mssg_used")).put("status", 400);
	 		    	System.out.println(pr.getValue("mssg_SUFail")); 
	 			}
			
		} catch (Exception e) {
			System.out.println("error");
			System.out.println(e. getMessage());
		}

		out.print(json.toString());
	}

}
