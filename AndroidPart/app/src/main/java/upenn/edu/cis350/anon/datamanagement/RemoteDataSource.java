package upenn.edu.cis350.anon.datamanagement;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import java.util.Date;


import upenn.edu.cis350.anon.Feedback;
import upenn.edu.cis350.anon.Genre;
import upenn.edu.cis350.anon.Post;
import upenn.edu.cis350.anon.Reply;
import upenn.edu.cis350.anon.User;

public class RemoteDataSource {

    public static Post[] getPostsbyUrl( URL url) {

        Post[] posts;
        try {


            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();

            JSONObject jo = new JSONObject(str);
            JSONArray postsJson = jo.getJSONArray("posts");

            posts = new Post[postsJson.length()];

            for (int i = 0; i < posts.length; i++) {

                JSONObject postJ = postsJson.getJSONObject(i);
                String title, date, userName, genre, content;

                title = postJ.getString("title");
                date = postJ.getString("date");
                // need to populate "userId" field first!
                userName = postJ.getJSONObject("user").getString("name");
                // need to populate "genreId" field first
                genre = postJ.getJSONObject("genre").getString("name");
                content = postJ.getString("content");

                //Post post = new Post(title, date, userName, genre, content);

                //posts[i] = post;
            }


        } catch (Exception e) {
            return null;
        }

        return posts;
    }

    public static Post[] getPostsInJason(String str){

        Post[] posts;
        try {

            JSONObject jo = new JSONObject(str);
            JSONArray postsJson = jo.getJSONArray("posts");

            posts = new Post[postsJson.length()];

            for (int i = 0; i < posts.length; i++) {

                JSONObject postJ = postsJson.getJSONObject(i);
                String postId, title, date, userName, iconLink, genre, content;

                postId = postJ.getString("_id");
                title = postJ.getString("name");
                date = postJ.getString("time");
                // get user
                String userid = postJ.getString("userId");
                URL url = new URL("http://10.0.2.2:3000/getUserById?id=" + userid);
                JSONObject json = new JSONObject(getStrByUrl(url));
                String status = json.getString("status");
                if (status.equals("success")) { // if user found
                    JSONObject userJ = json.getJSONObject("user");
                    userName = userJ.getString("alias");
                    iconLink = userJ.getString("iconLink");
                } else { // if no user found
                    userName = "[deleted user]";
                    iconLink = null;
                }
                // need to populate "genreId" field first
                String genreid = postJ.getString("genre");
                url = new URL("http://10.0.2.2:3000/getGenreNameById?id=" + genreid);
                genre= getStrByUrl(url);
                content = postJ.getString("content");

                Post post = new Post(userid, genreid, title, Calendar.getInstance(), userName, genre, content);
                post.setPostId(postId);
                post.setIconLink(iconLink);

                posts[i] = post;
            }


        } catch (Exception e) {
            return new Post[0];
        }

        return posts;


    }

    public static String getStrByUrl(URL url){
        try {
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            return str;
        } catch (Exception e) {
            Log.v("getStrByUrl error",e.getMessage());
            return "error";
        }
    }

    public static Post[] getPostsbyGenreId(String genre){
        URL url;
        ArrayList<Post> posts = new ArrayList<Post>();

        try {
                url = new URL("http://10.0.2.2:3000/getPostsByGenre?genre=" + genre);
                String str = getStrByUrl(url);
                Post[] temp = getPostsInJason(str);
                Collections.addAll(posts, temp);

        } catch (Exception e) {
            Log.v("error",e.getMessage());
        }
        Post[] ans = posts.toArray(new Post[posts.size()]);
        return ans;
    }
    public static Post[] getPostsbyUserGenre(User user){
        URL url;
         String userId = user.getUserId();
        //String userId = "5e854df97d58922d34b950cb";
        ArrayList<Post> posts = new ArrayList<Post>();

        try {
            url = new URL("http://10.0.2.2:3000/getUserGenre?id=" + userId);
            String str = getStrByUrl(url);
            JSONObject jo = new JSONObject(str);
            JSONArray genres = jo.getJSONArray("genres");

            for (int i = 0; i < genres.length(); i++) {

                String genre = genres.getString(i);

                url = new URL("http://10.0.2.2:3000/getPostsByGenre?genre=" + genre);
                str = getStrByUrl(url);
                Post[] temp = getPostsInJason(str);

                Collections.addAll(posts, temp);


            }
        } catch (Exception e) {
            Log.v("error",e.getMessage());


    }
        Post[] ans = posts.toArray(new Post[posts.size()]);
        return ans;
    }


