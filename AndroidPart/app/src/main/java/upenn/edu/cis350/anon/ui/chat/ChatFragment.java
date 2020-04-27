package upenn.edu.cis350.anon.ui.chat;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.w3c.dom.Text;

import upenn.edu.cis350.anon.MainActivity;
import upenn.edu.cis350.anon.Post;
import upenn.edu.cis350.anon.PostActivity;
import upenn.edu.cis350.anon.ProfileActivity;
import upenn.edu.cis350.anon.R;

import upenn.edu.cis350.anon.User;
import upenn.edu.cis350.anon.UserActivity;

public class ChatFragment extends Fragment {

    protected FragmentActivity mActivity;


    public static PostListAdapter adapter;


    public static String MyPREFERENCES = "nightModePrefs";
    public static String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences sharedPreferences;
    Switch darkModeSwitch;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Toggle Dark Mode

        checkNightModeActivated();

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CharSequence text = "Switched to dark mode";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    saveNightModeState(true);
                    getActivity().recreate();
                } else {
                    CharSequence text = "Switched to light mode";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    saveNightModeState(false);
                    getActivity().recreate();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        User user = ((UserActivity) getActivity()).user;

        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        darkModeSwitch = view.findViewById(R.id.modeSwitch);


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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mActivity = (FragmentActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    private void saveNightModeState(boolean nightMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);

        editor.apply();
    }

    private void checkNightModeActivated() {
        if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)) {
            darkModeSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            darkModeSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
}
