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
 * Servlet implementation class Board
 */
@WebServlet(
		name="Board",
		urlPatterns="/Board")
public class Board extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Board() {
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
		
		String board_name = request.getParameter("board_name");
		
		if (board_name=="") {		
			User user = (User) session.getAttribute("user");
			json.put("boards", db.userBoards(user.getId())).put("status", 200).put("response", pr.getValue("mssg_success"));
		}else {
			json.put("boards", db.allBoards(board_name)).put("status", 200).put("response", pr.getValue("mssg_success"));
		}
		out.print(json.toString());
		
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
		
		try {
			String name = reqBody.getString("board_name");
			User user = (User) session.getAttribute("user");
			System.out.println(user.getId()+name);
							
			if (db.getBoard(user.getId(), name)==0) {
				Integer board_id = db.newBoard(name, user.getId());
				json.put("status", 200).put("response", pr.getValue("mssg_boardCreated")).put("board_id", board_id);
				System.out.println(pr.getValue("mssg_boardCreated"));
			}else {
				json.put("response", pr.getValue("mssg_boardUsed")).put("status", 400);
 		    	System.out.println(pr.getValue("mssg_boardUsed"));
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
			Integer id = reqBody.getInt("board_id");
			
			if (id!=0) {
				db.deleteBoard(id);
				json.put("status", 200).put("response", pr.getValue("mssg_boardDeleted"));
				System.out.println(pr.getValue("mssg_boardDeleted"));
			}else {
				json.put("response", pr.getValue("mssg_boardNotExist")).put("status", 400);
 		    	System.out.println(pr.getValue("mssg_boardNotExist"));
			}		
		
		}catch (Exception e) {
			System.out.println("error");
			System.out.println(e. getMessage());
			e.printStackTrace();
		}
		
		out.print(json.toString());
		
		}
		
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();
		
		System.out.println("ESTOY AQUI");
		
		try {
			Integer board_id = reqBody.getInt("board_id");
			String newName = reqBody.getString("new_board_name");
			User user = (User) session.getAttribute("user");
			
			System.out.println(board_id+newName);
			
			if (db.getBoard(user.getId(), newName)==0) {
				db.updateBoard(board_id, newName);
				json.put("status", 200).put("response", pr.getValue("mssg_boardUpdate"));
				System.out.println(pr.getValue("mssg_boardUpdate"));	
			}else {
				json.put("response", pr.getValue("mssg_boardUsed")).put("status", 400);
 		    	System.out.println(pr.getValue("mssg_boardUsed"));
			}
		
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

