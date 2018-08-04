package memm.org.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import memm.org.DataBase;
import memm.org.User;
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
		HttpSession session = request.getSession();
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();
		
		System.out.println("ESTOY AQUI g");
		
		String username = request.getParameter("username");
		
		if (username=="") {		
			json.put("users", db.allUsers()).put("status", 200).put("response", pr.getValue("mssg_success"));
		}else {
			json.put("users", db.Users(username)).put("status", 200).put("response", pr.getValue("mssg_success"));
		}
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
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();
		
		try {
			Integer id = reqBody.getInt("user_id");
			
			if (id!=0) {
				db.deleteUser(id);
				json.put("status", 200).put("response", "user deleted");
				System.out.println("user deleted");
			}else {
				json.put("response", "user does not exist").put("status", 400);
 		    	System.out.println("user does not exis");
			}		
		
		}catch (Exception e) {
			System.out.println("error");
			System.out.println(e. getMessage());
			e.printStackTrace();
		}
		
		out.print(json.toString());
		
		}

}
