var express = require('express');
var passport = require("passport");
var User     = require("../models/user");
var router = express.Router();


router.get("", function(req, res) {
  res.render("signup");
});

router.post("", function(req, res, next) {
  var firstName = req.body.firstName;
  var lastName = req.body.lastName;
  var email = req.body.email;
  var password = req.body.password;

  if (email !== '' && password !== '') {

  User.findOne({ email: email }, function(err, user) {

    if (err) { return next(err); }
    if (user) {
      req.flash("error", "User already exists");
      return res.redirect("/log/in");
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
  successRedirect: "/",
  failureRedirect: "/signup",
  failureFlash: true
}));


module.exports = router;