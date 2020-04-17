package upenn.edu.cis350.anon.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import upenn.edu.cis350.anon.Post;
import upenn.edu.cis350.anon.R;
import upenn.edu.cis350.anon.User;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private Post[] mDataset;
    private OnPostListener onPostListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView genre,date,user,content,title;
        OnPostListener onPostListener;

        public MyViewHolder(View v, OnPostListener onPostListener) {
            super(v);
            title = (TextView) v.findViewById(R.id.post_title2);
            date = (TextView) v.findViewById(R.id.post_date2);
            user = (TextView) v.findViewById(R.id.post_username2 );
            genre = (TextView) v.findViewById(R.id.post_genre2);
            content = (TextView) v.findViewById(R.id.post_content2);
            this.onPostListener = onPostListener;
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public PostAdapter(Post[] myDataset, OnPostListener onPostListener) {

        this.onPostListener = onPostListener;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, onPostListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){

        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Post post = mDataset[position];

        //holder.user is a view
        holder.title.setText(post.getTitle());
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        holder.date.setText(dateFormat.format(post.getDate().getTime()));
        holder.user.setText(post.getUserName());
        holder.genre.setText(post.getGenre());
        holder.content.setText(post.getContent());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }


}