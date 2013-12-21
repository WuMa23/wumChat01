/***/ 
var xmlhttp = new XMLHttpRequest();
function wumExternalFunction () {
	var msg = $("#usermsg")[0]; //ohne jQuery: var msg = document.getElementById("usermsg");
	if (msg.value=="") { alert("Message-Eingabe ist leer");} 
	else {	
		//Creating a new XMLHttpRequest object
		xmlhttp.onreadystatechange = function() {
			if (this.readyState == 4 && xmlhttp.status == 200) {
				$("#chatbox").html(xmlhttp.responseText);
			};
		};
		xmlhttp.open("GET","http://localhost:8080/FirstONE/Server?message="+ msg.value,true);
		xmlhttp.send(null);
		msg.value="";
	}
}
/***/
$( "#banner" ).click(function() {$( this ).fadeOut();});