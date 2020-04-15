var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb+srv://admin:APOC123@clusterAPOC-kktca.mongodb.net/test?retryWrites=true&w=majority', { useNewUrlParser: true });

var Schema = mongoose.Schema;

var personSchema = new Schema({
firstname: {type: String, required: true},
lastname: {type: String, required: true},
phonenumber: String,
region: String,
testpos: false
});

// export personSchema as a class called Person
module.exports = mongoose.model('Person', personSchema);