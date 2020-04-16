

// set up Express
var express = require('express');
var app = express();

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

var ObjectID = require('mongodb').ObjectID



// import schema classes
var Post = require('./Post.js');
var Reply = require('./Reply.js');
var User = require('./User.js');
var Genre = require('./Genre.js');

/***************************************/

// route for creating a new user
app.use('/addUser', (req, res) => {
	// construct the Post from the input data
	var newUser = new User({
		alias: req.query.alias,
		password: req.query.password,
		iconLink: req.query.iconLink,
		status: 0, // 0 = us er, 1 = admin, 2 = head admin
		contribution: 0,
		genresFollowed: [],
		postsFollowed: [],
		postsWritten: [],
		following: [],
		followers: []
	});

	// save to the database
	newUser.save((err) => {
		if (err) {
			res.json({status: err});
		}
		else {
			// display the "successfull created" page using EJS
			res.json({ status: 'success', user: newUser});
		}
	});
}
);

// route for getting a user
app.use('/getUser', (req, res) => {
	// construct the Post from the input data

	var username = req.query.alias

	User.findOne({alias: username}, (err, user) => {
		if (err) {
			res.json({status: err});
		}
		else if (!user) {
			res.json({status: 'no user'})
		}
		else {
			res.json({status: 'success', user: user})
		}
	});
}
);

// route for getting a user along with following/followers/posts written array
app.use('/getUserFullProfile', (req, res) => {
	// construct the Post from the input data

	var username = req.query.alias

	User.findOne({alias: username})
		.populate("postsWritten")
		.populate("following")
		.populate("followers")
		.exec(function (err, user) {
			if (err) {
				res.json({status: err});
			}
			else if (!user) {
				res.json({status: 'no user'})
			}
			else {
				res.json({status: 'success', user: user})
			}
		});
}
);

// route for creating a new genre
app.use('/addGenre', (req, res) => {
	// construct the Post from the input data
	var newGenre = new Genre({
		name: req.query.name
	});

	// save to the database
	newGenre.save((err) => {
		if (err) {
			res.json({status: 'Failed to add to database'});
		}
		else {
			// display the "successfull created" page using EJS
			res.json({ status: 'Success', genre: newGenre });
		}
	});
}
);

// route for creating a new post
app.use('/addPost', (req, res) => {
	// construct the Post from the input data
	var newPost = new Post({
		userId: req.query.userId,
		name: req.query.name, // title
		content: req.query.content,
		replies: [],
		genre: req.query.genreId,
		time: new Date((Number) (req.query.date)) // turns milliseconds into date format
	});

	// save to the database
	newPost.save((err) => {
		if (err) {
			res.json({status: 'Failed to add to database'});
		}
		else {
			// display the "successfull created" page using EJS
			res.json({ status: 'Success', post: newPost });
		}
	});
}
);

// route for creating a new reply
app.use('/addReply', (req, res) => {
	// construct the Post from the input data
	var postId = req.query.postId;

	var newReply = new Reply({
		userId: req.query.userId,
		content: req.query.content,
		time: new Date((Number) (req.query.date)) // turns milliseconds into date format
	});

	newReply.save((err, reply) => {
		if (err) {
			res.json({status: 'Failed to add to database'});
		}
		else {
			// add replyId to post
			Post.update(
				{_id: postId},
				{
					$push: {replies: reply._id}
				},
				(err, result) => {
					if (err) {
						res.json({status: 'Error updating post'});
						return;
					}
				}
			);
			res.json({ status: 'Success', reply: newReply });
		}
	});
}
);

app.use('/getGenreByName', (req, res) => {
	var searchName = req.query.name;
	if (searchName) {
		Genre.findOne({name: searchName}, (err, genre) => {
			if (err) {
				res.json({});
			}
			else if (!genre) {
				// no objects found, so send back empty json
				res.json({});
			}
			else {;
				// send back a single JSON object
				res.json(genre);
			}
		});
	} else {

	}
}
);

