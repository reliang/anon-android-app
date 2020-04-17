package upenn.edu.cis350.anon;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Post implements Serializable {

    String userId, genreId, postId;
    String title,username, genre,content;
    Calendar date;

    ArrayList<Reply> replies;
    String iconLink;

    public Post(String userId, String genreId,
                String title, Calendar date, String username, String genre, String content)  {
        this.userId = userId;
        this.genreId = genreId;
        this.title = title;
        this.date = date;
        this.username = username;
        this.genre = genre;
        this.content = content;
        this.replies = new ArrayList<>();
    }


    public Post (String username, String content) {
        this.username = username;
        this.content = content;
        this.replies = new ArrayList<>();
    }


    public String getUserId(){
        return userId;
    }
    public String getGenreId(){
        return genreId;
    }

    public String getPostId(){
        return postId;
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

    public void addReply(Reply reply) { replies.add(reply); }

    public ArrayList<Reply> getReplies() {return replies;}

    public String getUserName(){
        return username;
    }

    public String getGenre(){
        return genre;
    }

    public String getContent(){
        return content;
    }

    public String getIconLink() { return iconLink; }

    public void setPostId(String id){
        this.postId = id;
    }

    public void setIconLink(String iconLink) { this.iconLink = iconLink; }
}