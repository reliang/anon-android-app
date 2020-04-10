package upenn.edu.cis350.anon.ui.genre;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import upenn.edu.cis350.anon.R;
import upenn.edu.cis350.anon.datamanagement.RemoteDataSource;
import upenn.edu.cis350.anon.ui.home.HomeFragment;
import upenn.edu.cis350.anon.ui.home.PostAdapter;

public class GenreFragment extends Fragment {
    public View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v("genre","all genre create");


        rootView = inflater.inflate(R.layout.fragment_genre, container, false);

        /*
        MaterialCardView card1  = (MaterialCardView)rootView.findViewById(R.id.card1);
        //CardView card1  = (CardView)findViewById(R.id.card1);

        card1.setOnLongClickListener ( new View.OnLongClickListener() {
                                       @Override public boolean onLongClick(View v) {
                                           Log.v("a","genre clicked");
                                           return true;
                                       }
                                   }
        );*/

        return rootView;
    }




}
