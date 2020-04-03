var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb+srv://projectanon:abcd1234@cluster0-rvr54.mongodb.net/test?retryWrites=true&w=majority');

var Schema = mongoose.Schema;

var userSchema = new Schema({
    // userId is its own ObjectId
    alias: {type: String, required: true, unique: true},
    password: {type: String, required: true},
    iconLink: String,
    status: {type: Number, min: 0, max: 2,  required: true},
    contribution: Number,
    genresFollowed: [{type: Schema.Types.ObjectId, ref: 'Genre'}],
    postsFollowed: [{type: Schema.Types.ObjectId, ref: 'Post'}],
    postsWritten: [{type: Schema.Types.ObjectId, ref: 'Post'}],
    followers: [{type: Schema.Types.ObjectId, ref: 'User'}]
    });

module.exports = mongoose.model('User', userSchema);