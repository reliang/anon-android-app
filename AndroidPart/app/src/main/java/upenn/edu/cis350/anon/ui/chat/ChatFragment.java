package upenn.edu.cis350.anon.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import upenn.edu.cis350.anon.Post;
import upenn.edu.cis350.anon.PostActivity;
import upenn.edu.cis350.anon.ProfileActivity;
import upenn.edu.cis350.anon.R;

import upenn.edu.cis350.anon.User;
import upenn.edu.cis350.anon.UserActivity;

public class ChatFragment extends Fragment {

    public static PostListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        User user = ((UserActivity) getActivity()).user;
        if (user != null) {
            // set username
            TextView username = (TextView) view.findViewById(R.id.profile_username);
            username.setText(user.getAlias());
            // set contribution point
            TextView contribution = (TextView) view.findViewById(R.id.contribution_point);
            int contributionPoint = user.getNumFollowers() * 10 + user.getNumPostsFollowed() + user.getNumPostsWritten() * 5;
            user.setContribution(contributionPoint);
            contribution.setText(Integer.toString(user.getContribution()));

            // set icon
            ImageView icon = (ImageView) view.findViewById(R.id.profile_icon);
            String iconLink = user.getIconLink();
            UserActivity.setIcon(iconLink, icon);
            // set following and follower number
            TextView following = (TextView) view.findViewById(R.id.profile_following);
            TextView followers = (TextView) view.findViewById(R.id.profile_followers);
            following.setText(Integer.toString(user.getNumFollowing()));
            followers.setText(Integer.toString(user.getNumFollowers()));
            // set post list
            ListView postlist = (ListView) view.findViewById(R.id.profile_post_list);
            adapter = new PostListAdapter(user.getPostsWritten());
            postlist.setAdapter(adapter);
            postlist.setOnItemClickListener(listClick);
        }
        return view;
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
}
