package upenn.edu.cis350.anon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;
import upenn.edu.cis350.anon.ui.chat.PostListAdapter;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            // set username
            TextView username = (TextView) findViewById(R.id.profile_username);
            username.setText(user.getAlias());
            // set icon
            ImageView icon = (ImageView) findViewById(R.id.profile_icon);
            String iconLink = user.getIconLink();
            if (iconLink != null) {
                switch (iconLink) {
                    case "Cat":
                        icon.setImageResource(R.drawable.cat);
                        break;
                    case "Dog":
                        icon.setImageResource(R.drawable.dog);
                        break;
                    case "Fish":
                        icon.setImageResource(R.drawable.fish);
                        break;
                    case "Turtle":
                        icon.setImageResource(R.drawable.turtle);
                        break;
                    case "Parrot":
                        icon.setImageResource(R.drawable.parrot);
                        break;
                    default:
                }
            }
            // fill user with post writter/following/follower info
            String result = RemoteDataSource.populateUserProfile(user);
            if (result.equals("success")) {
                // set following and follower number
                TextView following = (TextView) findViewById(R.id.profile_following);
                TextView followers = (TextView) findViewById(R.id.profile_followers);
                following.setText(Integer.toString(user.getNumFollowing()));
                followers.setText(Integer.toString(user.getNumFollowers()));
                // set post list
                ListView postlist = (ListView) findViewById(R.id.profile_post_list);
                PostListAdapter adapter = new PostListAdapter(user.getPostsWritten());
                postlist.setAdapter(adapter);
            }

        }
    }

    public void onFollowUserButtonClick(View v) {

    }
}
