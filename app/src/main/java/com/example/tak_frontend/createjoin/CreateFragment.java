package com.example.tak_frontend.createjoin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tak_frontend.R;


public class CreateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Button confirmHouseButton;
    private Button backToChoiceButton;

    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("createhouse");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_createhouse, container, false);

        confirmHouseButton = (Button) view.findViewById(R.id.acceptCreateButton);
        backToChoiceButton = (Button) view.findViewById(R.id.backCreateButton);

        confirmHouseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Log.i("confirmHouseButton", "Confirm House Name clicked");
                /*TODO: Bundle up the "editTextCreateHouse" string and send it to the backend.
                Along with all the other information it'll need to make a new house for this user.*/
            }

        });

        backToChoiceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View w)
            {
                Log.i("joinButton", "Back to Choice Button clicked");
                //TODO: Call the Create/Join Choice fragment (that is, return to previous screen.)
            }

        });
        return inflater.inflate(R.layout.fragment_createhouse, container, false);
    }
};