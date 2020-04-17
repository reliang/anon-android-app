package upenn.edu.cis350.anon.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import upenn.edu.cis350.anon.Post;
import upenn.edu.cis350.anon.R;

public class PostListAdapter extends BaseAdapter {

    private List<Post> posts;

    public PostListAdapter(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post p) {
        posts.add(p);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int position) {
        return posts.get(posts.size() - 1 - position);
    }

    @Override
    public long getItemId(int position) {
        return posts.size() - 1 - position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.post_item, parent, false);
        }
        Post post = getItem(position);

        TextView title = (TextView) convertView.findViewById(R.id.post_title2);
        TextView date = (TextView) convertView.findViewById(R.id.post_date2);
        TextView user = (TextView) convertView.findViewById(R.id.post_username2 );
        TextView genre = (TextView) convertView.findViewById(R.id.post_genre2);
        TextView content = (TextView) convertView.findViewById(R.id.post_content2);
        title.setText(post.getTitle());
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        date.setText(dateFormat.format(post.getDate().getTime()));
        user.setText(post.getUserName());
        genre.setText(post.getGenre());
        content.setText(post.getContent());
        return convertView;
    }
}
