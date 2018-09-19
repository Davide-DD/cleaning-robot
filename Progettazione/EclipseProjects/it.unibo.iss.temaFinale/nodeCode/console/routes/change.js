var express = require('express');
var myMqtt = require('../my_modules/my-mqtt');
var setupPassport = require("../my_modules/utils/setup-passport");
var router = express.Router();

router.post('/temperature', setupPassport.ensureAuthenticated, function(req, res, next) {
	myMqtt.publish('temperature', 'msg(tempEvent,event,qacityinformationproban_ctrl,none,tempEvent(' + req.body.message + '),' + 1 + ')', { retain: true });
	res.sendStatus(200);
});

router.post('/hour', setupPassport.ensureAuthenticated, function(req, res, next) {
	myMqtt.publish('hour',  'msg(timeEvent,event,qacityinformationproban_ctrl,none,timeEvent(' + req.body.message + '),' + 1 + ')', { retain: true });
	res.sendStatus(200);
});

module.exports = router;