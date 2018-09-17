const http = require('http')
const yargs = require('yargs');
const request = require('request');
const server = require('./server');
const geocode = require('./geocode/geocode');
const weather = require('./weather/weather');
const { DateTime } = require('luxon');

var lat, lon;
var timeZoneId;

geocode.geocodeAddress(server.city, (errorMessage, results) => {
	if (errorMessage) {
		console.log(errorMessage);
	} else {
		lat = results.latitude;
		lon = results.longitude;
		server.completeInit();
	}
});

const updateTemperature = (callback) => {
  weather.getWeather(lat, lon, (errorMessage, weatherResults) => {
    if (!errorMessage) {
    	timeZoneId = weatherResults.timezone;
    	callback(undefined, Math.floor(weatherResults.temperature));
    }
    else {
    	callback(errorMessage, undefined);
    }
  })
};

const updateTime = (callback) => {
	callback(DateTime.local().setZone(timeZoneId).hour);
}


module.exports.updateTemperature = updateTemperature
module.exports.updateTime = updateTime