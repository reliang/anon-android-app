package upenn.edu.cis350.anon.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import upenn.edu.cis350.anon.R;
import upenn.edu.cis350.anon.User;
import upenn.edu.cis350.anon.UserActivity;

public class ChatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        User user = ((UserActivity) getActivity()).user;
        if (user != null) {
            TextView username = (TextView) view.findViewById(R.id.profile_username);
            username.setText(user.getAlias());
            ImageView icon = (ImageView) view.findViewById(R.id.profile_icon);
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
        }

        return view;
    }
}
