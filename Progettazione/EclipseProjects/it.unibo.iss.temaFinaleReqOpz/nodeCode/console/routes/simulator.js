var express = require('express');
var setupPassport = require("../my_modules/utils/setup-passport");
var router = express.Router();

router.get('', setupPassport.ensureAuthenticated, function(req, res, next) {
  res.render('simulator');
});

router.use('/change', require('./change'));
router.use('/delete', require('./delete'));

module.exports = router;