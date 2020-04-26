package upenn.edu.cis350.anon.ui.home;
import upenn.edu.cis350.anon.PostActivity;
import upenn.edu.cis350.anon.UserActivity;
import upenn.edu.cis350.anon.datamanagement.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import upenn.edu.cis350.anon.Post;
import upenn.edu.cis350.anon.R;
import upenn.edu.cis350.anon.User;


public class HomeFragment extends Fragment implements PostAdapter.OnPostListener{

    @Override
    public void onPostClick(int position) {
        Log.v("a", "post clicked");
        Intent i = new Intent(getActivity(), PostActivity.class);
/*
        Bundle extras = new Bundle();
        extras.putSerializable("post", posts[position]);
        extras.putSerializable("user", user);
        Log.v("a", "post clicked user id: "+user.getUserId());
        i.putExtras(extras);*/
        i.putExtra("post", posts[position]);

        startActivity(i);
    }

    public enum ViewOption{
        GENRE,
        FALLOWED
    }

    static User user;
    static ViewOption viewOp = ViewOption.GENRE;
    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;

    static Post[] posts ;

    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v("a", "oncreate home fragment");
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("a", "oncreate view");

       // homeViewModel =
               // ViewModelProviders.of(this).get(HomeViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        user = ((UserActivity)getActivity()).user;

        // BEGIN_INCLUDE(initializeRecyclerView)
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyler_posts);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        fillPost(ViewOption.GENRE);

        return rootView;
    }




    public void fillPost(ViewOption option) {
        Log.v("fill","fillpost");
        switch(option) {
            case GENRE:
                posts = RemoteDataSource.getPostsbyUserGenre(user);
                break;
            case FALLOWED:
                posts = RemoteDataSource.getPostsbyUserFallowed(user);
                break;
        }

        mAdapter = new PostAdapter(posts,this);
        recyclerView.setAdapter(mAdapter);
    }




}