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


public class CreatejoinchoiceFragment extends Fragment {

    private Bundle b;
    private Button createButton;
    private Button joinButton;
    private Button altSigninButton; //If the user wants to be someone else

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
        altSigninButton = (Button) view.findViewById(R.id.altSignInButton);
        //TODO: Replace the "Mr. Whiskers" string with the user's actual name or email.

        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Log.i("createButton", "Create Button clicked");
                ((MainActivity) getActivity()).openFragment(CreateFragment.newInstance(b));
            }

        });

        joinButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View w)
            {
                Log.i("joinButton", "Join Button clicked");
                ((MainActivity) getActivity()).openFragment(JoinFragment.newInstance(b));
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