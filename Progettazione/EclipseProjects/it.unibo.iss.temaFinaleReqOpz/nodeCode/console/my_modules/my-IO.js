var io;

var topics = {
    'commands and statuses' : 'disable_all'
};

module.exports.bind = (server) => {
    io = require('socket.io')(server);
    
    io.on('connection', function(socket) {
        for(var topic in topics) {
            socket.emit(topic, topics[topic]);
        }
    });
};

module.exports.emit = (topic, message) => {
    topics[topic] = message;
    io.emit(topic, message);
};