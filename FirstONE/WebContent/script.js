/***/ 
// Mit dem XMLHttpRequest-Objekt wird eine Anfrage an den Web-Server erstellt
var xmlhttp = new XMLHttpRequest();

// function zur Kommunikation mit dem Web-Server:
// ein neuer Chat-Eintrag kann (muss aber nicht) an den Server gesendet werden
// sämtliche Chat-Einträge werden vom Server zurückgemeldet
function sndRcvHTTP (userMsg, userId) {
	xmlhttp.onreadystatechange = function() { // function für Verarbeitung der Server-Rückmeldung
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {	
			$("#chatbox").html(xmlhttp.responseText); // Serverantwort in die ChatBox schreiben
			$("#chatbox")[0].scrollTop = $("#chatbox")[0].scrollHeight; // ans Ende scrollen
		};
	};
	var sndUrl = "http://localhost:8080/FirstONE/Server";  
	// Server-Servlet kann auch ohne usermsg aufgerufen werden: 
	if (userMsg != undefined) {
		sndUrl+="?usermsg="+ userMsg + "&userid=" + userId;
	};
	xmlhttp.open("GET",sndUrl,true); 	// GET-Anfrage wird asynchron gesendet
	xmlhttp.send(null);					// bei GET werden Daten mit URL übergeben 
}

// function zum Prüfen der Eingabe-Daten und Aufruf der Kommunikation mit dem Web-Server
function sndMsgToServer () {
	var userId = $("#userid")[0];
	if (userId.value == "") { alert("User-Eingabe ist leer");} 
	var userMsg = $("#usermsg")[0];
	if (userMsg.value == "") { alert("Message-Eingabe ist leer");} 
	else {	
		sndRcvHTTP(userMsg.value, userId.value);
		userMsg.value="";	// Zurücksetzen des Eingabefeldes
	}
}

// function schickt Betätigung der  Eingabe-Taste Chat-Daten an den Web-Server
function chkEnter(e) {
	if (e.keyCode == 13) {
		sndMsgToServer ();
	}
}

// Werbe-Banner ausblenden
$("#banner").click(function() {$( this ).fadeOut();});