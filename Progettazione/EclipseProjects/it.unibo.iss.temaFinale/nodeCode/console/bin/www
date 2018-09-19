#!/usr/bin/env node

/**
 * Module dependencies.
 */

var app = require('../app');
var debug = require('debug')('myapp:server');
var http = require('http');

var yargs = require('yargs');
var myQA = require('../my_modules/my-QA');
var myIO = require('../my_modules/my-IO');

var argv = yargs
  .options({
    mode: {
      demand: true,
      alias: 'mode',
      describe: 'Mode of operation: debug or production',
      string: true
    },
    qaAddr: {
      demand: true,
      alias: 'qaAddress',
      describe: 'QActor address',
      string: true
    },
    qaPo: {
      demand: true,
      alias: 'qaPort',
      describe: 'QActor port',
      string: true
    },
    mqttPr: {
      demand: false,
      alias: 'mqttProto',
      describe: 'Mosquitto broker protocol',
      string: true,
      default: 'tcp'
    },
    mqttAddr: {
      demand: false,
      alias: 'mqttAddress',
      describe: 'Mosquitto broker address',
      string: true,
      default: 'localhost'
    },
    mqttPo: {
      demand: false,
      alias: 'mqttPort',
      describe: 'Mosquitto broker port',
      string: true,
      default: '1883'
    }
  })
  .help().alias('help', 'h')
  .argv;


/**
 * Get port from environment and store in Express.
 */

var port = normalizePort(process.env.PORT || '3000');
app.set('port', port);


/**
 * Create HTTP server.
 */

var server = http.createServer(app);


/**
 * Bind with socket.io.
 */

myIO.bind(server);


/**
 * Bind with QActor to manage commands and system statuses.
 */

myQA.init(argv.qaAddress, argv.qaPort);


/**
 * Connect to mqtt broker if server runs in debug mode
 */

app.set('mode', argv.mode);
if (argv.mode === 'debug') {
  var myMqtt = require('../my_modules/my-mqtt');
  myMqtt.connect(argv.mqttProto, argv.mqttAddr, argv.mqttPort);
}


/**
 * Listen on provided port, on all network interfaces.
 */

server.listen(port);
server.on('error', onError);
server.on('listening', onListening);


/**
 * Normalize a port into a number, string, or false.
 */

function normalizePort(val) {
  var port = parseInt(val, 10);

  if (isNaN(port)) {
    // named pipe
    return val;
  }

  if (port >= 0) {
    // port number
    return port;
  }

  return false;
}

/**
 * Event listener for HTTP server "error" event.
 */

function onError(error) {
  if (error.syscall !== 'listen') {
    throw error;
  }

  var bind = typeof port === 'string'
    ? 'Pipe ' + port
    : 'Port ' + port;

  // handle specific listen errors with friendly messages
  switch (error.code) {
    case 'EACCES':
      console.error(bind + ' requires elevated privileges');
      process.exit(1);
      break;
    case 'EADDRINUSE':
      console.error(bind + ' is already in use');
      process.exit(1);
      break;
    default:
      throw error;
  }
}

/**
 * Event listener for HTTP server "listening" event.
 */

function onListening() {
  var addr = server.address();
  var bind = typeof addr === 'string'
    ? 'pipe ' + addr
    : 'port ' + addr.port;
  debug('Listening on ' + bind);
}