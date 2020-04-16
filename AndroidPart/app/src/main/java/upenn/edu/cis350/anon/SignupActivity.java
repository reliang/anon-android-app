package upenn.edu.cis350.anon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        icon = "Cat";

        Spinner spinner = (Spinner) findViewById(R.id.avatar_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.avatar_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set spinner's listener
        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        icon = (String) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
        newUser.setIconLink(icon);
        String status = RemoteDataSource.addUserByObject(newUser);
        if (status.equals("success")) {
            Intent i = new Intent(this, UserActivity.class);
            i.putExtra("user", newUser);
            startActivity(i);
        } else {
            Toast.makeText(this, "existing username", Toast.LENGTH_LONG).show();
        }
    }
}
