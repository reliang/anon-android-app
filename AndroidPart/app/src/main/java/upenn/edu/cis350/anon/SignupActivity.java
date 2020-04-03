package upenn.edu.cis350.anon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Spinner spinner = (Spinner) findViewById(R.id.avatar_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.avatar_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }


    public void onSignupButtonClick(View v) {
        String username = ((EditText) findViewById(R.id.username_view)).getText().toString();
        String password = ((EditText) findViewById(R.id.password_view)).getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username Missing!", Toast.LENGTH_LONG).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password Missing!", Toast.LENGTH_LONG).show();
            return;
        }

        User newUser = new User(username, password);
        String status = RemoteDataSource.addUserByObject(newUser);
        if (status.equals("success")) {
            Intent i = new Intent(this, UserActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "existing username", Toast.LENGTH_LONG).show();
        }
    }
}
