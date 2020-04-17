package upenn.edu.cis350.anon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.GregorianCalendar;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;
import upenn.edu.cis350.anon.ui.chat.ChatFragment;
import upenn.edu.cis350.anon.ui.chat.PostListAdapter;

public class FeedbackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    public void onSubmitButtonClick(View v) {
        String content = ((EditText) findViewById(R.id.content)).getText().toString();

        if (content.isEmpty()) {
            Toast.makeText(this, "Write something!", Toast.LENGTH_LONG).show();
            return;
        }

        Feedback newFeedback = new Feedback(new GregorianCalendar(), content);

        String status = RemoteDataSource.addFeedbackbyObject(newFeedback);


        if (status.equals("Success")) {
            Toast.makeText(this, "Your feedback is received", Toast.LENGTH_LONG).show();
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
        }
    }
}