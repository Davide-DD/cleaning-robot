var mqtt = require('mqtt')
var client  = mqtt.connect('tcp://localhost:1883')
 
client.on('connect', function () {
  client.subscribe(['temperature', 'hour'])
})
 
client.on('message', function (topic, message) {
  console.log(topic.toString() + ': ' + message.toString())
})