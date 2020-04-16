package upenn.edu.cis350.anon;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
    String alias;
    String userId;
    String password;
    String iconLink; // base64 string or link?
    int status; // 0 = user, 1 = admin, 2 = head admin
    int contribution;
    // implement using LinkedHashSet
    Set<Integer> generesFollowed; // genere ids
    Set<Integer> postsFollowed; // post ids
    Set<Integer> postsWritten; // post ids
    Set<Integer> followers; // user ids

    public User(String alias, String password) {
        this.alias = alias;
        this.password = password;
        this.generesFollowed = new HashSet<>();
        this.postsFollowed = new HashSet<>();
        this.postsWritten = new HashSet<>();
        this.followers = new HashSet<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setUserId(String id) { userId = id; }

    public void setIconLink(String link) { iconLink = link; }

    public void setUserStatus(int stat) { status = stat; }

    public void setContribution(int contrib) { contribution = contrib; }
}