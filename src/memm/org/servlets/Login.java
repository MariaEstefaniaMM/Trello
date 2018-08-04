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

import Proxy.CheckAdminProxy;
import memm.org.utilities.Encrypt;
import memm.org.utilities.PropertiesReader;
import memm.org.DataBase;
import memm.org.User;

/**
 * Servlet implementation class Session
 */
@WebServlet(
		name="Login",
		urlPatterns="/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();
		CheckAdminProxy proxy = new CheckAdminProxy();		
		
		
			String email = reqBody.getString("email"); 
			String password = reqBody.getString("password");
			
			Integer userId = db.getUserId(email, Encrypt.returnEncrypt(password));	
			boolean isAdmin = proxy.checkAdmin(email);
			
			if(userId!=0) {
					User user = new User(userId, db.username, isAdmin);
					storeValue(user, session);
					System.out.println(pr.getValue("mssg_successful"));
					json.put("status", 200).put("response", pr.getValue("mssg_successful")).put("isAdmin", isAdmin);
					System.out.println("Succesful login: "+ email+ " User ID: "+userId+ " Username "+db.username+" is Admin "+ isAdmin);
			}else {
				System.out.println(pr.getValue("mssg_wrong"));
				json.put("response", pr.getValue("mssg_wrong")).put("status", 400);
				session.invalidate();
				System.out.println("Wrong data");
			}

		System.out.println(json.toString());
		System.out.println(session);
		out.println(json.toString());
	}		
	
	public void storeValue(User user, HttpSession session) {

			session.setAttribute("user", user);

	}
}
