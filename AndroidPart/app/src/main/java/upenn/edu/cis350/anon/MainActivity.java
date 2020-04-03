package upenn.edu.cis350.anon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import upenn.edu.cis350.anon.ui.chat.ChatFragment;
import upenn.edu.cis350.anon.ui.dashboard.DashBoardFragment;
import upenn.edu.cis350.anon.ui.genre.GenreFragment;
import upenn.edu.cis350.anon.ui.notifications.NotificationsFragment;

import upenn.edu.cis350.anon.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginButtonClick(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, 2);
    }

    public void onSignupButtonClick(View v) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivityForResult(i, 3);
    }

}
