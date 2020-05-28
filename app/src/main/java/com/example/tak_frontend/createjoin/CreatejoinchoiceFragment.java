package com.example.tak_frontend.createjoin;

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


    private Bundle b;
    private Button createButton;
    private Button joinButton;
    private Button backButton;

    public CreatejoinchoiceFragment() {
        // Required empty public constructor
    }



    public static CreatejoinchoiceFragment newInstance(Bundle b) {
        CreatejoinchoiceFragment fragment = new CreatejoinchoiceFragment();
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = getArguments();
        getActivity().setTitle("Createjoinchoice");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_createjoinchoice, container, false);

        createButton = (Button) view.findViewById(R.id.createHouseholdButton);
        joinButton = (Button) view.findViewById(R.id.joinHouseholdButton);

        backButton = (Button) view.findViewById(R.id.backButton);


        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Log.d("createButton", "Create Button clicked");
                ((MainActivity) getActivity()).openFragment(CreateFragment.newInstance(b));
            }

        });

        joinButton.setOnClickListener(new View.OnClickListener()
        {

            @Override public void onClick(View v)
            {
                Log.d("joinButton", "Join Button clicked");
                ((MainActivity) getActivity()).openFragment(JoinFragment.newInstance(b));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                ((MainActivity) getActivity()).openFragment(ProfileFragment.newInstance(b));
            }

        });
        return view;
    }
};