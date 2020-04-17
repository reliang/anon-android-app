package upenn.edu.cis350.anon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;
import upenn.edu.cis350.anon.ui.chat.ChatFragment;
import upenn.edu.cis350.anon.ui.chat.PostListAdapter;

public class MakePostActivity extends AppCompatActivity {

    private String genre;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);

        genre = "Sexual Assault";
        user = (User) getIntent().getSerializableExtra("user");

        Spinner spinner = (Spinner) findViewById(R.id.genre_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genre_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set spinner's listener
        //spinner.setOnItemSelectedListener(this);
    }

    public void onPostButtonClick(View v) {

        String userId, genreId, title, content, username, genre;
        userId = user.getUserId();
        genreId = "5e863b135595fb1a089ef03e"; // CHANGE TO CURRENT GENRE'S
        username = user.getAlias();
        genre = "Hazing"; // CHANGE TO CURRENT GENRE'S
        title  = ((EditText) findViewById(R.id.make_post_title)).getText().toString();
        content = ((EditText) findViewById(R.id.make_post_content)).getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Make a title!", Toast.LENGTH_LONG).show();
            return;
        } else if (content.isEmpty()) {
            Toast.makeText(this, "Write something!", Toast.LENGTH_LONG).show();
            return;
        }
        Post newPost = new Post(
                userId,
                genreId,
                title,
                new GregorianCalendar(),
                username,
                genre,
                content
                );
        String status = RemoteDataSource.addPostbyObject(newPost);
        if (status.equals("Success")) {
            // add post to current user's post written
            user.addPostWritten(newPost);
            // refresh user profile's post list (not working)
            final PostListAdapter adapter = ChatFragment.adapter;
            adapter.addPost(newPost);
            runOnUiThread(new Runnable() {
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
            Intent i = new Intent(this, PostActivity.class);
            i.putExtra("post", newPost);
            startActivity(i);
        } else {
            Toast.makeText(this, status, Toast.LENGTH_LONG).show();
        }
    }


}