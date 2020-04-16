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
    boolean banned;
    int contribution;
    // implement using LinkedHashSet
    Set<Integer> generesFollowed; // genere ids
    Set<Integer> postsFollowed; // post ids
    Set<Integer> postsWritten; // post ids
    Set<Integer> followers; // user ids

    public User(String alias, String password) {
        this.alias = alias;
        this.password = password;
        this.banned = false;
        this.generesFollowed = new HashSet<>();
        this.postsFollowed = new HashSet<>();
        this.postsWritten = new HashSet<>();
        this.followers = new HashSet<>();
    }

    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }

    public boolean isBanned() { return banned; }

    public void setUserId(String id) { userId = id; }
}