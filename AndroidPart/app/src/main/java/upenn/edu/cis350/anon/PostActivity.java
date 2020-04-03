package upenn.edu.cis350.anon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Post post = (Post) getIntent().getSerializableExtra("post");
        if (post != null) {
            TextView username = (TextView) findViewById(R.id.post_username);
            TextView genre = (TextView) findViewById(R.id.post_genre);
            TextView title = (TextView) findViewById(R.id.post_title);
            TextView content = (TextView) findViewById(R.id.post_content);
            username.setText(post.getUserName());
            genre.setText(post.getGenre());
            title.setText(post.getTitle());
            content.setText(post.getContent());
        } else {
            Toast.makeText(this, "Couldn't get post :(", Toast.LENGTH_LONG).show();
        }
    }
}
