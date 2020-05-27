package com.example.tak_frontend.createjoin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tak_frontend.R;


public class JoinFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Button confirmJoinButton;
    private Button backToChoiceButton;

    public JoinFragment() {
        // Required empty public constructor
    }

    public static JoinFragment newInstance(String param1, String param2) {
        JoinFragment fragment = new JoinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("joinhouse");
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

        backToChoiceButton = (Button) view.findViewById(R.id.backJoinButton);
        confirmJoinButton = (Button) view.findViewById(R.id.joinButton);

        confirmJoinButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Log.i("joinButton", "Join Button clicked");
                //TODO: Bundle up the input string and send it to the backend.
                //TODO: IF the string is VALID, plop the user in their new profile page.
                //TODO: If string is INVALID, let them know and STAY IN THIS FRAGMENT so they can try again if they want.
            }

        });

        backToChoiceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View w)
            {
                Log.i("BackToChoice", "BacktoChoice Button clicked");
                //TODO: Return to the choice fragment.
            }

        });
        return inflater.inflate(R.layout.fragment_joinhouse, container, false);
    }
};