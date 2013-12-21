
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Server
 */
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Server() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext sctx = getServletContext();

		String userMsg =  request.getParameter("usermsg");
		String userId =  request.getParameter("userid");
		String filename = "/WEB-INF/chatLog.txt";
		
		if (userMsg != null) {
		    System.out.println("Chat-Nachricht: " + userMsg);
		    
		    Date zeitstempel = new Date();
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		     
		    String sctxPath = sctx.getRealPath("/");
		    File logfile = new File(sctxPath, filename);
		    FileWriter out;
		    if (!(logfile.exists())) {
		    	out = new FileWriter(logfile);
		    	out.write("neues file" + System.lineSeparator());
		    	System.out.println(filename + " in " + sctxPath + " erstellt");
		    } else {
		    	out = new FileWriter(logfile, true);
		    }
		    out.write(simpleDateFormat.format(zeitstempel) + "|" + userId + "|" + userMsg + System.lineSeparator());
		    out.close();
		}
		
		InputStream is = sctx.getResourceAsStream(filename);
        if (is != null) {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            PrintWriter writer = response.getWriter();
            String text = "";
            
            // Datei lesen und in den Response schreiben:
            while ((text = reader.readLine()) != null) {
                writer.println("<div id='chatboxmsg'>" + text + "</div>");
            }
            reader.close();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
