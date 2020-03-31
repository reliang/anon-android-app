package upenn.edu.cis350.anon;

import java.util.ArrayList;

public class Post {
    int userId;
    int postId;
    String content;
    ArrayList<Reply> replies;
    int genreId;
    int time; // replace with Java Time class

    String name;

    public Post(String n, String c){
        name = n;
        content = c;
    }


    public String getContent(){
        return content;
    }
    public String getUserName(){
        return name;
    }
}