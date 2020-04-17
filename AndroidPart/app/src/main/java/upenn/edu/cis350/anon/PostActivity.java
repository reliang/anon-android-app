package upenn.edu.cis350.anon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;

public class PostActivity extends AppCompatActivity {
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        post = (Post) getIntent().getSerializableExtra("post");
        if (post != null) {
            TextView username = (TextView) findViewById(R.id.post_username);
            TextView genre = (TextView) findViewById(R.id.post_genre);
            TextView title = (TextView) findViewById(R.id.post_title);
            TextView content = (TextView) findViewById(R.id.post_content);
            TextView date = (TextView) findViewById(R.id.post_date);
            username.setText(post.getUserName());
            genre.setText(post.getGenre());
            title.setText(post.getTitle());
            content.setText(post.getContent());
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            date.setText(dateFormat.format(post.getDate().getTime()));
        } else {
            Toast.makeText(this, "Couldn't get post :(", Toast.LENGTH_LONG).show();
        }
    }

    public void onReplyButtonClick(View v) {
        if (post == null) {
            Toast.makeText(this, "Couldn't get post :(", Toast.LENGTH_LONG).show();
            return;
        }

        User user = UserActivity.user;
        String postId, userId, username, content;
        Calendar date;
        postId = post.getPostId();
        userId = user.getUserId();
        username = user.getAlias();
        content = ((TextView) findViewById(R.id.reply_content)).getText().toString();
        date = new GregorianCalendar();

        Reply newReply = new Reply(postId, userId, username, content, date);
        String status = RemoteDataSource.addReplybyObject(newReply);
        if (status.equals("Success")) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.post_layout);

            TextView username_view = new TextView(this);
            LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 45, 0, 0);
            username_view.setLayoutParams(params);
            username_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
            username_view.setText(username);
            layout.addView(username_view);

            TextView date_view = new TextView(this);
            date_view.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            date_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            date_view.setText(dateFormat.format(date.getTime()));
            layout.addView(date_view);

            TextView content_view = new TextView(this);
            content_view.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            content_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            content_view.setText(content);
            layout.addView(content_view);

            // clear text

            Toast.makeText(this, status, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, status, Toast.LENGTH_LONG).show();
        }
    }

    public void onUsernameClick(View v) {
        Intent i = new Intent(this, ProfileActivity.class);
        User user = new User(post.getUserName());
        String status = RemoteDataSource.getUserByObject(user);
        if (status.equals("success")) {
            i.putExtra("user", user);
            startActivity(i);
        }
    }
}
