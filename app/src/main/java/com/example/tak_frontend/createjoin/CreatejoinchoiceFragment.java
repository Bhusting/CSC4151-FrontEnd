package com.example.tak_frontend.createjoin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.profile.ProfileFragment;



public class CreatejoinchoiceFragment extends Fragment {

    private Button createButton;
    private Button joinButton;
    private Button backButton;

    public CreatejoinchoiceFragment() {
        // Required empty public constructor
    }

    public static CreatejoinchoiceFragment newInstance() {
        CreatejoinchoiceFragment fragment = new CreatejoinchoiceFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("House?");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_createjoinchoice, container, false);

        createButton = (Button) view.findViewById(R.id.createHouseholdButton);
        joinButton = (Button) view.findViewById(R.id.joinHouseholdButton);

        backButton = (Button) view.findViewById(R.id.backButton);


        createButton.setOnClickListener(v -> {
            Log.d("createButton", "Create Button clicked");
            ((MainActivity) getActivity())
                    .openFragment(CreateFragment.newInstance());
        });

        joinButton.setOnClickListener(v -> {
            Log.d("joinButton", "Join Button clicked");
            ((MainActivity) getActivity())
                    .openFragment(JoinFragment.newInstance());
        });

        backButton.setOnClickListener(v -> ((MainActivity) getActivity())
                    .openFragment(ProfileFragment.newInstance()));
        return view;
    }
};