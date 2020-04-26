package upenn.edu.cis350.anon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;
import upenn.edu.cis350.anon.ui.genre.GenrePostsFragment;

public class PostActivity extends AppCompatActivity {
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //user = (User) (getIntent().getExtras()).getSerializable("user");
        post = (Post) (getIntent().getExtras()).getSerializable("post");
        //user = (User) getIntent().getSerializableExtra("user");
        //post = (Post) getIntent().getSerializableExtra("post");
        //Log.v("a", "on create get user: "+user.getUserId());

        if (post == null) {
            Toast.makeText(this, "Couldn't get post :(", Toast.LENGTH_LONG).show();
            return;
        }

        String status = RemoteDataSource.populatePostInfo(post);

        TextView username = (TextView) findViewById(R.id.post_username);
        TextView genre = (TextView) findViewById(R.id.post_genre);
        TextView title = (TextView) findViewById(R.id.post_title);
        TextView content = (TextView) findViewById(R.id.post_content);
        TextView date = (TextView) findViewById(R.id.post_date);
        ImageView icon = (ImageView) findViewById(R.id.post_icon);
        TextView likes = (TextView) findViewById(R.id.likeAmount);



        username.setText(post.getUserName());
        genre.setText(post.getGenre());
        title.setText(post.getTitle());
        content.setText(post.getContent());
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        date.setText(dateFormat.format(post.getDate().getTime()));
        String iconLink = post.getIconLink();
        UserActivity.setIcon(iconLink, icon);
        likes.setText(""+post.likes);

        ArrayList<Reply> replies = post.getReplies();
        for (int i = 0; i < replies.size(); i++) {
            Reply reply = replies.get(i);
            // construct reply
            LinearLayout layout = (LinearLayout) findViewById(R.id.post_layout);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.reply_item, layout, false);

            String replyIcon = reply.getIconLink();
            Calendar replyDate = reply.getDate();
            final String replyUsername = reply.getUsername();
            String replyContent = reply.getContent();

            ImageView iconView = (ImageView) view.findViewById(R.id.reply_icon);
            TextView dateView = (TextView) view.findViewById(R.id.reply_date);
            TextView usernameView = (TextView) view.findViewById(R.id.reply_username);
            TextView contentView = (TextView) view.findViewById(R.id.reply_text);

            UserActivity.setIcon(replyIcon, iconView);
            dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            dateView.setText(dateFormat.format(replyDate.getTime()));
            usernameView.setText(replyUsername);
            usernameView.setOnClickListener(new TextView.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                    User user = new User(replyUsername);
                    i.putExtra("user", user);
                    startActivity(i);
                }
            });
            contentView.setText(replyContent);

            layout.addView(view);
        }
    }

    public void onReplyButtonClick(View v) {
        if (post == null) {
            Toast.makeText(this, "Couldn't get post :(", Toast.LENGTH_LONG).show();
            return;
        }

        User user = UserActivity.user;
        String postId, userId, iconLink, content;
        Calendar date;
        postId = post.getPostId();
        userId = user.getUserId();
        final String username = user.getAlias();
        iconLink = user.getIconLink();
        content = ((TextView) findViewById(R.id.reply_content)).getText().toString();
        date = new GregorianCalendar();

        Reply newReply = new Reply(postId, userId, username, iconLink, content, date);
        String status = RemoteDataSource.addReplybyObject(newReply);
        if (status.equals("Success")) {
            // construct reply
            LinearLayout layout = (LinearLayout) findViewById(R.id.post_layout);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.reply_item, layout, false);

            ImageView iconView = (ImageView) view.findViewById(R.id.reply_icon);
            TextView dateView = (TextView) view.findViewById(R.id.reply_date);
            TextView usernameView = (TextView) view.findViewById(R.id.reply_username);
            TextView contentView = (TextView) view.findViewById(R.id.reply_text);

            UserActivity.setIcon(iconLink, iconView);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            dateView.setText(dateFormat.format(date.getTime()));
            usernameView.setText(username);
            usernameView.setOnClickListener(new TextView.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                    User user = new User(username);
                    i.putExtra("user", user);
                    startActivity(i);
                }
            });
            contentView.setText(content);

            layout.addView(view);

            // clear text

            Toast.makeText(this, status, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, status, Toast.LENGTH_LONG).show();
        }
    }

    public void onUsernameClick(View v) {
        Intent i = new Intent(this, ProfileActivity.class);
        User user = new User(post.getUserName());
        i.putExtra("user", user);
        startActivity(i);
    }

    public void onLikeClicked(View v) {
        String postId =post.postId;
        User user = UserActivity.user;
        RemoteDataSource.addLike(user,postId);

        TextView likes = (TextView) findViewById(R.id.likeAmount);
        likes.setText(""+(post.likes+1));
    }
}
