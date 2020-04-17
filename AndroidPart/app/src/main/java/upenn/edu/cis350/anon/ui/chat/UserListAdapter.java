package upenn.edu.cis350.anon.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import upenn.edu.cis350.anon.Post;
import upenn.edu.cis350.anon.R;
import upenn.edu.cis350.anon.User;

public class UserListAdapter extends BaseAdapter {

    private List<User> users;

    public UserListAdapter(List<User> users) {
        this.users = users;
    }

    public void addUser(User u) {
        users.add(u);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(users.size() - 1 - position);
    }

    @Override
    public long getItemId(int position) {
        return users.size() - 1 - position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_item, parent, false);
        }
        User user = getItem(position);

        ImageView icon = (ImageView) convertView.findViewById(R.id.user_list_icon);
        TextView username = (TextView) convertView.findViewById(R.id.user_list_username );
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
        username.setText(user.getAlias());

        return convertView;
    }
}