    public static Post[] getPostsbyUserFallowed(User user) {

        URL url;
        String userId = user.getUserId();
        ArrayList<Post> posts = new ArrayList<Post>();

        try {
            url = new URL("http://10.0.2.2:3000/getUserFallowedPost?id=" + userId);
            String str = getStrByUrl(url);

            Log.v("followed",str);

            JSONObject jo = new JSONObject(str);
            JSONArray followed = jo.getJSONArray("followed");

            for (int i = 0; i < followed.length(); i++) {

                String postId = followed.getString(i);

                url = new URL("http://10.0.2.2:3000/getPostById?id=" + postId);

                Log.v("postid",postId);
                str = getStrByUrl(url);

                Log.v("posts",str);
                Post[] temp = getPostsInJason(str);

                Collections.addAll(posts, temp);


            }
        } catch (Exception e) {
            Log.v("error",e.getMessage());


        }
        Post[] ans = posts.toArray(new Post[posts.size()]);
        return ans;

    }

    public static Post[] testGetPost(User user) {

        Post [] posts = new Post[5];
        for (int i = 0; i < 5; i++) {
            posts[i] = new Post("id","genreID"
                    ,"Fallowed",Calendar.getInstance(),"b","c","d");
        }

        //Log.v("", "fallowed fill");
        return posts;

    }

    private static boolean isBanned(String userId) {
        URL url;
        try {
            url = new URL("http://10.0.2.2:3000/getUserById?id=" + userId);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            if (str == null) {
                return true;
            }
            JSONObject jo = new JSONObject(str);
            String status = jo.getString("status");
            // give post its id
            return jo.getJSONObject("user").getBoolean("banned");
            
        } catch (Exception e) {
            return false;
        }
    }

    public static String addPostbyObject(Post post) {
        String userId, genreId, title, content;
        Long date;

        title = post.getTitle();
        userId = post.getUserId();
        genreId = post.getGenreId();
        content = post.getContent();
        date = post.getDate().getTimeInMillis(); // gets date in millisecond format

        if (isBanned(userId)) {
            return "You are banned";
        }

        URL url;
        try{
            url = new URL("http://10.0.2.2:3000/addPost?"
                    + "userId=" + userId
                    + "&name=" + title
                    + "&content=" + content
                    + "&genreId=" + genreId
                    + "&date=" + date );
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            if (str == null) {
                return "Error accessing web";
            }
            JSONObject jo = new JSONObject(str);
            String status = jo.getString("status");
            // give post its id
            if (status.equals("Success")) {
                post.setPostId(jo.getJSONObject("post").getString("_id"));
            }
            return status;
        } catch (Exception e) {
            return "Error adding post";
        }
    }

    public static String addReplybyObject(Reply reply)  {
        String postId, userId, content;
        Long date;

        postId = reply.getPostId();
        userId = reply.getUserId();
        content = reply.getContent();
        date = reply.getDate().getTimeInMillis(); // gets date in millisecond format

        if (isBanned(userId)) {
            return "You are banned";
        }

        URL url;
        try{
            url = new URL("http://10.0.2.2:3000/addReply?"
                    + "userId=" + userId
                    + "&postId=" + postId
                    + "&content=" + content
                    + "&date=" + date );
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            if (str == null) {
                return "Error accessing web";
            }
            JSONObject jo = new JSONObject(str);
            String status = jo.getString("status");
            return status;
        } catch (Exception e) {
            return "Error adding reply";
        }
    }

    public static String populatePostInfo(Post post) {
        String postId = post.getPostId();
        URL url;

        try {
            url = new URL("http://10.0.2.2:3000/getFullPostById?id=" + postId);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            if (str == null) {
                return "Error accessing web";
            }
            JSONObject jo = new JSONObject(str);
            String status = jo.getString("status");
            if (status.equals("success")) {
                JSONArray postsJ = jo.getJSONArray("posts");
                JSONObject postJ = postsJ.getJSONObject(0);
                // set icon link
                JSONObject userJ = postJ.getJSONObject("userId");
                post.setIconLink(userJ.getString("iconLink"));
                // add replies
                JSONArray repliesJ = postJ.getJSONArray("replies");
                for (int i = 0; i < repliesJ.length(); i++) {
                    JSONObject replyJ = repliesJ.getJSONObject(i);
                    Reply reply = getReplyFromJSON(replyJ);
                    post.addReply(reply);
                }
            }
            return status;
        } catch (Exception e) {
            return "Error getting replies";
        }
    }


