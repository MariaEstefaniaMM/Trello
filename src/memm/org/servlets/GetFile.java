package memm.org.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import memm.org.utilities.PropertiesReader;

/**
 * Servlet implementation class GetFile
 */
@WebServlet("/GetFile")
/*@WebServlet(name = "GetFile",
urlPatterns = "/GetFile")*/
public class GetFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static PropertiesReader prop = PropertiesReader.getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		response.setContentType("file");

        response.setHeader("Content-disposition","attachment; filename="+name);
        
        File my_file = new File(prop.getValue("baseDir") + "/" +name);
        System.out.println(name);
        
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
