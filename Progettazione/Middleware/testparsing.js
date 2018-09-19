const net = require('net');
const colors = require('colors');
const commandLineArgs = require('command-line-args')

qaanswer = "robotCmd(buslog,data(secondPart, 300))";
qaanswer = qaanswer.replace(/\s+/g, '');

dataTemp = qaanswer.substring(qaanswer.indexOf(",")+ 1);
console.log("dataTemp: " + dataTemp);
temp = dataTemp.substring(dataTemp.indexOf(","));
console.log("temp: " + temp);
timeReceived = temp.substring(1, temp.indexOf(')'));
console.log("timeReceived: " + timeReceived);
direction = dataTemp.split(",")[0].substring(5);
console.log("direction: " + direction);

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

async function demo() {
  console.log('Taking a break...');
  await sleep(10000);
  console.log('Two seconds later');
}

demo();