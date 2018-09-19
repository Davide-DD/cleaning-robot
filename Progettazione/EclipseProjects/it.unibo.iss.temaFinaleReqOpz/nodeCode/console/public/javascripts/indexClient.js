// Variabili

var availableCommands = ["Start", "Stop"];


// Funzione di utilita'

function capitalize(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}


// Gestione dei pulsanti di start, stop, segnalazione dello stato degli attori e del warning in caso di temperatura e orario non adeguati

var robotCommandClick = (command) => {
	$.ajax({
		type: 'GET',
		url: 'commands/' + command
	});
};

function configStart(status) {
	if (status === 'enable') $('#robotStartCommand').removeClass('bg-secondary').addClass('bg-success').bind('click', function() { robotCommandClick('start') });
	else $('#robotStartCommand').removeClass('bg-success').addClass('bg-secondary').unbind('click');
}

function configStop(status) {
	if (status === 'enable') $('#robotStopCommand').removeClass('bg-secondary').addClass('bg-danger').bind('click', function() { robotCommandClick('stop') });
	else $('#robotStopCommand').removeClass('bg-danger').addClass('bg-secondary').unbind('click');
}

function showTemperatureAndTimeWarning(status) {	
	if (status === 'disable') $('#temperatureAndTimeStatus').text('La temperatura e/o l\'orario non permettono l\'avvio del robot');
	else $('#temperatureAndTimeStatus').text('');
}

function showActorsStatuses(status) {	
	if (status === 'enable') {
		$('#robotStatus').text('Pulizia in corso...');
		$('#ledStatus').text('Lampeggia...');
	}
	else { 
		$('#robotStatus').text('In attesa...');
		$('#ledStatus').text('Spento...');
	}
}

function manageCommands(statuses, commands) {
	if (commands[0] !== 'all') {
		for (var i = 0; i < commands.length; i++) {
			window['config' + capitalize(commands[i])](statuses[i]);
			if (commands[i] === 'stop') showActorsStatuses(statuses[i]);
		}
		showTemperatureAndTimeWarning('enable');
	}
	else {
		for (var i = 0; i < availableCommands.length; i++) {
			window['config' + availableCommands[i]](status);
		}
		showTemperatureAndTimeWarning(statuses[0]);
		showActorsStatuses(statuses[0]);
	}
}


// MAIN

var socket = io();
socket.on('commands and statuses', function(msg) {
	var result = msg.split(', ');
	var temp;
	var commands = [], statuses = [];
	for (var i = 0; i < result.length; i++) {
		temp = result[i].split('_');
		statuses.push(temp[0]);
		commands.push(temp[1]);
	}
	manageCommands(statuses, commands);
});