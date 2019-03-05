var express = require('express');
var bodyParser = require('body-parser');
var app = express();

// bodyParser is a type of middleware
// It helps convert JSON strings
// the 'use' method assigns a middleware
app.use(bodyParser.json({ type: 'application/json' }));

const hostname = '127.0.0.1';
const port = 3000;

// http status codes
const statusOK = 200;
const statusNotFound = 404;

// using an array to simulate a database for demonstration purposes
var mockDatabase = [
    {
        fruit: "apple",
        color: "red"
    },
    {
        fruit: "banana",
        color: "yellow"
    }
]

// Handle GET (all) request
app.get('/', function(req, res) {

    // error checking
    if (mockDatabase.length < 1) {
        res.statusCode = statusNotFound;
        res.send('Item not found');
        return;
    }

    // send response
    res.statusCode = statusOK;
    res.send(mockDatabase);
});

// Handle GET (one) request
app.get('/:id', function(req, res) {

    // error checking
    var id = req.params.id;
    if (id < 0 || id >= mockDatabase.length) {
        res.statusCode = statusNotFound;
        res.send('Item not found');
        return;
    }
    
    // send response
    res.statusCode = statusOK;
    res.send(mockDatabase[id]);
});

// Handle POST request
app.post('/', function(req, res) {

    // get data from request
    var newObject = req.body; // TODO validate data
    mockDatabase.push(newObject);

    // send created item back with id included
    var id = mockDatabase.length - 1;
    res.statusCode = statusOK;
    res.send(`Item added with id ${id}`);
});

// Handle PUT request
app.put('/:id', function(req, res) {

    // replace current object
    var id = req.params.id;     // TODO validate id
    var replacement = req.body; // TODO validate data
    mockDatabase[id] = replacement;

    // report back to the client
    res.statusCode = statusOK;
    res.send(`Item replaced at id ${id}`);
});

// Handle PATCH request 
app.patch('/:id', function(req, res) {

    // update current object
    var id = req.params.id;        // TODO validate id
    var newColor = req.body.color; // TODO validate data
    mockDatabase[id].color = newColor;

    // report back to the client
    res.statusCode = statusOK;
    res.send(`Item updated at id ${id}`);
});

// Handle DELETE request 
app.delete('/:id', function(req, res) {
    
    // delete specified item
    var id = req.params.id;  // TODO validate id
    mockDatabase.splice(id, 1);

    // send response back
    res.statusCode = statusOK;
    res.send();
});

app.listen(port, hostname, function () {
    console.log(`Listening at http://${hostname}:${port}/...`);
});
