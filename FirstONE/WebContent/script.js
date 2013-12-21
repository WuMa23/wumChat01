/***/ 
var xmlhttp = new XMLHttpRequest(); // Creating a new XMLHttpRequest object

function sndRcvHTTP (userMsg, userId) {
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {	
			$("#chatbox").html(xmlhttp.responseText);
			$("#chatbox")[0].scrollTop = $("#chatbox")[0].scrollHeight;
		};
	};
	var sndUrl = "http://localhost:8080/FirstONE/Server";
	if (userMsg != undefined) {
		sndUrl+="?usermsg="+ userMsg + "&userid=" + userId;
	};
	xmlhttp.open("GET",sndUrl,true);
	xmlhttp.send(null);
}

function sndMsgToServer () {
	var userId = $("#userid")[0];
	if (userId.value == "") { alert("User-Eingabe ist leer");} 
	var userMsg = $("#usermsg")[0];
	if (userMsg.value == "") { alert("Message-Eingabe ist leer");} 
	else {	
		sndRcvHTTP(userMsg.value, userId.value);
		userMsg.value="";
	}
}

function chkEnter(e) {
	if (e.keyCode == 13) {
		sndMsgToServer ();
	}
}

/***/
$( "#banner" ).click(function() {$( this ).fadeOut();});