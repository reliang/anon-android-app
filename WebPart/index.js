

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
var Feedback = require('./Feedback.js');

/***************************************/

// route for creating a new user
app.use('/addUser', (req, res) => {
	// construct the Post from the input data
	var newUser = new User({
		alias: req.query.alias,
		password: req.query.password,
		iconLink: req.query.iconLink,
		status: 0, // 0 = us er, 1 = admin, 2 = head admin
		online: true,
		banned: false,
		readByNotifications: false,
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
			res.json({ status: err });
		}
		else {
			// display the "successfull created" page using EJS
			res.json({ status: 'success', user: newUser });
		}
	});
}
);

// route for getting a user by alias
app.use('/getUserByName', (req, res) => {
	// construct the Post from the input data

	var username = req.query.alias

	User.findOne({ alias: username }, (err, user) => {
		if (err) {
			res.json({ status: err });
		}
		else if (!user) {
			res.json({ status: 'no user' })
		}
		else {
			res.json({ status: 'success', user: user })
		}
	});
}
);

// route for getting a user by id
app.use('/getUserById', (req, res) => {
	// construct the Post from the input data

	var id = req.query.id

	User.findOne({ "_id": id }, (err, user) => {
		if (err) {
			res.json({ status: err });
		}
		else if (!user) {
			res.json({ status: 'no user' })
		}
		else {
			res.json({ status: 'success', user: user })
		}
	});
}
);

