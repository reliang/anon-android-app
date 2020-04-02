package upenn.edu.cis350.anon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Post implements Serializable {

    String userId, genreId;

    String title,date,username,genre,content;

    ArrayList<Reply> replies;

    public Post(String userId, String genreId,
                String title, String date, String username, String genre, String content)  {
        this.userId = userId;
        this.genreId = genreId;
        this.title = title;
        this.date = date;
        this.username = username;
        this.genre = genre;
        this.content = content;
    }

    public Post(String userId, String genreId,
                String title, String username, String genre, String content){
        this(userId, genreId, title, "", username, genre, content);
    }

    public Post (String username, String content) {
        this.username = username;
        this.content = content;
    }


    public String getUserId(){
        return userId;
    }
    public String getGenreId(){
        return genreId;
    }

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    public String getUserName(){
        return username;
    }

    public String getGenre(){
        return genre;
    }

    public String getContent(){
        return content;
    }

    public void setDate(Date date) {
        this.date = date.getMonth() + "/" + date.getDay() + "/" + date.getYear()
                + " " + date.getHours() + ":" + date.getMinutes();
    }

}