    public static String addUserByObject(User user) {
        String alias = user.getAlias();
        String password = user.getPassword();
        String iconLink = user.getIconLink();

        URL url;
        try{
            if (iconLink != null) {
                url = new URL("http://10.0.2.2:3000/addUser?"
                        + "alias=" + alias
                        + "&password=" + password
                        + "&iconLink=" + iconLink);
            } else {
                url = new URL("http://10.0.2.2:3000/addUser?"
                        + "alias=" + alias
                        + "&password=" + password);
            }
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            if (str == null) {
                return "Error accessing web";
            }
            JSONObject jo = new JSONObject(str);
            String status = jo.getString("status");
            // give user its id
            if (status.equals("success")) {
                user.setUserId(jo.getJSONObject("user").getString("_id"));
            }
            return status;
        } catch (Exception e) {
            return "Error adding user";
        }
    }

    public static String getUserByObject(User user) {
        String alias = user.getAlias();
        String password = user.getPassword();

        try {
            URL url = new URL("http://10.0.2.2:3000/getUserByName?"
                    + "alias=" + alias);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            if (str == null) {
                return "Error accessing web";
            }
            JSONObject jo = new JSONObject(str);
            String status = jo.getString("status");
            if (status.equals("success")) {
                if (jo.getJSONObject("user").getString("password").equals(password)) {
                    // fill user with info
                    JSONObject userJSON = jo.getJSONObject("user");
                    String id = userJSON.getString("_id");
                    String iconLink = userJSON.getString("iconLink");
                    int userStatus = userJSON.getInt("status");
                    int contribution = userJSON.getInt("contribution");
                    user.setUserId(id);
                    user.setIconLink(iconLink);
                    user.setUserStatus(userStatus);
                    user.setContribution(contribution);
                    return "success";
                } else {
                    return "wrong username or password";
                }
            }
            return status;
        } catch (Exception e) {
            return "Error getting user";
        }
    }

    public static String addFeedbackbyObject(Feedback feedback) {
        String content;
        Long date;

        content = feedback.getContent();
        date = feedback.getDate().getTimeInMillis(); // gets date in millisecond format

        try {
            URL url = new URL("http://10.0.2.2:3000/addFeedback?"
                    + "content=" + content + "&date=" + date);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            if (str == null) {
                return "Error accessing web";
            }
            JSONObject jo = new JSONObject(str);
            String status = jo.getString("status");

            return status;
        } catch (Exception e) {
            return "Error adding feedback";
        }
    }

    public static String addFollowerByObjects(User follower, User following) {
        String followerId = follower.getUserId();
        String followingId = following.getUserId();

        URL url;
        try{
            url = new URL("http://10.0.2.2:3000/addFollower?"
                    + "followerId=" + followerId
                    + "&followingId=" + followingId);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            if (str == null) {
                return "Error accessing web";
            }
            JSONObject jo = new JSONObject(str);
            String status = jo.getString("status");
            return status;
        } catch (Exception e) {
            return "Error adding user";
        }
    }

    public static String getAllGenres(ArrayList<Genre> genres) {
        URL url;
        try{
            url = new URL("http://10.0.2.2:3000/getAllGenres");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            if (str == null) {
                return "Error accessing web";
            }
            JSONObject jo = new JSONObject(str);
            String status = jo.getString("status");
            if (status.equals("success")) {
                JSONArray genresJ = jo.getJSONArray("genres");
                for (int i = 0; i < genresJ.length(); i++) {
                    JSONObject genreJ = genresJ.getJSONObject(i);
                    genres.add(new Genre(genreJ.getString("_id"), genreJ.getString("name")));
                }
            }
            return status;
        } catch (Exception e) {
            return "Error adding user";
        }
    }
        
