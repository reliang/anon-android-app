package upenn.edu.cis350.anon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import upenn.edu.cis350.anon.ui.chat.UserListAdapter;

public class UserListActivity extends AppCompatActivity {

    UserListAdapter adapter;
    ListView userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        boolean following = getIntent().getBooleanExtra("following", true);
        User user = (User) getIntent().getSerializableExtra("user");
        // set user list
        userlist = (ListView) findViewById(R.id.user_list);
        if (following) {
            adapter = new UserListAdapter(user.getFollowing());
        } else {
            adapter = new UserListAdapter(user.getFollowers());
        }
        userlist.setAdapter(adapter);
        userlist.setOnItemClickListener(listClick);
    }

    private AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener () {
        @Override
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
            User userClicked = adapter.getItem( position );
            if (userClicked != null) {
                i.putExtra("user", userClicked);
                startActivity(i);
            }

        }

    };
}
