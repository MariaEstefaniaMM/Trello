package memm.org.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import memm.org.DataBase;
import memm.org.User;
import memm.org.utilities.PropertiesReader;

/**
 * Servlet implementation class Files
 */

@WebServlet(name = "Files",
            urlPatterns = "/Files")
@MultipartConfig

public class Files extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static PropertiesReader prop = PropertiesReader.getInstance();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Files() {
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
		json.put("files", db.cardFiles(id)).put("status", 200).put("response", pr.getValue("mssg_success"));
		
		out.print(json.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		Collection<Part> files = request.getParts();
		InputStream filecontent = null;
		OutputStream os = null;
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();

		ArrayList<Integer> files_id= new ArrayList<Integer>();
		ArrayList<String> files_name= new ArrayList<String>();
		
		try {
			
			Integer card_id = Integer.parseInt(request.getParameter("card_id"));
			System.out.println("card_id"+card_id);
			User user = (User) session.getAttribute("user");
       
            for (Part file : files) {
            	String file_name = getFileName(file);
            	Integer file_id = db.getFile(card_id, file_name);
            	String file_url = prop.getValue("baseDir") + "/" + file_name;
            	System.out.println(user.getId()+""+card_id+""+file_name+""+file_id+""+file_url);
            	
            	if (file_id==0) {
    			
				filecontent = file.getInputStream();
				file_id = db.newFile(card_id, user.getId(), file_url, file_name);
				System.out.println("Archivo-> " + file_name + "" + file_url);
				os = new FileOutputStream(file_url);
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = filecontent.read(bytes)) != -1) {
					os.write(bytes, 0, read);					
				}
				files_id.add(file_id);
				files_name.add(file_name);
				json.put("status", 200).put("response", pr.getValue("mssg_fileUploaded")).put("files_id", files_id).put("files_name", files_name).put("username", user.getUsername());
				System.out.println(pr.getValue("mssg_fileUploaded"));
            }else {
				json.put("response", pr.getValue("mssg_FileExist")).put("status", 400); 
 		    	System.out.println(pr.getValue("mssg_FileExist"));
			}
    		}        
			out.print(json.toString());
		} catch (Exception e) {
			System.out.println("error");
			System.out.println(e. getMessage());
			e.printStackTrace();
		} finally {
			if (filecontent != null) {
				filecontent.close();
			}
			if (os != null) {
				os.close();
			}
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		DataBase db = new DataBase();
		PropertiesReader pr = PropertiesReader.getInstance();
		
		try {
			
			Integer file_id = reqBody.getInt("file_id");
			String file_name = reqBody.getString("file_name");
			String file_url = prop.getValue("baseDir") + "/" + file_name;
			File file = new File(file_url);
            System.out.println(file_id+file_url);
            
			if (file_id!=0) {
				db.deleteFile(file_id);
				json.put("status", 200).put("response", pr.getValue("mssg_fileDeleted"));
				System.out.println(pr.getValue("mssg_fileDeleted"));
				file.delete();
			}else {
				json.put("response", pr.getValue("mssg_fileNotExist")).put("status", 400);
 		    	System.out.println(pr.getValue("mssg_fileNotExist")); 
			}		
		
		}catch (Exception e) {
			System.out.println("error");
			System.out.println(e. getMessage());
			e.printStackTrace();
		}
		
		out.print(json.toString());
		
		}
		
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
	
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");				
			}
			System.out.println("AQUI"+part.getHeader("content-disposition"));
		}
		return null;
	}

}
