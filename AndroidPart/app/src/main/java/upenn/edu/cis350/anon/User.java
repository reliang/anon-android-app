package upenn.edu.cis350.anon;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    List<Post> postsWritten; // post ids
    List<User> following; // user ids
    List<User> followers; // user ids

    public User(String alias) {
        this(alias, null);
    }

    public User(String alias, String password) {
        this.alias = alias;
        this.password = password;
        this.banned = false;
        this.generesFollowed = new HashSet<>();
        this.postsFollowed = new HashSet<>();
        this.postsWritten = new ArrayList<>();
        this.following = new ArrayList<>();
        this.followers = new ArrayList<>();
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

    public boolean isBanned() { 
        return banned; 
    }

    public String getIconLink() {
        return iconLink;
    }

    public int getNumFollowers() {
        return followers.size();
    }

    public int getNumFollowing() {
        return following.size();
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public List<Post> getPostsWritten() { return postsWritten; }

    public void setUserId(String id) { userId = id; }

    public void setIconLink(String link) { iconLink = link; }

    public void setUserStatus(int stat) { status = stat; }

    public void setContribution(int contrib) { contribution = contrib; }

    public void addPostWritten(Post post) { postsWritten.add(post); }

    public void addFollowing(User user) { following.add(user); }

    public void addFollower(User user) { followers.add(user); }
}