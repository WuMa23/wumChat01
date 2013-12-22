
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
		
	    Date zeitstempel = new Date();
		ServletContext sctx = getServletContext();

		String userMsg =  request.getParameter("usermsg");
		String userId =  request.getParameter("userid");
	    
	    // Ablage bzw. Name für ChatLog-File festlegen
	    SimpleDateFormat dateyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	    String filename = "/WEB-INF/chatLog_" + dateyyyyMMdd.format(zeitstempel) + ".txt";			

		// Servlet kann mit und ohne userMsg aufgerufen werden.
		// Wenn keine userMsg mitgegeben wird, dann erfolgt kein Eintrag im chatLog-File
		if (userMsg != null) {

			// userMSG-Ausgabe in Console
			System.out.println("Chat-Nachricht: " + userMsg); 
		    
		    String sctxPath = sctx.getRealPath("/");		// Dateipfad ServletContext ermitteln
		    File logfile = new File(sctxPath, filename);	// Datei instanzieren (für exist-Prüfuen)	
		    FileWriter out;
		    if (!(logfile.exists())) {						 
		    	out = new FileWriter(logfile);				// ChatLog-File neu erstellen
		    	// Datum in die erste Zeile schreiben
		    	SimpleDateFormat dateISO = new SimpleDateFormat("yyyy-MM-dd");
		    	out.write("[" + dateISO.format(zeitstempel) + "]" + System.lineSeparator()); 
		    	System.out.println(filename + " in " + sctxPath + " erstellt"); // Consol-Ausgabe
		    } else {
		    	out = new FileWriter(logfile, true);		// bestehendes ChatLog-File verwenden
		    }

		    // Zeit, User und Message in ChatLog-File schreiben
		    SimpleDateFormat timeHHmmss = new SimpleDateFormat("HH:mm:ss");
		    out.write(timeHHmmss.format(zeitstempel) + ";" + userId + ";" + userMsg + System.lineSeparator());
		    out.close(); // close ist für nachfolgendes Lesen wichtig
		}
		
		InputStream is = sctx.getResourceAsStream(filename);
        if (is != null) {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            PrintWriter writer = response.getWriter();
            String textline = "";
            
            // ChatLog-File lesen, Zeile in Felder aufsplitten und in den Response schreiben:
            while ((textline = reader.readLine()) != null) {
            	String[] textfeld = textline.split(";");
            	String sndText = "<div id='chatboxmsg'>";
            	if (textfeld.length > 0) {sndText += textfeld[0];} 
            	if (textfeld.length > 1) {sndText += " <i>" + textfeld[1] + "</i>";}
            	if (textfeld.length > 2) {sndText += " " + textfeld[2];}
            	sndText += "</div>";
                writer.println(sndText);
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
