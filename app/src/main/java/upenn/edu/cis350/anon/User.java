public class User {
    String alias;
    int userId;
    String password;
    String iconLink; // base64 string or link?
    int status; // 0 = user, 1 = admin, 2 = head admin
    int contribution;
    // implement using LinkedHashSet
    Set<int> generesFollowed; // genere ids
    Set<int> postsFollowed; // post ids
    Set<int> postsWritten; // post ids
    Set<int> followers; // user ids
}