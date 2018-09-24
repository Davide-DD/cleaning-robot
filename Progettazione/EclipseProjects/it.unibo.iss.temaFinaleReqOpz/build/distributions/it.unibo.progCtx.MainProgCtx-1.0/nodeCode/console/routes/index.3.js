var express = require('express');
var app = require('../app');
var passport = require("passport");
var User     = require("../models/user");
var setupPassport = require("../my_modules/utils/setup-passport");
var router = express.Router();


router.use(function(req, res, next) {
  res.locals.currentUser = req.user;
  res.locals.errors      = req.flash("error");
  res.locals.infos       = req.flash("info");
  next();
});

router.get("/", function(req, res, next) {
	if (req.user !== undefined) res.render("index");
  else res.render("login");
});

router.get("/login", function(req, res) {
	res.render("login");
});

router.post("/login", passport.authenticate("login", {
  successRedirect: "/index",
  failureRedirect: "/login",
  failureFlash: true
}));

router.get("/index", setupPassport.ensureAuthenticated, function(req, res, next) {
	//console.log("auth routes access ");
	res.render("index");
});

router.get("/logout", function(req, res) {
  req.logout();
  res.redirect("/");
});

router.get("/signup", function(req, res) {
  res.render("signup");
});

router.post("/signup", function(req, res, next) {
  var firstName = req.body.firstName;
  var lastName = req.body.lastName;
  var email = req.body.email;
  var password = req.body.password;

  if (email !== '' && password !== '') {

  User.findOne({ email: email }, function(err, user) {

    if (err) { return next(err); }
    if (user) {
      req.flash("error", "User already exists");
      return res.redirect("/login");
    }

    var newUser = new User({
      firstName: firstName, 
      lastName: lastName, 
      email: email, 
      password: password
    });
    newUser.save(next);

  });
  } else {
    req.flash("error", "E-mail and password cannot be empty");
    return res.redirect('/signup');
  }
}, passport.authenticate("login", {
  successRedirect: "/index",
  failureRedirect: "/signup",
  failureFlash: true
}));

router.get('/commands/:command', setupPassport.ensureAuthenticated, function (req, res, next) {
	app.get('qaComm').emitQaEvent(req.params.command);
	res.sendStatus(200);
});

router.use('/simulator', require('./simulator'));

module.exports = router;