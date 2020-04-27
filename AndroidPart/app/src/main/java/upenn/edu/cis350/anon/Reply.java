package upenn.edu.cis350.anon;

import java.io.Serializable;
import java.util.Calendar;

public class Reply implements Serializable {
    String postId;
    String userId;
    String username;
    String iconLink;
    String content;
    boolean read_in_notifications;
    Calendar date;

    public Reply(String postId, String userId, String username, String iconLink, String content, Calendar date) {
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.iconLink = iconLink;
        this.content = content;
        this.date = date;
        read_in_notifications = false;
    }

    public void readReply() {
        read_in_notifications = true;
    }

    public boolean getReadOrNot() { return read_in_notifications; }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getIconLink() {
        return iconLink;
    }

    public String getContent() {
        return content;
    }

    public Calendar getDate() {
        return date;
    }
}