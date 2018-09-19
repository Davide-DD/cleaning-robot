var mqtt = require('mqtt');

var publisher;

module.exports.connect = (proto, address, port) => {
    publisher = mqtt.connect(proto + '://' + address + ':' + port);
};

module.exports.publish = (topic, message, options) => {
    publisher.publish(topic, message, options);
};