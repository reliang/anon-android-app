package upenn.edu.cis350.anon.ui.home;
import upenn.edu.cis350.anon.datamanagement.*;

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


public class HomeFragment extends Fragment {

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


        // BEGIN_INCLUDE(initializeRecyclerView)
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyler_posts);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        fillPost(ViewOption.GENRE);

        return rootView;
    }




    public static void fillPost(ViewOption option) {
        Log.v("fill","fillpost");
        switch(option) {
            case GENRE:
                posts = RemoteDataSource.getPostsbyUserGenre(user);
                break;
            case FALLOWED:
                posts = RemoteDataSource.getPostsbyUserFallowed(user);
                break;
        }

        mAdapter = new PostAdapter(posts);
        recyclerView.setAdapter(mAdapter);
    }

    private void test() {
        posts = new Post[5];
        for (int i = 0; i < 5; i++) {
            posts[i] = new Post("ANNE","content");
        }
    }


}