app.use('/getUserByName', (req, res) => {
	var searchName = req.query.name;
	if (searchName) {
		User.findOne({alias: searchName}, (err, user) => {
			if (err) {
				res.json({});
			}
			else if (!user) {
				// no objects found, so send back empty json
				res.json({});
			}
			else {;
				// send back a single JSON object
				res.json(user);
			}
		});
	} else {

	}
}
);

app.use('/getUserGenre', (req, res) => {
	var id = req.query.id; 
	var o_id = new ObjectID(id);

	User.findOne( {"_id":o_id}, (err, user) => 
		{ 
		if (err) {
			res.json( { 'status' : err } ); 
		} 
		else if (!user) { 
			res.json( { 'status' : 'no person' } ); 
		} 
		else {
			//res.json( { 'person' : person } ); 

			res.json( { 'genres' : user.genresFollowed } );


/*
			(user.genresFollowed).forEach((g) => {
				genres.push(g);
			});*/

			//res.json( { 'genres' : user.genresFollowed } ); 
		    //genres.concat(user.genresFollowed);

		}
	});
	
});

app.use('/getUserFallowedPost', (req, res) => {
	var id = req.query.id; 
	var o_id = new ObjectID(id);

	User.findOne( {"_id":o_id}, (err, user) => 
		{ 
		if (err) {
			res.json( { 'status' : err } ); 
		} 
		else if (!user) { 
			res.json( { 'status' : 'no person' } ); 
		} 
		else {
			//res.json( { 'person' : person } ); 

			res.json( { 'followed' : user.postsFollowed } );


/*
			(user.genresFollowed).forEach((g) => {
				genres.push(g);
			});*/

			//res.json( { 'genres' : user.genresFollowed } ); 
		    //genres.concat(user.genresFollowed);

		}
	});
	
});

app.use('/getPostsByGenre', (req, res) => {

	var genre = req.query.genre; 

	Post.find({'genre':ObjectID(genre)}, (err, posts) => {
		//console.log(posts);
		if (err) {
			console.log('uh oh' + err);
			res.json({});
		}
		else if (posts.length == 0) {
			// no objects found, so send back empty json
			res.json({});
		}
		else {


/*
			posts.forEach((post) => {
				await post.populate('userId').execPopulate();;
				await post.populate('genre').execPopulate();;
				console.log(post.genre);

			});*/
		
		res.json({'posts':posts});
		}

	});
});


app.use('/getUsernameById', (req, res) => {

	var id = req.query.id; 
	var o_id = new ObjectID(id);

	User.findOne( {"_id":o_id}, (err, user) => 
		{ 
		if (err) {
			res.json( { 'status' : err } ); 
		} 
		else if (!user) { 
			res.json( { 'status' : 'no person' } ); 
		} 
		else {
			//res.json( { 'person' : person } ); 

			res.send(user.alias);
		}
	});
});

app.use('/getGenreNameById', (req, res) => {

	var id = req.query.id; 
	var o_id = new ObjectID(id);

	Genre.findOne( {"_id":o_id}, (err, g) => 
		{ 
		if (err) {
			res.json( { 'status' : err } ); 
		} 
		else if (!g) { 
			res.json( { 'status' : 'no person' } ); 
		} 
		else {
			//res.json( { 'person' : person } ); 

			res.send(g.name);
		}
	});
});

app.use('/getPostById', (req, res) => {

	var id = req.query.id; 
	var o_id = new ObjectID(id);

	Post.findOne( {"_id":o_id}, (err, p) => 
		{ 
		if (err) {
			res.json( { 'status' : err } ); 
		} 
		else if (!p) { 
			res.json( { 'status' : 'no person' } ); 
		} 
		else {
			var ans = [];
			ans.push(p);
			res.json( { 'posts' : ans } ); 

			
		}
	});
});




/*************************************************/

app.use('/public', express.static('public'));

app.use('/', (req, res) => { res.redirect('/public/personform.html'); });

app.listen(3000, () => {
	console.log('Listening on port 3000');
});
