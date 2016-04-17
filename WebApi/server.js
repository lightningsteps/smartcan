/*!
 * express
 * Copyright(c) 2009-2013 TJ Holowaychuk
 * Copyright(c) 2013 Roman Shtylman
 * Copyright(c) 2014-2015 Douglas Christopher Wilson
 * MIT Licensed
 */
var express = require('express')
  , cors = require('cors')
  , app = express();

var bodyParser = require('body-parser');

var cans = [
  { "id": 1,
    "capacity":67,
    "type":"Glass",
    "latitude":42.6585709,
    "longitude":18.05828,
    "address": "Ul. kardinala Stepinca"    
    },
    {
    "id": 2,
    "capacity":73,
    "type":"Paper",
    "latitude":42.6605388,
    "longitude":18.0594263,
    "address": "Ul. Ivana Dulčića"
    },
    {
    "id": 3,
    "capacity":37,
    "type":"Garbage",
    "latitude":42.642355,
    "longitude":18.107158,
    "address": "Ul. Iza Grada"
    },
    {
    "id": 4,
    "capacity":59,
    "type":"Plastic",
    "latitude":42.6416834,
    "longitude":18.1025632,
    "address": "Ul. don Frana Bulića"
    },
    {
    "id": 5,
    "capacity":73,
    "type":"Glass",
    "latitude":42.658345,
    "longitude":18.071196,
    "address": "Kliševska Ul."
    },
    {
    "id": 6,
    "capacity":67,
    "type":"Paper",
    "latitude":42.652999,
    "longitude":18.086384,
    "address": "Ul. Nikole Tesle"
    },
    {
    "id": 7,
    "capacity":23,
    "type":"Garbage",
    "latitude":42.654492,
    "longitude":18.073998,
    "address": "Ul. Petra Zoranića"
    },
    {
    "id": 8,
    "capacity":14,
    "type":"Plastic",
    "latitude":42.642422,
    "longitude":18.118607,
    "address": "Ul. Bruna Bušića"
    },
    {
    "id": 9,
    "capacity":23,
    "type":"Glass",
    "latitude":42.643124,
    "longitude":18.114235,
    "address": "Ul. kralja Petra Krešimira"
    },
    {
    "id": 10,
    "capacity":5,
    "type":"Paper",
    "latitude":42.642355,
    "longitude":18.107158,
    "address": "Ul. Iza Grada"
    },
    {
    "id": 11,
    "capacity":96,
    "type":"Garbage",
    "latitude":42.643163,
    "longitude":18.109166,
    "address": "Zagrebačka ul."
    },
    {
    "id": 12,
    "capacity":47,
    "type":"Plastic",
    "latitude":42.645760,
    "longitude":18.101422,
    "address": "Volantina ul."
    },
    {
    "id": 13,
    "capacity":21,
    "type":"Glass",
    "latitude":42.648873,
    "longitude":18.089448,
    "address": "Ul. Alberta Hallera"
    },
    {
    "id": 14,
    "capacity":15,
    "type":"Paper",
    "latitude":42.649643,
    "longitude":18.088059,
    "address": "Ul. od Montovjerne"
    },
    {
    "id": 15,
    "capacity":85,
    "type":"Garbage",
    "latitude":42.651623,
    "longitude":18.082478,
    "address": "Ul. Iva Vojnovića"
    },
    {
    "id": 16,
    "capacity":36,
    "type":"Plastic",
    "latitude":42.653872,
    "longitude":18.080949,
    "address": "Ul. od Batale"
    },
    {
    "id": 17,
    "capacity":72,
    "type":"Glass",
    "latitude":42.655537,
    "longitude":18.077017,
    "address": "Ul. kralja Tomislava"
    },
    {
    "id": 18,
    "capacity":49,
    "type":"Paper",
    "latitude":42.655549,
    "longitude":18.073968,
    "address": "Ul. Mata Vodopića"
    },
    {
    "id": 19,
    "capacity":81,
    "type":"Garbage",
    "latitude":42.657367,
    "longitude":18.070967,
    "address": "Ul. Petra Svačića"
    },
    {
    "id": 20,
    "capacity":4,
    "type":"Plastic",
    "latitude":42.638284,
    "longitude":18.128721,
    "address": "Ul. Frana Supila"
    },
    {
    "id": 21,
    "capacity":53,
    "type":"Glass",
    "latitude":42.653315,
    "longitude":18.093052,
    "address": "Ul. Andrije Hebranga"
    }
];

var server_port = process.env.OPENSHIFT_NODEJS_PORT || 8080
var server_ip_address = process.env.OPENSHIFT_NODEJS_IP || '127.0.0.1'

app.use(bodyParser.json())

app.use(cors());

app.listen(server_port, server_ip_address, function () {
console.log( "Listening on " + server_ip_address )
});

app.get('/cans', function(req, res) {
  res.json(cans);
  console.log('array length' + cans.length);
});

app.get('/cans/:id', function(req, res) {
    
    console.log('params' + req.params.id);
    
    for(var i=0; i < cans.length; i++) {
        
        if (cans[i].id == req.params.id){
            console.log('item: ' + cans[i]);
            res.json(cans[i]);
        }
        
        console.log('itemid: ' + cans[i].id);
    }
});

app.delete('/cans/:id', function(req, res) {
    
    console.log('params' + req.params.id);
    
    for(var i=0; i < cans.length; i++) {
        
        if (cans[i].id == req.params.id){
            console.log('removing item: ' + cans[i]);
            var obj = cans[i];
            cans.splice(i, 1);
            res.json(obj);
        }
    }
});

app.get('/updateData/:cap', function(req, res){
    console.log('Before:' + cans[0].capacity);
    cans[0].capacity = req.params.cap;
    console.log('After:' + cans[0].capacity);
    res.json(cans);
});

app.put('/addCan', function(req, res){
    console.log(req.body);
    var highestID = Math.max.apply(Math,cans.map(function(o){return o.id;}));
    highestID += 1;
    console.log('Highest: ' + highestID);
    var info = req.body;
    info.id = highestID;
    cans.push(info);
    res.json(info);

});