// route for getting a user along with following/followers/posts written array
app.use('/getUserFullProfile', (req, res) => {
	// construct the Post from the input data

	var username = req.query.alias

	User.findOne({ alias: username })
		.populate("postsWritten")
		.populate("following")
		.populate("followers")
		.exec(function (err, user) {
			if (err) {
				res.json({ status: err });
			}
			else if (!user) {
				res.json({ status: 'no user' })
			}
			else {
				res.json({ status: 'success', user: user })
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
			res.json({ status: 'Failed to add to database' });
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
	var userId = req.query.userId;

	// construct the Post from the input data
	var newPost = new Post({
		userId: userId,
		name: req.query.name, // title
		content: req.query.content,
		replies: [],
		genre: req.query.genreId,
		time: new Date((Number)(req.query.date)), // turns milliseconds into date format
		likes: 0
	});

	// save to the database
	newPost.save((err, post) => {
		if (err) {
			res.json({ status: 'Failed to add to database' });
		}
		else {
			// add post to user's post written
			User.update(
				{ _id: userId },
				{
					$push: { postsWritten: post._id }
				},
				(err, result) => {
					if (err) {
						res.json({ status: 'Error adding to post written' });
					} else {
						// display the "successfull created" page using EJS
						res.json({ status: 'Success', post: newPost });
					}
				}
			);
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
		time: new Date((Number)(req.query.date)) // turns milliseconds into date format
	});

	newReply.save((err, reply) => {
		if (err) {
			res.json({ status: 'Failed to add to database' });
		}
		else {
			// add replyId to post
			Post.update(
				{ _id: postId },
				{
					$push: { replies: reply._id }
				},
				(err, result) => {
					if (err) {
						res.json({ status: 'Error updating post' });
					} else {
						res.json({ status: 'Success', reply: newReply });
					}
				}
			);
		}
	});
}
);

// route for creating a new post
app.use('/addFollower', (req, res) => {
	var followerId = req.query.followerId;
	var followingId = req.query.followingId;

	// add followerId to following's followers
	User.update(
		{ _id: followingId },
		{
			$push: { followers: followerId }
		},
		(err, result) => {
			if (err) {
				res.json({ status: 'Error adding follower' });
			} else {
				// add followingId to follower's following
				User.update(
					{ _id: followerId },
					{
						$push: { following: followingId }
					},
					(err, result) => {
						if (err) {
							res.json({ status: 'Error adding following' });
							return;
						} else {
							// display the "successfull created" page using EJS
							res.json({ status: 'Success' });
						}
					}
				);
			}
		}
	);


}
);


//add a genre followed by user
app.use('/addFollowedGenre', (req, res) => {
	// construct the Post from the input data
	var userId = req.query.userId;
	var genreId = req.query.genreId;

	// add replyId to post
	User.updateOne(
		{ _id: userId },
		{
			$addToSet: { genresFollowed: genreId}
			//$set: {alias:'anne'}
		},
		(err, result) => {
			if (err) {
				//res.json({ status: 'Error updating followed' });
				 console.warn(err.message);
			} else {
				res.json({ status: 'Success'});
			}
		}
	);
		
});

//add a genre followed by user
app.use('/addLike', (req, res) => {
	// construct the Post from the input data
	var userId = req.query.userId;
	var postId = req.query.postId;

	// add like to user's liked
	User.updateOne(
		{ _id: userId },
		{
			$addToSet: { postsFollowed: postId}
			//$set: {alias:'anne'}
		},
		(err, result) => {
			if (err) {
				//res.json({ status: 'Error updating followed' });
				 console.warn(err.message);
			} else {
				//res.json({ status: 'Success'});
			}
		}
	);

	//add 1 to number of likes
	Post.updateOne(
		{ _id: postId },
		{
			$inc: { likes: 1}
			//$set: {alias:'anne'}
		},
		(err, result) => {
			if (err) {
				//res.json({ status: 'Error updating followed' });
				 console.warn(err.message);
			} else {
				res.json({ status: 'Success'});
			}
		}
	);
		
});


app.use('/getAllGenres', (req, res) => {
	Genre.find((err, genres) => {
		if (err) {
			res.json({ status: 'Error getting genres' });
		} else {
			res.json({ status: 'success', 'genres': genres});
		}
	})
}
);

app.use('/getGenreByName', (req, res) => {
	var searchName = req.query.name;
	if (searchName) {
		Genre.findOne({ name: searchName }, (err, genre) => {
			if (err) {
				res.json({});
			}
			else if (!genre) {
				// no objects found, so send back empty json
				res.json({});
			}
			else {
				;
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
		User.findOne({ alias: searchName }, (err, user) => {
			if (err) {
				res.json({});
			}
			else if (!user) {
				// no objects found, so send back empty json
				res.json({});
			}
			else {
				;
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

	User.findOne({ "_id": o_id }, (err, user) => {
		if (err) {
			res.json({ 'status': err });
		}
		else if (!user) {
			res.json({ 'status': 'no person' });
		}
		else {
			//res.json( { 'person' : person } ); 

			res.json({ 'genres': user.genresFollowed });


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

	User.findOne({ "_id": o_id }, (err, user) => {
		if (err) {
			res.json({ 'status': err });
		}
		else if (!user) {
			res.json({ 'status': 'no person' });
		}
		else {
			//res.json( { 'person' : person } ); 

			res.json({ 'followed': user.postsFollowed });


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

	Post.find({ 'genre': ObjectID(genre) }, (err, posts) => {
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

			res.json({ 'posts': posts });
		}

	});
});


app.use('/getUsernameById', (req, res) => {

	var id = req.query.id;
	var o_id = new ObjectID(id);

	User.findOne({ "_id": o_id }, (err, user) => {
		if (err) {
			res.json({ 'status': err });
		}
		else if (!user) {
			res.json({ 'status': 'no person' });
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

	Genre.findOne({ "_id": o_id }, (err, g) => {
		if (err) {
			res.json({ 'status': err });
		}
		else if (!g) {
			res.json({ 'status': 'no person' });
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

	Post.findOne({ "_id": o_id }, (err, p) => {
		if (err) {
			res.json({ 'status': err });
		}
		else if (!p) {
			res.json({ 'status': 'no person' });
		}
		else {
			var ans = [];
			ans.push(p);
			res.json({ 'posts': ans });


		}
	});
});

// route for getting a post along with replies
app.use('/getFullPostById', (req, res) => {

	var id = req.query.id;
	var o_id = new ObjectID(id);

	Post.findOne({ "_id": o_id })
	.populate("userId")
	.populate("replies")
	.populate("genre")
	.exec(function (err, p) {
		if (err) {
			res.json({ 'status': err });
		}
		else if (!p) {
			res.json({ 'status': 'no person' });
		}
		else {
			var ans = [];
			ans.push(p);
			res.json({ 'status': 'success', 'posts': ans });


		}
	});
});

// route for adding a new feedback
app.use('/addFeedback', (req, res) => {
	// construct the Feedback from the input data
	var newFeedback = new Feedback({
		userId: req.query.userId,
		content: req.query.content,
		time: new Date((Number)(req.query.date)) // turns milliseconds into date format
	});

	// save to the database
	newFeedback.save((err) => {
		if (err) {
			res.json({ status: 'Failed to add to database' });
		}
		else {
			// display the "successfull created" page using EJS
			res.json({ status: 'Success', feedback: newFeedback });
		}
	});
}
);

// route for banning a user from posting
app.use('/ban_user', (req, res) => {
	var alias = req.query.alias;

	User.updateOne({ alias: alias }, { banned: true }, (err, p) => {
		if (err) {
			res.json({ 'status': err });
		}
	});

	setTimeout(function () {
		res.redirect('/ban');
	}, 1000)
});

// route for unbanning a user from posting
app.use('/unban_user', (req, res) => {
	var alias = req.query.alias;

	User.updateOne({ alias: alias }, { banned: false }, (err, p) => {
		if (err) {
			res.json({ 'status': err });
		}
	});

	setTimeout(function () {
		res.redirect('/ban');
	}, 1000)
});

app.use("/login", (req, res) => {
	var alias = req.query.alias;

	User.updateOne({ alias: alias }, { online: true }, (err, p) => {
		if (err) {
			res.json({ 'status': err });
		}
	});
		
	res.redirect('/');
});

app.use("/logout", (req, res) => {
	var alias = req.query.alias;

	User.updateOne({ alias: alias }, { online: false }, (err, p) => {
		if (err) {
			res.json({ 'status': err });
		}
	});
		
	res.redirect('/');
});



// route for accessing data via the web api
// to use this, make a request for /api to get an array of all Person objects
// or /api?name=[whatever] to get a single object
app.use('/api', (req, res) => {
	console.log("LOOKING FOR SOMETHING?");

	// construct the query object
	var queryObject = {};
	if (req.query.name) {
		// if there's a name in the query parameter, use it here
		queryObject = { "name": req.query.name };
	}

	Person.find(queryObject, (err, persons) => {
		console.log(persons);
		if (err) {
			console.log('uh oh' + err);
			res.json({});
		}
		else if (persons.length == 0) {
			// no objects found, so send back empty json
			res.json({});
		}
		else if (persons.length == 1) {
			var person = persons[0];
			// send back a single JSON object
			res.json({ "name": person.name, "age": person.age });
		}
		else {
			// construct an array out of the result
			var returnArray = [];
			persons.forEach((person) => {
				returnArray.push({ "name": person.name, "age": person.age });
			});
			// send it back as JSON Array
			res.json(returnArray);
		}

	});
});




/***************** Front End *********************/

app.get("/", (req, res) => {
	Post.aggregate([{$group: {_id: "$genre", count: { $sum: 1 }}}, {$sort: {_id: -1}}],
					 (err, genres) => {
		if (err) {
			res.write(err);
		} else {
			User.find({}, {_id: 0, alias: 1, online: 1, postsWritten: 1},
					 (err, users) => {
				if (err) {
					res.write(err);
				} else {
					res.render('home', {genres: genres, users: users});
				}
			});
		}
	});



	//res.render('home');
});

// route for returning all the feedbacks
app.get("/getFeedback", (req, res) => {
	// find all the Feedback objects in the database
	Feedback.find((err, feedbacks) => {
		if (err) {
			res.type('html').status(200);
			console.log('uh oh' + err);
			res.write(err);
		}
		else {
			if (feedbacks.length == 0) {
				res.type('html').status(200);
				res.write('There are no feedbacks');
				res.end();
				return;
			}
			// use EJS to show all the feedback
			res.render('feedback', { feedbacks: feedbacks.reverse() });
		}
	});
});

app.get("/ban", (req, res) => {
	// find all the User objects in the database
	User.find((err, users) => {
		if (err) {
			res.type('html').status(200);
			console.log('uh oh' + err);
			res.write(err);
		}
		else {
			if (users.length == 0) {
				res.type('html').status(200);
				res.write('There are no users');
				res.end();
				return;
			}
			// use EJS to show all the users
			res.render('ban', { users: users });
		}
	});
});

app.listen(3000, () => {
	console.log('Listening on port 3000');
});
