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
 * Servlet implementation class UserBoard
 */
@WebServlet(
		name="UserBoard",
		urlPatterns="/UserBoard")
public class UserBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserBoard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		json.put("colab", db.getColab(id)).put("status", 200).put("response", pr.getValue("mssg_success"));
		
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
		
		try {
			Integer board_id = reqBody.getInt("board_id");
			String username = reqBody.getString("username");
			Integer type = reqBody.getInt("type");
			
			Integer user_id = db.checkSignup("", username);
			
			System.out.println(board_id + user_id+ type);
			if ( user_id !=0) {
			if (db.getTypeUserBoard(board_id, user_id) ==0) {
				db. newColaborator(board_id, user_id, type) ;
				json.put("status", 200).put("response", pr.getValue("mssg_colabAdded")).put("user_id", user_id).put("username", db.username);
				System.out.println(pr.getValue("mssg_colabAdded"));
			}else {
				json.put("response", pr.getValue("mssg_colabExist")).put("status", 400);
 		    	System.out.println(pr.getValue("mssg_colabExist"));
			}		
			}else {
				json.put("response", pr.getValue("mssg_userNotExist")).put("status", 400);
 		    	System.out.println(pr.getValue("mssg_userNotExist"));
			}
		}catch (Exception e) {
			System.out.println("error");
			System.out.println(e. getMessage());
			e.printStackTrace();
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
			Integer board_id = reqBody.getInt("board_id");
			Integer user_id = reqBody.getInt("user_id");
			
			//if (db.getTypeUserBoard(board_id, user_id)!=0) {
				db.deleteUserBoard(board_id, user_id);
				json.put("status", 200).put("response", pr.getValue("mssg_colabDeleted"));
				System.out.println(pr.getValue("mssg_colabDeleted"));
			//}else {
				//json.put("response", "Board does not exist").put("status", 400);
 		    	//System.out.println("Delete Board Fail");
			//}		
		
		}catch (Exception e) {
			System.out.println("error");
			json.put("response", pr.getValue("mssg_deleteFail")).put("status", 400);
			System.out.println(e. getMessage());
			e.printStackTrace();
		}
		
		out.print(json.toString());
		
		}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();
		
		try {
			Integer board_id = reqBody.getInt("board_id");
			Integer user_id = reqBody.getInt("user_id");
			Integer newType = reqBody.getInt("newType");
			
			System.out.println(board_id+newType);

				db.updateUserBoard(board_id, user_id, newType);
				json.put("status", 200).put("response", pr.getValue("mssg_colabUpdate"));
				System.out.println(pr.getValue("mssg_colabUpdate"));	
		
		}catch (Exception e) {
			System.out.println("error");
			System.out.println(e. getMessage());
			e.printStackTrace();
			json.put("response", pr.getValue("mssg_updateFail")).put("status", 400);
		    System.out.println(pr.getValue("mssg_updateFail"));
		}
		
		out.print(json.toString());
		
		}
}
