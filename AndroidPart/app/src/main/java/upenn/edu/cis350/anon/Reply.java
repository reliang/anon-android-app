package upenn.edu.cis350.anon;

import java.util.Calendar;

public class Reply {
    String postId;
    String userId;
    String username;
    String content;
    Calendar date;

    public Reply(String postId, String userId, String username, String content, Calendar date) {
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.date = date;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public Calendar getDate() {
        return date;
    }
}