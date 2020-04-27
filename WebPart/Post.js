var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb+srv://projectanon:abcd1234@cluster0-rvr54.mongodb.net/test?retryWrites=true&w=majority');

var Schema = mongoose.Schema;

var postSchema = new Schema({
    // postId is its own ObjectId
    userId: {type: Schema.Types.ObjectId, ref: 'User'},
    name: String,
    content: String,
    replies: [{type: Schema.Types.ObjectId, ref: 'Reply'}],
    genre: {type: Schema.Types.ObjectId, ref: 'Genre'},
    time: Date,
    likes: Number
    });

// export postSchema as a class called Post
module.exports = mongoose.model('Post', postSchema);
