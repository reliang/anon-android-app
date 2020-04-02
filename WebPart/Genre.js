var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb+srv://projectanon:abcd1234@cluster0-rvr54.mongodb.net/test?retryWrites=true&w=majority');

var Schema = mongoose.Schema;

var genreSchema = new Schema({
    // genreId is it own ObjectId
    name: {type: String, required: true, unique: true}
    });

module.exports = mongoose.model('Genre', genreSchema);