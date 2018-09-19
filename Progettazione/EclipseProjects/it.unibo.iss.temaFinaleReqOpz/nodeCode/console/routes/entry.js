var express = require('express');
var router = express.Router();

router.use(function(req, res, next) {
  res.locals.currentUser = req.user;
  res.locals.errors      = req.flash("error");
  res.locals.infos       = req.flash("info");
  next();
});

router.use('/log', require('./access'));
router.use('/signup', require('./signup'));

router.use('/', require('./index'));
router.use('/simulator', require('./simulator'));

module.exports = router;