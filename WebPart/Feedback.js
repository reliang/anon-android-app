var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb+srv://projectanon:abcd1234@cluster0-rvr54.mongodb.net/test?retryWrites=true&w=majority');

var Schema = mongoose.Schema;

var feedbackSchema = new Schema({
    content: String,
    time: Date
    });

// export feedbackSchema as a class called feedback
module.exports = mongoose.model('Feedback', feedbackSchema);
