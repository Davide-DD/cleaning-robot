const net = require('net')

const SEPARATOR = ";"

const client = new Client({ip: readIpFromArguments(), port: readPortNumberFromArguments()})

// setTimeout(turnRight, 1000)
// setTimeout(move1, 2000);
// setTimeout(move1, 3000);
// setTimeout(move1, 4000);
// setTimeout(move1, 5000);
// setTimeout(move1, 6000);
// setTimeout(move1, 7000);
// setTimeout(move1, 8000);
// setTimeout(move1, 9000);
// setTimeout(move1, 10000);
// setTimeout(move1, 11000);
// setTimeout(turnRight, 4000)
// setTimeout(move2, 4600)
// setTimeout(turnRight, 5000)
// setTimeout(move1, 6000)
// setTimeout(turnLeft, 7500)
// setTimeout(move2, 8500)
// setTimeout(turnLeft, 9500)
// setTimeout(move1, 11000)
// setTimeout(turnRight, 12500)
// setTimeout(move2, 14000)
// setTimeout(turnRight, 15500)
// setTimeout(move1, 16500)
// setTimeout(turnLeft, 18000)
// setTimeout(move2, 19500)
// setTimeout(turnLeft, 21000)
// setTimeout(move1, 22500)

var counter = 0;
setTimeout(resetCounter, 1000);

function resetCounter() {
	counter = 0;
	setTimeout(resetCounter, 1000);
}

function move1(msg)
{
	client.send(`{ "type": "moveForward", "arg": 150 }`)
}

function move2(msg)
{
	client.send(`{ "type": "moveForward", "arg": 350 }`)
}

function turnRight(msg)
{
	client.send( `{ "type": "turnRight", "arg": 500 }` )
}

function turnLeft(msg)
{
	client.send( `{ "type": "turnLeft", "arg": 500 }` )
}

function Client({ port, ip }) {
    const self = this

    let clientSocket
    const outQueue = []

    connectTo(port, ip)
    
    function connectTo(port, ip) {
        const client = new net.Socket()
        clientSocket = client

        client.connect({ port, ip }, () => console.log(`\tConnecting...`) )

        client.on('connect', () => {
            console.log(`\tConnected`)
            flushOutQueue()
        })

        client.on('data', function (data) {
            var jsonData = JSON.parse(("" + data).substring(1, data.length - 2));
			if (jsonData.type === "collision" && counter == 0) {
				counter++;
				console.log(jsonData)
			}
			
        });
        
        client.on('close', () =>  console.log(`\tConnection closed`) )
        client.on('error', () => console.log(`\tConnection error`) )
    }

    this.send = function(message) {
        if(!clientSocket.connecting)
            clientSocket.write(SEPARATOR +message +SEPARATOR)
        else {
            console.log(`\tSocket not created, message added to queue`)
            outQueue.push(message)
        }
    }

    this.finish = function() {
        if(clientSocket.connecting)
            clientSocket.on('connect', clientSocket.end )
        else
            clientSocket.end()
    }

    function flushOutQueue() {
        while(outQueue.length !== 0) {
            const data = outQueue.shift()
            self.send(data)
        }
    }
}

function readIpFromArguments() {
    const ipAddress = String(process.argv[2])
    if(!ipAddress) {
        console.error("This script expects 2 arguments: server ip address and port number.")
        process.exit()
    }

    return ipAddress
}

function readPortNumberFromArguments() {
    const port = Number(process.argv[3])
    if(!port || port < 0 || port >= 65536) {
        console.error("This script expects a valid port number (>= 0 and < 65536) as argument.")
        process.exit()
    }

    return port
}