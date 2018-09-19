var net = require('net');

const PORT = 1800;
const ADDRESS = '127.0.0.1';

var server = net.createServer(onClientConnected);

server.listen(PORT, ADDRESS);

function onClientConnected(socket) {

  let clientName = `${socket.remoteAddress}:${socket.remotePort}`;
  console.log(`${clientName} connected.`);

  socket.on('data', (data) => {

    let message = data.toString().replace(/[\n\r]*$/, '');
    console.log(`${clientName} said: ${message}`);
  });

  // Si attiva quando il client si disconnette
  socket.on('end', () => {
    console.log(`${clientName} disconnected.`);
  });

  //TEST: posizionare il robot sulla prima riga della matrice della stanza, rivolto verso destra e a metà di tale riga.
  //Con il comando successivo mi aspetto clear come parametro di ritorno
  setTimeout(function () { socket.write("manageCommands(SENDER, data(w, 300)\n"); }, 10000);
  //Con il comando successivo invece mi aspetto obstacle come parametro di ritorno 
  //(perchè andando avanti due volte verso destra con arg>300 e partendo da metà stanza, il robot al secondo comando sbatte contro il muro a destra)
  setTimeout(function () { socket.write("manageCommands(SENDER, data(w, 300)\n"); }, 20000);
  //Con il comando successivo mi aspetto clear come parametro di ritorno (tornando indietro non c'è nessun ostacolo)
  setTimeout(function () { socket.write("manageCommands(SENDER, data(s, 300)\n"); }, 30000);

}

console.log(`Server started at: ${ADDRESS}:${PORT}. I am a shitty QA node!`);