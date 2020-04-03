package upenn.edu.cis350.anon;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Post implements Serializable {

    String userId, genreId;
    String title,username,genre,content;
    Calendar date;

    ArrayList<Reply> replies;

    public Post(String userId, String genreId,
                String title, Calendar date, String username, String genre, String content)  {
        this.userId = userId;
        this.genreId = genreId;
        this.title = title;
        this.date = date;
        this.username = username;
        this.genre = genre;
        this.content = content;
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

    public Calendar getDate(){
        return date;
    }

    public String getDateString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date.getTime());

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

}