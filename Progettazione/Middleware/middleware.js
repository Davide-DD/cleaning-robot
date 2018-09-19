const net = require('net');
const colors = require('colors');
const commandLineArgs = require('command-line-args')

var PORT_SOFFRITTI = 8999;
var ADDRESS_SOFFRITTI = '127.0.0.1';

var PORT_QACTOR = 8019;
var ADDRESS_QACTOR = '127.0.0.1';

var msgNum = 0;
var ignoreCollisions = false;
var command;
var onlyW = false;
var secondPart;

//Timeouts
var timeout;
var obsTimeout;

//Parametri di tempo (in ms)
var initialDate;
var finalDate;
var timeReceived;
var timeBeforeObstacle = 0;
var savedTimeBeforeObstacle;

//Parametri di tempo cambiabili
var timeToWait = 700; //tempo di attesa di un possibile collision event. Se scade questo tempo, viene inviato comunque un clear
var mobileObstacleTime = 600; //finestra di tempo in cui viene verificato se arrivano altri eventi di collision. Scaduta, viene inviato un evento di obstacle (fisso)
var timeToStop = 3000; //tempo di stop del robot in caso di avvenuta collisione con ostacolo mobile

//Args parser
const optionDefinitions = [
	{ name: 'log', alias: 'l', type: Boolean },
	{ name: 'ps', type: Number },
	{ name: 'as', type: String, multiple: false },
	{ name: 'pq', type: Number },
	{ name: 'aq', type: String, multiple: false }
]
const options = commandLineArgs(optionDefinitions)
if (options.ps)
	PORT_SOFFRITTI = options.ps
if (options.as)
	ADDRESS_SOFFRITTI = options.as
if (options.pq)
	PORT_QACTOR = options.pq
if (options.aq)
	ADDRESS_QACTOR = options.aq
var log = options.log

//Funzioni di timeout
function sendClear() {
	var msg = "msg(robotAnswerMid,event,qamiddleware_ctrl,none,robotAnswerMid(clear)," + msgNum + ")";
	if (toQActorSocket !== null) {
		if (log) console.log('Sending to QA node: '.green + msg);
		toQActorSocket.write(msg + "\n");
		msgNum += 1;
	}
}

function sendObstacle() {
	if (secondPart)
		sendClear();
	else {
		var msg = "msg(robotAnswerMid,event,qamiddleware_ctrl,none,robotAnswerMid(obstacle)," + msgNum + ")";
		if (toQActorSocket !== null) {
			if (log) console.log('Sending to QA node: '.green + msg);
			toQActorSocket.write(msg + "\n");
			msgNum += 1;
		}
	}
}

function stopIgnoringCollisions() {
	ignoreCollisions = false;
	if (onlyW) {
		timeLeft = timeReceived - savedTimeBeforeObstacle;
		if (timeLeft < 0 || timeLeft > timeReceived)
			timeLeft = 0;
		if (log) console.log("Robot moves forward for " + timeLeft + "ms.. (" + timeReceived + "-" + savedTimeBeforeObstacle + ")")
		command = JSON.stringify({ "type": "moveForward", "arg": timeLeft });
		toSoffrittiSocket.write(command);
	}
	sendClear();
}

// ------------- SOCKET TRA MIDDLEWARE E QA NODE ----------------
var toQActorSocket = new net.Socket();
toQActorSocket.connect(PORT_QACTOR, ADDRESS_QACTOR, function () {
	if (log) console.log(('Connected to QA node (' + ADDRESS_QACTOR + ':' + PORT_QACTOR + ')').bold);
});

// ------------- WAIT FOR MIDDLEWARE READY ------------- 

var msg = "msg(robotAnswerMid,event,qamiddleware_ctrl,none,robotAnswerMid(ready),"+msgNum+")";
if (log) console.log('Attempting to send a ready message to QActor:'.green + msg );

toQActorSocket.write(msg + "\n");
msgNum += 1;
if (log) console.log('Message sent.'.green);


toQActorSocket.on('error', function (err) {
	if (log) console.log("Error: " + err.message);
})

var qaanswer = "";
var dataTemp = "";

