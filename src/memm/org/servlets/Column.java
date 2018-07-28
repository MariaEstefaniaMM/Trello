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
 * Servlet implementation class Column
 */
@WebServlet(
		name="Column",
		urlPatterns="/Column")
public class Column extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Column() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();
		
		System.out.println("ESTOY AQUI g");
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		json.put("columns", db.boardColumns(id)).put("status", 200).put("response", pr.getValue("mssg_success"));
		
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
			Integer board_id = reqBody.getInt("board_id");			
			String cName = reqBody.getString("column_name");
			User user = (User) session.getAttribute("user");
			System.out.println(board_id+cName);

            Integer column_id = db.getColumn(board_id, cName);
			
			if (column_id==0) {
				column_id = db.newColumn(board_id, cName, user.getId());
				json.put("status", 200).put("response", pr.getValue("mssg_columnCreated")).put("column_id", column_id);
				System.out.println(pr.getValue("mssg_columnCreated"));
			}else {
				json.put("response", pr.getValue("mssg_columnUsed")).put("status", 400);
 		    	System.out.println(pr.getValue("mssg_creationFail"));
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

			Integer column_id = reqBody.getInt("column_id");
            System.out.println(column_id);
			
			if (column_id!=0) {
				db.deleteColumn(column_id);
				json.put("status", 200).put("response", pr.getValue("mssg_columnDeleted"));
				System.out.println(pr.getValue("mssg_columnDeleted"));
			}else {
				json.put("response", pr.getValue("mssg_columnNotExist")).put("status", 400);
 		    	System.out.println(pr.getValue("mssg_deleteFail"));
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
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();
		
		System.out.println("ESTOY AQUI");
		
		try {
			
			String newName = reqBody.getString("new_column_name");
			Integer column_id = reqBody.getInt("column_id");
			
			System.out.println(column_id+newName);
			
			db.updateColumn(column_id, newName);
			json.put("status", 200).put("response", pr.getValue("mssg_columnUpdate"));
			System.out.println(pr.getValue("mssg_columnUpdate"));		
		
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
