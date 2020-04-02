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
		status: 0, // 0 = user, 1 = admin, 2 = head admin
		contribution: 0,
		genresFollowed: [],
		postsFollowed: [],
		postsWritten: [],
		followers: []
	});

	// save to the database
	newUser.save((err) => {
		if (err) {
			res.json({status: 'Failed to add to database'});
		}
		else {
			// display the "successfull created" page using EJS
			res.json({ status: 'Success', user: newUser });
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
		time: new Date()
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

// route for creating a new person
// this is the action of the "create new person" form
app.use('/create', (req, res) => {
	// construct the Person from the form data which is in the request body
	var newPerson = new Person({
		name: req.body.name,
		age: req.body.age,
	});

	// save the person to the database
	newPerson.save((err) => {
		if (err) {
			res.type('html').status(200);
			res.write('uh oh: ' + err);
			console.log(err);
			res.end();
		}
		else {
			// display the "successfull created" page using EJS
			res.render('created', { person: newPerson });
		}
	});
}
);

// route for showing all the people
app.use('/all', (req, res) => {

	// find all the Person objects in the database
	Person.find({}, (err, persons) => {
		if (err) {
			res.type('html').status(200);
			console.log('uh oh' + err);
			res.write(err);
		}
		else {
			if (persons.length == 0) {
				res.type('html').status(200);
				res.write('There are no people');
				res.end();
				return;
			}
			// use EJS to show all the people
			res.render('all', { persons: persons });

		}
	}).sort({ 'age': 'asc' }); // this sorts them BEFORE rendering the results
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




/*************************************************/

app.use('/public', express.static('public'));

app.use('/', (req, res) => { res.redirect('/public/personform.html'); });

app.listen(3000, () => {
	console.log('Listening on port 3000');
});
