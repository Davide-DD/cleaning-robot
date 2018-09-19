var express = require('express');
var myQA = require('../my_modules/my-QA');
var setupPassport = require("../my_modules/utils/setup-passport");
var router = express.Router();

router.get("/", function(req, res, next) {
	if (req.user !== undefined) res.render("index", { mode: req.app.get('mode') });
	else res.render("login");
});

router.get('/commands/:command', setupPassport.ensureAuthenticated, function (req, res, next) {
	myQA.sendEvent(req.params.command);
	res.sendStatus(200);
});

module.exports = router;