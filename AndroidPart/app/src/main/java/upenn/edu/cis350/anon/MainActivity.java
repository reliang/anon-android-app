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
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    public void onPostDemoClick(View v) {
        Intent i = new Intent(this, PostActivity.class);
        startActivityForResult(i, 0);
    }

    public void onMakePostDemoClick(View v) {
        Intent i = new Intent(this, MakePostActivity.class);
        startActivityForResult(i, 1);
    }

    public void onLoginButtonClick(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, 2);
    }

    public void onSignupButtonClick(View v) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivityForResult(i, 3);
    }

    public void onViewGenreButtonClick(View v) {
        HomeFragment.ViewOption opt = HomeFragment.ViewOption.GENRE;
        HomeFragment.fillPost(opt);
    }

    public void onViewFallowButtonClick(View v) {
        HomeFragment.ViewOption opt = HomeFragment.ViewOption.FALLOWED;
        HomeFragment.fillPost(opt);
    }

    public void showPostClicked(View v) {
        Fragment selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_dashboard:
                            selectedFragment = new DashBoardFragment();
                            break;
                        case R.id.nav_chat:
                            selectedFragment = new ChatFragment();
                            break;
                        case R.id.nav_genre:
                            selectedFragment = new GenreFragment();
                            break;
                        case R.id.nav_notifications:
                            selectedFragment = new NotificationsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

}