    // gets following/followers/posts written
    public static String populateUserProfile(User user) {
        String alias = user.getAlias();

        try {
            URL url = new URL("http://10.0.2.2:3000/getUserFullProfile?"
                    + "alias=" + alias);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String str = task.get();
            if (str == null) {
                return "Error accessing web";
            }
            JSONObject jo = new JSONObject(str);
            String status = jo.getString("status");
            if (status.equals("success")) {
                JSONObject userJSON = jo.getJSONObject("user");
                // fill user with info
                String id = userJSON.getString("_id");
                String iconLink = userJSON.getString("iconLink");
                int userStatus = userJSON.getInt("status");
                int contribution = userJSON.getInt("contribution");
                user.setUserId(id);
                user.setIconLink(iconLink);
                user.setUserStatus(userStatus);
                user.setContribution(contribution);
                // fill lists
                JSONArray postsJSON = userJSON.getJSONArray("postsWritten");
                JSONArray followingJSON = userJSON.getJSONArray("following");
                JSONArray followersJSON = userJSON.getJSONArray("followers");
                // turn json array into posts
                for (int i = 0; i < postsJSON.length(); i++) {
                    JSONObject postJ = postsJSON.getJSONObject(i);
                    Post post = getPostFromJSON(postJ);
                    user.addPostWritten(post);
                }
                for (int i = 0; i < followingJSON.length(); i++) {
                    JSONObject followingJ = followingJSON.getJSONObject(i);
                    User following = getUserFromJSON(followingJ);
                    user.addFollowing(following);
                }
                for (int i = 0; i < followersJSON.length(); i++) {
                    JSONObject followerJ = followersJSON.getJSONObject(i);
                    User follower = getUserFromJSON(followerJ);
                    user.addFollower(follower);
                }
                return "success";
            }
            return status;
        } catch (Exception e) {
            return "Error getting posts written";
        }
    }

    // Helper Functions! -----------------------------------------------------------------------
    public static Post getPostFromJSON(JSONObject postJ) {
        try {
            URL url;
            String postId, title, date, userName, iconLink, genre, content;

            postId = postJ.getString("_id");
            title = postJ.getString("name");
            date = postJ.getString("time");
            // get user
            String userid = postJ.getString("userId");
            url = new URL("http://10.0.2.2:3000/getUserById?id=" + userid);
            JSONObject jo = new JSONObject(getStrByUrl(url));
            String status = jo.getString("status");
            if (status.equals("success")) { // if user found
                JSONObject userJ = jo.getJSONObject("user");
                userName = userJ.getString("alias");
                iconLink = userJ.getString("iconLink");
            } else { // if no user found
                userName = "[deleted user]";
                iconLink = null;
            }
            // need to populate "genreId" field first
            String genreid = postJ.getString("genre");
            url = new URL("http://10.0.2.2:3000/getGenreNameById?id=" + genreid);
            genre= getStrByUrl(url);
            content = postJ.getString("content");

            Post post = new Post(userid, genreid, title, Calendar.getInstance(), userName, genre, content);
            post.setPostId(postId);
            post.setIconLink(iconLink);
            return post;
        } catch (Exception e) {
            return null;
        }
    }

    public static User getUserFromJSON(JSONObject userJSON) {
        try {
            String alias = userJSON.getString("alias");
            String id = userJSON.getString("_id");
            String iconLink = userJSON.getString("iconLink");
            int userStatus = userJSON.getInt("status");
            int contribution = userJSON.getInt("contribution");

            User user = new User(alias);
            user.setUserId(id);
            user.setIconLink(iconLink);
            user.setUserStatus(userStatus);
            user.setContribution(contribution);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public static Reply getReplyFromJSON(JSONObject replyJ) {
        try {
            URL url;
            String postId, userId, date, username, iconLink, content;

            postId = replyJ.getString("_id");
            date = replyJ.getString("time");
            // get user
            userId = replyJ.getString("userId");
            url = new URL("http://10.0.2.2:3000/getUserById?id=" + userId);
            JSONObject jo = new JSONObject(getStrByUrl(url));
            String status = jo.getString("status");
            if (status.equals("success")) { // if user found
                JSONObject userJ = jo.getJSONObject("user");
                username = userJ.getString("alias");
                iconLink = userJ.getString("iconLink");
            } else { // if no user found
                username = "[deleted user]";
                iconLink = null;
            }
            content = replyJ.getString("content");
            //Reply(String postId, String userId, String username, String content, Calendar date)
            Reply reply = new Reply(postId, userId, username, iconLink, content, Calendar.getInstance());
            return reply;
        } catch (Exception e) {
            return null;
        }
    }


    public static void addUserFollowedGenre(User user, String genre) {

        URL url;
        String userId = user.getUserId();

        try {
            url = new URL("http://10.0.2.2:3000/addFollowedGenre?userId=" + userId+"&genreId="+genre);
            getStrByUrl(url);

        } catch (Exception e) {
            Log.v("error", e.getMessage());


        }
    }
}
