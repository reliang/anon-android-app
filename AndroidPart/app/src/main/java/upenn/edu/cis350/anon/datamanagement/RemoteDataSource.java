package upenn.edu.cis350.anon.datamanagement;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import java.util.Date;


import upenn.edu.cis350.anon.Post;
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
                String title, date, userName, genre, content;

                title = postJ.getString("name");
                date = postJ.getString("time");
                // get user
                String userid = postJ.getString("userId");
                URL url = new URL("http://10.0.2.2:3000/getUsernameById?id=" + userid);
                userName= getStrByUrl(url);
                // need to populate "genreId" field first
                String genreid = postJ.getString("genre");
                url = new URL("http://10.0.2.2:3000/getGenreNameById?id=" + genreid);
                genre= getStrByUrl(url);
                content = postJ.getString("content");

                Post post = new Post(userid, genreid, title, Calendar.getInstance(), userName, genre, content);

                posts[i] = post;
            }


        } catch (Exception e) {
            return null;
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


    public static Post[] getPostsbyUserGenre(User user){
        URL url;
        //String userId = user.getId();
        String userId = "5e854df97d58922d34b950cb";
        ArrayList<Post> posts = new ArrayList<Post>();

        try {
            url = new URL("http://10.0.2.2:3000/getUserGenre?id=" + userId);
            String str = getStrByUrl(url);

            Log.v("genres",str);

            JSONObject jo = new JSONObject(str);
            JSONArray genres = jo.getJSONArray("genres");

            for (int i = 0; i < genres.length(); i++) {

                String genre = genres.getString(i);

                url = new URL("http://10.0.2.2:3000/getPostsByGenre?genre=" + genre);

                Log.v("genreid",genre);
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

    /*
    public static Post[] getPostsbyUserGenre(User user) {
        Post [] posts = new Post[5];
        for (int i = 0; i < 5; i++) {
            posts[i] = new Post("id","genreID"
                    ,"Fallowed",Calendar.getInstance(),"b","c","d");
        }

        //Log.v("", "fallowed fill");
        return posts;

    }*/


    public static Post[] getPostsbyUserFallowed(User user) {

        URL url;
        //String userId = user.getId();
        String userId = "5e854df97d58922d34b950cb";
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

    public static String addPostbyObject( Post post) {
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
            return status;
        } catch (Exception e) {
            return "Error adding post";
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
