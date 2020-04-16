package upenn.edu.cis350.anon.ui.genre;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import upenn.edu.cis350.anon.Post;
import upenn.edu.cis350.anon.R;
import upenn.edu.cis350.anon.User;
import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;
import upenn.edu.cis350.anon.ui.home.PostAdapter;



public class GenrePostsFragment extends Fragment {



    static User user;
    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    static Post[] posts ;
    //String genreId;
    View cardView;

    protected RecyclerView.LayoutManager mLayoutManager;

    public GenrePostsFragment(View v){
         cardView = v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v("a", "oncreate genrepost fragment");
        super.onCreate(savedInstanceState);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("a", "oncreate view genrepost");

        // homeViewModel =
        // ViewModelProviders.of(this).get(HomeViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_genreposts, container, false);


        // BEGIN_INCLUDE(initializeRecyclerView)
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_postsInGenre);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);


        posts = RemoteDataSource.getPostsbyGenreId("5e86301f1c9d440000945f37");
        mAdapter = new PostAdapter(posts);
        recyclerView.setAdapter(mAdapter);

        fillPost(cardView);

        return rootView;
    }


    public static void fillPost(View v) {
        Log.v("fill","fill post in post fragment");
        String genreId = "";
        //ROOT VIEW IS GENRE FRAGMENT
        View genresRoot = v.getRootView();

        MaterialCardView violence  = (MaterialCardView)genresRoot.findViewById(R.id.card_violence);
        MaterialCardView greeklife  = (MaterialCardView)genresRoot.findViewById(R.id.card_greeklife);
        MaterialCardView hazing  = (MaterialCardView)genresRoot.findViewById(R.id.card_hazing);

        MaterialCardView sexualassult  = (MaterialCardView)genresRoot.findViewById(R.id.card_sexualassult);


        if(v == violence){
            Log.v("card","violence card");
            genreId = "5e86301f1c9d440000945f37";
        }else if(v == greeklife){
            genreId = "5e865a7cd2d2645eb8118735";
        }else if(v == hazing){
            genreId = "5e863b135595fb1a089ef03e";
        }else if(v == sexualassult){
            genreId = "5e854d357d58922d34b950ca";
        }

        posts = RemoteDataSource.getPostsbyGenreId(genreId);
        mAdapter = new PostAdapter(posts);
        recyclerView.setAdapter(mAdapter);
    }


/*
    public static void fillPost(View v) {
        Log.v("fill","fillpost in oist fragment");
        String genreId = "";
        //ROOT VIEW IS GENRE FRAGMENT
        View genresRoot = v.getRootView();

        MaterialCardView violence_card  = (MaterialCardView)genresRoot.findViewById(R.id.card_violence);

        if(v == violence_card){
            Log.v("card","violence card");

            genreId = "5e86301f1c9d440000945f37";
        }

        posts = RemoteDataSource.getPostsbyGenreId(genreId);
        mAdapter = new PostAdapter(posts);
        recyclerView.setAdapter(mAdapter);
    }*/

    private void test() {
        posts = new Post[5];
        for (int i = 0; i < 5; i++) {
            posts[i] = new Post("ANNE","content");
        }
    }


}