toQActorSocket.on('data', function (data) {
	//ESEMPIO:
	//Formato di arrivo dal QActor: robotCmd(buslog, data(w, 300))
	//Formato che vuole Soffritti: { "type": "moveForward", "arg": 300 }
	qaanswer = qaanswer + data;
	if (qaanswer === "") return;
	if (data.includes("\n")) {
		if (log) console.log('\nReceived from QA node: '.green + ("" + qaanswer).trim());
		
		// Remove spaces inside qaanswer
		qaanswer = qaanswer.replace(/\s+/g, '');
		dataTemp = qaanswer.substring(qaanswer.indexOf(",")+ 1);
		console.log("dataTemp: " + dataTemp);
		temp = dataTemp.substring(dataTemp.indexOf(","));
		console.log("temp: " + temp);
		timeReceived = temp.substring(1, temp.indexOf(')'));
		console.log("timeReceived: " + timeReceived);
		direction = dataTemp.split(",")[0].substring(5);
		console.log("direction: " + direction);
		qaanswer = ""; // reset answer
		directionForSoffritti = "";
		switch (direction) {
			case "w":
				directionForSoffritti = "moveForward";
				onlyW = true;
				break;
			case "s":
				timeReceived = timeBeforeObstacle;
				onlyW = false;
				directionForSoffritti = "moveBackward";
				break;
			case "a":
				onlyW = false;
				directionForSoffritti = "turnLeft";
				break;
			case "d":
				onlyW = false;
				directionForSoffritti = "turnRight";
				break;
			case "secondPart":
				if (log) console.log("\nSECOND PART STARTED (ASTAR)!\n".bold)
				secondPart = true;
				//sendClear();
				setTimeout(sendClear, timeToWait);
				return;
			case "finished":
				toSoffrittiSocket.pause();
				return;
		}
		command = JSON.stringify({ "type": directionForSoffritti, "arg": timeReceived });
		if (log) console.log('Sending to Soffritti node: '.cyan + command);
		toSoffrittiSocket.write(command);
		initialDate = new Date();
		if (log) console.log('Waiting ' + timeToWait + 'ms to see if a collision message arrives from Soffritti node...');
		//Se dopo aver inviato il comando, non ricevo alcun messaggio di "collision", 
		//significa che il path è libero e quindi devo mandare "clear" al QA node
		collisionReceived = 0;
		timeout = setTimeout(sendClear, timeToWait);
		
	}
	

});

toQActorSocket.on('close', function () {
	if (log) console.log('Connection to QA node closed'.bold);
});

// ---------- SOCKET TRA MIDDLEWARE E SOFFRITTI NODE -------------
var toSoffrittiSocket = new net.Socket();
toSoffrittiSocket.connect(PORT_SOFFRITTI, ADDRESS_SOFFRITTI, function () {
	if (log) console.log(('Connected to Soffritti node (' + ADDRESS_SOFFRITTI + ':' + PORT_SOFFRITTI + ')').bold);
});

toSoffrittiSocket.on('error', function (err) {
	if (log) console.log("Error: " + err.message);
})


toSoffrittiSocket.on('data', function (data) {
	var jsonData = JSON.parse(("" + data).substring(1, data.length - 2));
	if (jsonData.type === "collision" && !ignoreCollisions) {
		finalDate = new Date();
		timeBeforeObstacle = finalDate - initialDate
		initialDate = new Date();
		if (log) console.log('Received from Soffritti node: '.cyan + ("" + data).trim());
		if (log) console.log('Passed ' + timeBeforeObstacle + 'ms before receiving a collision..')
		clearTimeout(timeout);

		if (collisionReceived == 0) {
			savedTimeBeforeObstacle = timeBeforeObstacle;
			obsTimeout = setTimeout(sendObstacle, mobileObstacleTime); //Se il timeout termina, viene inviato l'evento obstacle (ostacolo fisso) 
			//(NOTA: se siamo nella seconda parte (astar) ce ne freghiamo degli ostacoli fissi eventualmente trovati poichè non devono essere rilevati per niente! 
			//Infatto, eventuali ostacoli fissi rilevati sono dovuti ad errori di approssimazione dei vari timer che portano il robot a sbattere contro le pareti della stanza (che sono ostacoli fissi).
			collisionReceived++;
		}
		else {
			clearTimeout(obsTimeout); //..altrimenti si tratta di un ostacolo mobile (più eventi di collision ricevuto negli ultimi mobileObstacleTime ms)
			if (log) console.log('Moving obstacle found!');
			setTimeout(stopIgnoringCollisions, timeToStop);
			ignoreCollisions = true;
		}

	}
});

toSoffrittiSocket.on('close', function () {
	if (log) console.log('Connection to Soffritti node closed'.bold);
});

if (log) {
	console.log("Middleware started!\n".bold)
	console.log('+--------------------+     +------------------+     +--------------------+')
	console.log('|                    |     |                  |     |                    |')
	console.log('|      ' + 'QA  Node'.green + '      <----->    Middleware    <----->  ' + ' Soffritti Node '.cyan + '  |')
	console.log('|                    |     |                  |     |                    |')
	console.log('+--------------------+     +------------------+     +--------------------+\n')
}





