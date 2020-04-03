package upenn.edu.cis350.anon.datamanagement;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.Date;

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

    public static Post[] getPostsbyUserGenre(User use){
        URL url;

        /*
        try{
            url = new URL("http://10.0.2.2:3000/api?id=");
        } catch (Exception e) {
            return null;
        }
        return getPostsbyUrl(url);*/



        Post [] posts = new Post[5];
        for (int i = 0; i < 5; i++) {
            //posts[i] = new Post("Genre","a","b","c","d");
        }

        return posts;






    }

    public static Post[] getPostsbyUserFallowed(User user) {
        Post [] posts = new Post[5];
        for (int i = 0; i < 5; i++) {
            //posts[i] = new Post("Fallowed","a","b","c","d");
        }

        //Log.v("", "fallowed fill");
        return posts;

    }

    public static String addPostbyObject(Post post) {
        String userId, genreId, title, content;
        Long date;

        title = post.getTitle();
        userId = post.getUserId();
        genreId = post.getGenreId();
        content = post.getContent();
        date = post.getDate().getTimeInMillis(); // gets date in millisecond format

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

    public static String addReplybyObject(Reply reply) {
        String postId, userId, content;
        Long date;

        postId = reply.getPostId();
        userId = reply.getUserId();
        content = reply.getContent();
        date = reply.getDate().getTimeInMillis(); // gets date in millisecond format

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
}

    public static String addUserByObject(User user) {
        String alias = user.getAlias();
        String password = user.getPassword();

        try{
            URL url = new URL("http://10.0.2.2:3000/addUser?"
                    + "alias=" + alias
                    + "&password=" + password);
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
            return "Error adding post";
        }
    }

    public static String getUserByObject(User user) {
        String alias = user.getAlias();
        String password = user.getPassword();

        try{
            URL url = new URL("http://10.0.2.2:3000/getUser?"
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
                    return "success";
                } else {
                    return "wrong username or password";
                }
            }
            return status;
        } catch (Exception e) {
            return "Error adding post";
        }
    }
}
