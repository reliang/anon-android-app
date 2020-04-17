package upenn.edu.cis350.anon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import upenn.edu.cis350.anon.ui.chat.PostListAdapter;
import upenn.edu.cis350.anon.ui.chat.UserListAdapter;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        boolean following = getIntent().getBooleanExtra("following", true);
        User user = (User) getIntent().getSerializableExtra("user");
        // set user list
        ListView userlist = (ListView) findViewById(R.id.user_list);
        UserListAdapter adapter;
        if (following) {
            adapter = new UserListAdapter(user.getFollowing());
        } else {
            adapter = new UserListAdapter(user.getFollowers());
        }
        userlist.setAdapter(adapter);
    }
}
