package upenn.edu.cis350.anon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;

public class MakePostActivity extends AppCompatActivity {

    private String genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);

        genre = "Sexual Assault";

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
        String title, content;
        title  = ((EditText) findViewById(R.id.make_post_title)).getText().toString();
        content = ((EditText) findViewById(R.id.make_post_content)).getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this, "Make a title!", Toast.LENGTH_LONG).show();
            return;
        } else if (content.isEmpty()) {
            Toast.makeText(this, "Write something!", Toast.LENGTH_LONG).show();
            return;
        }
        Post newPost = new Post("5e854df97d58922d34b950cb", // CHANGE TO GET USERID
                "5e863b135595fb1a089ef03e",
                title,
                "abc",
                "Hazing",
                content
                );
        String status = RemoteDataSource.addPostbyObject(newPost);
        if (status.equals("Success")) {
            Intent i = new Intent(this, PostActivity.class);
            i.putExtra("post", newPost);
            startActivity(i);
        } else {
            Toast.makeText(this, status, Toast.LENGTH_LONG).show();
        }
        /*
        if (status) {
            Toast.makeText(this, "Post created", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Failed to make post", Toast.LENGTH_LONG).show();
        }
        */
    }


}