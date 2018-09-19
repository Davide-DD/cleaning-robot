var express = require('express');
var myQA = require('../my_modules/my-QA');
var setupPassport = require("../my_modules/utils/setup-passport");
var router = express.Router();

router.get('/map', setupPassport.ensureAuthenticated, function(req, res, next) {
	myQA.sendEvent("deleteMapCmd", "deleteMap");
	res.sendStatus(200);
});


module.exports = router;