//QActorWebUI.js
var curSpeed = "low";
	
    console.log("QActorWebUI.js : server IP= "+document.location.host);
 /*
 * WEBSOCKET
 */
    var sock = new WebSocket("ws://"+document.location.host, "protocolOne");
    sock.onopen = function (event) {
         //console.log("QActorWebUI.js : I am connected to server.....");
         document.getElementById("connection").value = 'CONNECTED';
    };
    sock.onmessage = function (event) {
        //console.log("QActorWebUI.js : "+event.data);
        //alert( "onmessage " + event);
        document.getElementById("state").value = ""+event.data;
    }
    sock.onerror = function (error) {
        //console.log('WebSocket Error %0',  error);
        document.getElementById("state").value = ""+error;
    };
    
	function setSpeed(val){
		curSpeed = val;
		document.getElementById("speed").value = curSpeed;
	}
	function send(message) {
		document.getElementById("sending").value = ""+message;
		sock.send(message);
	};
/*	
	document.onkeydown = function(event) {
//	alert("event="+event);
		    event = event || window.event;
		    switch (event.keyCode || event.which) {
		        case 65:
		            send('a-High');
		            break;
		        case 68:
		            send('d-High');
		            break;
		        case 87:
		            send('w-High');
		            break;
		        case 83:
		            send('s-High');
		            break;
		        default:
		            //send('h-High');
		    }
	};
*/
 	//alert("loaded");
