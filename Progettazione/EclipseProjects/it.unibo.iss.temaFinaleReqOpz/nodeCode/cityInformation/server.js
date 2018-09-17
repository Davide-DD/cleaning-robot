// Requisiti
const http = require('http')
const yargs = require('yargs');
const request = require('request');
const mqtt = require('mqtt')


// Gestione degli argomenti
const argv = yargs
  .options({
    pr: {
      demand: false,
      alias: 'proto',
      describe: 'Mosquitto broker protocol',
      string: true
    },
    a: {
      demand: false,
      alias: 'address',
      describe: 'Mosquitto broker address',
      string: true
    },
    po: {
      demand: false,
      alias: 'port',
      describe: 'Mosquitto broker port',
      string: true
    },
    c: {
      demand: true,
      alias: 'city',
      describe: 'City to fetch weather for',
      string: true
    }
  })
  .help().alias('help', 'h')
  .argv;

argv.proto ? true : argv.proto = 'tcp';
argv.address ? true : argv.address = 'localhost';
argv.port ? true : argv.port = '1883'; // 1883 è la porta di default di Mosquitto

// Interazione pubsub
var publisher  = mqtt.connect(argv.proto + '://' + argv.address + ':' + argv.port);

// Inizializzazione attraverso init.js
var hour, temperature, msg = 0;

const temperatureLogic = (errorMessage, result) => {
  if(errorMessage) {
    console.log(errorMessage);
  }
  else {
    if(temperature !== result) {
      temperature = result;
      publisher.publish('temperature', 'msg(tempEvent,event,qacityinformationproban_ctrl,none,tempEvent(' + temperature + '),' + msg + ')', { retain: true });
      msg += 1;
    }
  }
}
const timeLogic = (result) => {
  if (result !== hour) {
    hour = result;
    publisher.publish('hour', 'msg(timeEvent,event,qacityinformationproban_ctrl,none,timeEvent(' + hour + '),' + msg + ')', { retain: true });
    msg += 1;
  }
}

module.exports.city = argv.c
module.exports.completeInit = () => {
  init.updateTemperature(temperatureLogic);
  init.updateTime(timeLogic);
};

const init = require('./init');


// Logica applicativa
setInterval(function() { init.updateTemperature(temperatureLogic) }, 1000*60*15);
setInterval(function() { init.updateTime(timeLogic) }, 1000*60);


