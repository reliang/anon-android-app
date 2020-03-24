package upenn.edu.cis350.anon;

import java.util.ArrayList;

public class Post {
    int userId;
    int postId;
    String content;
    ArrayList<Reply> replies;
    int genreId;
    int time; // replace with Java Time class
}