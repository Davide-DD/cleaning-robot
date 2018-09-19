var net = require('net');

//Connect to the QActor application
module.exports.connectToQaNode = (host, qaport) => {
	try {
		var socketToQaCtx = net.connect({ port: qaport, host: host });
		socketToQaCtx.setEncoding('utf8');
		console.log('connected to qa node:' + host + ":" + qaport);

		// Send a ready event to the QActor application, unless it won't work if you want to communicate first
		emitQaEvent(socketToQaCtx, 0, 'ready');

		return socketToQaCtx;

	} catch (e) {
		console.log(" ------- connectToQaNode ERROR " + e + " socketToQaCtx=" + socketToQaCtx);
	}
};

/*
 * emit an event for the QActor application
 */
var emitQaEvent = (qa, msgNum, payload) => {
	try {
		var msg = "msg(consoleCmd,event,qaconsoleproban_ctrl,none,consoleCmd(" + payload + ")," + msgNum + ")";
		if (qa !== null) {
			console.log('emitQaEvent mmsg=' + msg);
			qa.write(msg + "\n");
		}
	} catch (e) {
		console.log("WARNING: " + e);
	}
};

module.exports.emitQaEvent = emitQaEvent;