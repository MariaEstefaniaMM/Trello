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
 * Servlet implementation class Card
 */
@WebServlet(
		name="Card",
		urlPatterns="/Card")
public class Card extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Card() {
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
		
		System.out.println("ESTOY AQUI ahora"+id);
		json.put("cards", db.columnCards(id)).put("status", 200).put("response", pr.getValue("mssg_success"));
		
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

			Integer column_id = reqBody.getInt("column_id");
			String caName = reqBody.getString("card_name");
			String caDescrip = reqBody.getString("card_description");
			User user = (User) session.getAttribute("user");
			System.out.println(user.getId()+caDescrip+caName);
			
            Integer card_id = db.getCard(column_id, caName);
			
			if (card_id==0) {
				card_id = db.newCard(column_id, user.getId(), caName, caDescrip);
				json.put("status", 200).put("response", pr.getValue("mssg_cardCreated")).put("card_id", card_id);
				System.out.println(pr.getValue("mssg_cardCreated"));
			}else {
				json.put("response", pr.getValue("mssg_cardUsed")).put("status", 400);
 		    	System.out.println( pr.getValue("mssg_creationFail"));
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
			
			Integer card_id = reqBody.getInt("card_id");
            System.out.println(card_id);
			
			if (card_id!=0) {
				db.deleteCard(card_id);
				json.put("status", 200).put("response", pr.getValue("mssg_cardDeleted"));
				System.out.println(pr.getValue("mssg_cardDeleted"));
			}else {
				json.put("response", pr.getValue("mssg_cardNotExist")).put("status", 400);
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

			Integer new_column_id = reqBody.getInt("new_column_id");
			String newName = reqBody.getString("new_card_name");
			String newDescription = reqBody.getString("new_card_description");
            
			Integer card_id = reqBody.getInt("card_id");
            System.out.println(new_column_id+card_id);
			
			db.updateCard(newName, newDescription, new_column_id, card_id);
			json.put("status", 200).put("response", pr.getValue("mssg_cardUpdate"));
			System.out.println(pr.getValue("mssg_cardUpdate"));		
		
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
