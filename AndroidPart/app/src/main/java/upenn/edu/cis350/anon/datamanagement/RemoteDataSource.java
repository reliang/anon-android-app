package upenn.edu.cis350.anon.datamanagement;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

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
                userName = postJ.getJSONObject("user").getString("name");
                genre = postJ.getJSONObject("genre").getString("name");
                content = postJ.getString("content");

                Post post = new Post(title, date, userName, genre, content);

                posts[i] = post;
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
            posts[i] = new Post("Genre","a","b","c","d");
        }

        return posts;






    }

    public static Post[] getPostsbyUserFallowed(User user) {
        Post [] posts = new Post[5];
        for (int i = 0; i < 5; i++) {
            posts[i] = new Post("Fallowed","a","b","c","d");
        }

        //Log.v("", "fallowed fill");
        return posts;

    }
}
