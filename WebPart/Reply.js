var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb+srv://projectanon:abcd1234@cluster0-rvr54.mongodb.net/test?retryWrites=true&w=majority');

var Schema = mongoose.Schema;

var replySchema = new Schema({
    userId: {type: Schema.Types.ObjectId, ref: 'User'},
    readByNotifications: false,
    content: String,
    time: Date
    });

module.exports = mongoose.model('Reply', replySchema);