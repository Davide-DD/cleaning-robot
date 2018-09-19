var express = require('express');
var passport = require("passport");
var router = express.Router();

router.get("/in", function(req, res) {
	res.render("login");
});

router.post("/in", passport.authenticate("login", {
  successRedirect: "/",
  failureRedirect: "/log/in",
  failureFlash: true
}));

router.get("/out", function(req, res) {
  req.logout();
  res.redirect("/");
});

module.exports = router;