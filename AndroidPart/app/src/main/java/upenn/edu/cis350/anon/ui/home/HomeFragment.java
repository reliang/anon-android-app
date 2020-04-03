package upenn.edu.cis350.anon.ui.home;
import upenn.edu.cis350.anon.datamanagement.*;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.RemoteCallbackList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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


   // private HomeViewModel homeViewModel;

    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    static Post[] posts ;

    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;
    protected RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v("a", "oncreate home fragment");
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.

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

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;



        /*
        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }*/

       // setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        fillPost(ViewOption.GENRE);


        //mAdapter = new PostAdapter(posts);
        // Set CustomAdapter as the adapter for RecyclerView.
        //recyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)


        /*
        mLinearLayoutRadioButton = (RadioButton) rootView.findViewById(R.id.linear_layout_rb);
        mLinearLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
            }
        });

        mGridLayoutRadioButton = (RadioButton) rootView.findViewById(R.id.grid_layout_rb);
        mGridLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
            }
        });*/

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
        // Set CustomAdapter as the adapter for RecyclerView.
        recyclerView.setAdapter(mAdapter);
    }

    private void test() {
        posts = new Post[5];
        for (int i = 0; i < 5; i++) {
            posts[i] = new Post("ANNE","content");
        }
    }


}