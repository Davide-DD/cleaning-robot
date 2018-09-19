var qaComm = require('./utils/qa-comm');
var myIO = require('./my-IO');

var msgNum = 1;
var qa;

module.exports.init = (host, qaport) => {
    qa = qaComm.connectToQaNode(host, qaport);
    
    var qaanswer = "";
    qa.on('data', function (data) {
        qaanswer = qaanswer + data;
        if (qaanswer === "") return;
        if (data.includes("\n")) {
        	console.log('receivedQaEvent mmsg=' + qaanswer);
            var temp = qaanswer.substring(qaanswer.indexOf(",") + 1);
            temp = temp.substring(0, temp.indexOf(')'));
            if (temp.indexOf(',') !== -1) temp = temp.substring(1, temp.lastIndexOf('\''));
            myIO.emit('commands and statuses', temp);
            qaanswer = "";
        }
    });
};

module.exports.sendEvent = (payload) => {
    qaComm.emitQaEvent(qa, msgNum, payload);
};