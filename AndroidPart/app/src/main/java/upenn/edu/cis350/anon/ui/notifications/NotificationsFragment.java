package upenn.edu.cis350.anon.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import upenn.edu.cis350.anon.R;

public class NotificationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final String typeOfNotification;
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        String [] values =
                {"Click to sort notifications", "New followers","New replies"};
        Spinner spinner = (Spinner) view.findViewById(R.id.notifications_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typeOfNotification;
                CharSequence difficulty = (CharSequence) parent.getItemAtPosition(position);
                typeOfNotification = difficulty.toString();
                if (typeOfNotification.equals("New followers")) {
                    TextView follower_alias = getView().findViewById(R.id.follower_alias);
                    follower_alias.setText("Someone");
                    TextView replier_alias = getView().findViewById(R.id.replier_alias);
                    replier_alias.setText(" ");
                    TextView reply_stuff = getView().findViewById(R.id.reply_stuff);
                    reply_stuff.setText(" ");
                } else {
                    TextView follower_alias = getView().findViewById(R.id.follower_alias);
                    follower_alias.setText(" ");
                    TextView replier_alias = getView().findViewById(R.id.replier_alias);
                    replier_alias.setText("Someone");
                    TextView reply_stuff = getView().findViewById(R.id.reply_stuff);
                    reply_stuff.setText("Lorem ipsuym phaowuefhawouehfou abgalngbvoewiugha oweuhfgoaurwhgoub");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button seePostButton = (Button) view.findViewById(R.id.see_post_button);
        seePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button seeProfileButton = (Button) view.findViewById(R.id.see_profile_button);
        seeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
