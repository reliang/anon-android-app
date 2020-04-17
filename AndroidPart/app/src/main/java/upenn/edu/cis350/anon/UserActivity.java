package upenn.edu.cis350.anon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;
import upenn.edu.cis350.anon.ui.chat.ChatFragment;
import upenn.edu.cis350.anon.ui.dashboard.DashBoardFragment;
import upenn.edu.cis350.anon.ui.genre.GenreFragment;
import upenn.edu.cis350.anon.ui.genre.GenrePostsFragment;
import upenn.edu.cis350.anon.ui.notifications.NotificationsFragment;

import upenn.edu.cis350.anon.ui.home.HomeFragment;

public class UserActivity extends AppCompatActivity {

    //private DrawerLayout drawerLayout;
    //private ActionBarDrawerToggle actionBarDrawerToggle;

    public static User user;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.side_drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        View hView =  navigationView.getHeaderView(0);

        user = (User) getIntent().getSerializableExtra("user");
        RemoteDataSource.populateUserProfile(user);

        selectedFragment = new ChatFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }


    public void onPostDemoClick(View v) {
        Intent i = new Intent(this, PostActivity.class);
        startActivityForResult(i, 0);
    }

    public void onMakePostDemoClick(View v) {
        Intent i = new Intent(this, MakePostActivity.class);
        i.putExtra("user", user);
        startActivityForResult(i, 1);
    }

    public void onLogoutButtonClick(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, 1);
    }

    public void onViewGenreButtonClick(View v) {
        HomeFragment.ViewOption opt = HomeFragment.ViewOption.GENRE;
        ((HomeFragment)selectedFragment).fillPost(opt);
    }

    public void onViewFallowButtonClick(View v) {
        HomeFragment.ViewOption opt = HomeFragment.ViewOption.FALLOWED;
        ((HomeFragment)selectedFragment).fillPost(opt);
    }

    public void showPostClicked(View v) {
        selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }

    public void genreClicked(View v) {
        Log.v("clicked","genrepost clicked");
        Fragment postFragment = new GenrePostsFragment(v);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                postFragment).commit();
        //GenrePostsFragment.fillPost(v);
    }

    public void onFollowingClick(View v) {
        Intent i = new Intent(this, UserListActivity.class);
        i.putExtra("following", true);
        i.putExtra("user", user);
        startActivity(i);
    }

    public void onFollowersClick(View v) {
        Intent i = new Intent(this, UserListActivity.class);
        i.putExtra("following", false);
        i.putExtra("user", user);
        startActivity(i);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_dashboard:
                            selectedFragment= new HomeFragment();
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

