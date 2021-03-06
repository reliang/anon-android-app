package upenn.edu.cis350.anon.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import upenn.edu.cis350.anon.Post;
import upenn.edu.cis350.anon.PostActivity;
import upenn.edu.cis350.anon.ProfileActivity;
import upenn.edu.cis350.anon.R;
import upenn.edu.cis350.anon.Reply;
import upenn.edu.cis350.anon.User;
import upenn.edu.cis350.anon.UserActivity;
import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;

public class NotificationsFragment extends Fragment {

    private User user = ((UserActivity) getActivity()).user;
    List<Post> postsWritten = user.getPostsWritten();
    List<User> followers = user.getFollowers();
    User unreadFollower;
    Reply unreadReply;

    public static transient Post unreadPost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        String [] values =
                {"Followers and Replies", "Replies"};
        Spinner spinner = (Spinner) view.findViewById(R.id.notifications_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typeOfNotification;
                CharSequence difficulty = (CharSequence) parent.getItemAtPosition(position);
                typeOfNotification = difficulty.toString();
                if (typeOfNotification.equals("Followers and Replies")) {
                    String displayFollowerAlias = "No one";
                    for (User follower : followers) {
                        if (!follower.getIsReadOrNot()) {
                            unreadFollower = follower;
                            displayFollowerAlias = follower.getAlias();
                        }
                    }
                    TextView follower_alias = getView().findViewById(R.id.follower_alias);
                    follower_alias.setText(displayFollowerAlias);

                    String replierAlias = "No one";
                    String replyContent = "";

                    if (postsWritten.size() == 0) {
                        TextView replier_alias = getView().findViewById(R.id.replier_alias);
                        replier_alias.setText(replierAlias);
                    } else {
                        replierAlias = "No one";
                        for (Post onePost: postsWritten) {
                            RemoteDataSource.populatePostReplies(onePost);
                            ArrayList<Reply> replies = onePost.getReplies();

                            if (onePost.getReplies() == null) {
                                replierAlias = "No One";
                                replyContent = "There're no replies of your posts. ";
                            } else {
                                for (Reply oneReply : replies) {
                                    if (!oneReply.getReadOrNot()) {
                                        unreadPost = onePost;
                                        unreadReply = oneReply;
                                        replierAlias = oneReply.getUsername();
                                        replyContent = oneReply.getContent();
                                    }
                                }
                            }
                        }
                        TextView replier_alias = getView().findViewById(R.id.replier_alias);
                        replier_alias.setText(replierAlias);
                        TextView reply_stuff = getView().findViewById(R.id.reply_stuff);
                        reply_stuff.setText(replyContent);
                    }
                } else {
                    TextView follower_alias = getView().findViewById(R.id.follower_alias);
                    follower_alias.setText("");
                    TextView tv = getView().findViewById(R.id.new_follower);
                    tv.setText("");
                    TextView textView = getView().findViewById(R.id.followed_you);
                    textView.setText("");
                    View button = getView().findViewById(R.id.see_profile_button);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button seePostButton = (Button) view.findViewById(R.id.see_post_button);
        seePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PostActivity.class);
                if (unreadReply == null) {
                    Toast.makeText(getActivity(), "You don't have unread posts", Toast.LENGTH_LONG).show();
                } else {
                    i.putExtra("post", unreadPost);
                    startActivity(i);
                }
            }
        });

        Button seeProfileButton = (Button) view.findViewById(R.id.see_profile_button);
        seeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                if (unreadFollower == null) {
                    Toast.makeText(getActivity(), "You don't have new followers", Toast.LENGTH_LONG).show();
                } else {
                    unreadFollower.setRead();
                    i.putExtra("user", unreadFollower);
                    startActivity(i);
                }
            }
        });

        return view;
    }
}
