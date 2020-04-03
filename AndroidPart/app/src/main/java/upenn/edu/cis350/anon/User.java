package upenn.edu.cis350.anon;

import java.io.Serializable;
import java.util.Set;

public class User implements Serializable {
    String alias;
    int userId;
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
    }

    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }
}