package upenn.edu.cis350.anon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;
import upenn.edu.cis350.anon.ui.chat.PostListAdapter;

public class ProfileActivity extends AppCompatActivity {

    User user;
    public static PostListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            // fill user with info
            String result = RemoteDataSource.populateUserProfile(user);
            // set username
            TextView username = (TextView) findViewById(R.id.profile_username);
            username.setText(user.getAlias());
            // set icon
            ImageView icon = (ImageView) findViewById(R.id.profile_icon);
            String iconLink = user.getIconLink();
            UserActivity.setIcon(iconLink, icon);
            // set following and follower number
            TextView following = (TextView) findViewById(R.id.profile_following);
            TextView followers = (TextView) findViewById(R.id.profile_followers);
            following.setText(Integer.toString(user.getNumFollowing()));
            followers.setText(Integer.toString(user.getNumFollowers()));
            // set post list
            ListView postlist = (ListView) findViewById(R.id.profile_post_list);
            adapter = new PostListAdapter(user.getPostsWritten());
            postlist.setAdapter(adapter);
            postlist.setOnItemClickListener(listClick);
        }
    }

    private AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener () {
        @Override
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent i = new Intent(parent.getContext(), PostActivity.class);
            Post postClicked = adapter.getItem( position );
            if (postClicked != null) {
                i.putExtra("post", postClicked);
                startActivity(i);
            }
        }

    };

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

    public void onFollowUserButtonClick(View v) {
        String status = RemoteDataSource.addFollowerByObjects(UserActivity.user, user);
        user.addFollower(UserActivity.user);
        TextView followers = (TextView) findViewById(R.id.profile_followers);
        followers.setText(Integer.toString(user.getNumFollowers()));
        Toast.makeText(this, status, Toast.LENGTH_LONG).show();
    }
}
