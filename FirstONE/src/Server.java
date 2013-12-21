

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
//		ArrayList<String> arl = new ArrayList<String>();
//		if (sctx.getAttribute("chatListe") != null){
//			arl = (ArrayList)sctx.getAttribute("chatListe");	
//		}
			
		String msg =  request.getParameter("message");
		String filename = "/WEB-INF/chatLog.txt";
		
		if (msg != null) {
		    System.out.println("Folgende Nachricht wurde am Server empfangen : " + msg);
		    
		    Date zeitstempel = new Date();
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		    // arl.add("[" + simpleDateFormat.format(zeitstempel) + "] " + msg);
		    // sctx.setAttribute("chatListe", arl);
		     
		    String sctxPath = sctx.getRealPath("/");
		    File logfile = new File(sctxPath, filename);
		    FileWriter out;
		    if (!(logfile.exists())) {
		    	out = new FileWriter(logfile);
		    	out.write("neues file" + System.lineSeparator());
		    } else {
		    	out = new FileWriter(logfile, true);
		    }
		    out.write("[" + simpleDateFormat.format(zeitstempel) + "] " + msg + System.lineSeparator());
		    out.close();
		}
		
		InputStream is = sctx.getResourceAsStream(filename);
        if (is != null) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            PrintWriter writer = response.getWriter();
            String text = "";
            
            // Datei lesen und in den Response schreiben:
            while ((text = reader.readLine()) != null) {
                writer.println("<div id='chatboxmsg'>" + text + "</div>");
            }
        }
/*		
  		PrintWriter out = response.getWriter();
  		Iterator<String> i1 = arl.iterator();
		while (i1.hasNext()) {
		    out.println("<div id='chatboxmsg'>" + i1.next() + "</div>");
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
