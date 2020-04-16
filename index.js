// set up Express
var express = require('express');
var app = express();

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

// import the Person class from Person.js
var Person = require('./Person.js');

//import the Worker class from Worker.js
var Worker = require('./Worker.js');

// route for creating a new healthcare worker
app.use('/createWorker', (req, res) => {
	var newWorker = new Worker ({
		firstname: req.body.firstname,
		lastname: req.body.lastname,
		username: req.body.username,
		passwordHash: req.body.passwordHash,
	});


// save the person to the database
newWorker.save( (err) => {
if (err) {
    res.type('html').status(200);
    console.log(err);
    res.end();
}
else {
    // display the "successfull created" page using EJS
    res.send("Success");
	}
  	});
    }
);

app.use('/createPerson', (req, res)=> {
    var newPerson = new Person ({
        firstname: req.query.firstname,
        lastname: req.query.lastname,
        phonenumber: req.query.phonenumber,
        region: req.query.region,
        testpos: false
    });

    newPerson.save((err) => {
        if (err) {
            res.type('html').status(200);
            console.log(err);
            res.end();
        } else {
            res.send("Success");
        }
    })
});