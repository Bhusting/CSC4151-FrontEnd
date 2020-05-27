package com.example.tak_frontend.createjoin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tak_frontend.R;


public class CreatejoinchoiceFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button createButton;
    private Button joinButton;
    private Button altSigninButton; //If the user wants to be someone else

    public CreatejoinchoiceFragment() {
        // Required empty public constructor
    }



    public static CreatejoinchoiceFragment newInstance(String param1, String param2) {
        CreatejoinchoiceFragment fragment = new CreatejoinchoiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Createjoinchoice");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_createjoinchoice, container, false);

        createButton = (Button) view.findViewById(R.id.createHouseholdButton);
        joinButton = (Button) view.findViewById(R.id.joinHouseholdButton);
        altSigninButton = (Button) view.findViewById(R.id.altSignInButton);
        //TODO: Replace the "Mr. Whiskers" string with the user's actual name or email.

        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Log.i("createButton", "Create Button clicked");
                //TODO: Call the create house fragment.
            }

        });

        joinButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View w)
            {
                Log.i("joinButton", "Join Button clicked");
                //TODO: Call the join house fragment.
            }

        });

        altSigninButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Log.i("altSigninButton", "altSignin Button clicked");
                //TODO: Back out to Auth0 and get a NEW signin token. If none taken, don't sign out.
            }

        });

        return view;
    }
};