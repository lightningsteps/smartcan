/*!
 * express
 * Copyright(c) 2009-2013 TJ Holowaychuk
 * Copyright(c) 2013 Roman Shtylman
 * Copyright(c) 2014-2015 Douglas Christopher Wilson
 * MIT Licensed
 */

var express = require('./lib/express');
var app = express();

var cans = [
  {
    "capacity":67,
    "type":"GLASS",
    "latitude":42.659194,
    "longitude":18.060173,
    "address": "Neka adresa 2"    
    },
    {
    "capacity":37,
    "type":"PAPER",
    "latitude":42.661777,
    "longitude":18.059987 ,
    "address": "Neka adresa 2"
    }
];

app.listen(process.env.PORT || 8080);

app.get('/allCans', function(req, res) {
  res.json(cans);
});