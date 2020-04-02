package upenn.edu.cis350.anon;

import java.util.ArrayList;

public class Post {

    //int userId;
    int postId;

    String title,date,user,genre,content;

    ArrayList<Reply> replies;
   // int time; // replace with Java Time class


    public Post(){

    }

    public Post(String t, String d, String u, String g, String c)  {
        title = t;
        date = d;
        user = u;
        genre = g;
        content = c;
    }

    public Post(String n, String c){
        user = n;
        content = c;
    }



    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    public String getUserName(){
        return user;
    }

    public String getGenre(){
        return genre;
    }

    public String getContent(){
        return content;
    }


}