package upenn.edu.cis350.anon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginButtonClick(View v) {
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
        String status = RemoteDataSource.getUserByObject(newUser);
        if (status.equals("success")) {
            Intent i = new Intent(this, UserActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, status, Toast.LENGTH_LONG).show();
        }
    }
}
