package com.example.tak_frontend.createjoin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.TakViewModel;
import com.example.tak_frontend.MVVM.TakViewModelFactory;



public class JoinFragment extends Fragment {

    private Button confirmJoinButton;
    private Button backToChoiceButton;
    private Bundle b;

    private TakViewModel viewModel;


    public JoinFragment() {
        // Required empty public constructor
    }

    public static JoinFragment newInstance(Bundle args) {
        JoinFragment fragment = new JoinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("joinhouse");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        b = getArguments();
        viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity().getApplication(), b))
                .get(TakViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_joinhouse, container, false);


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

                ((MainActivity) getActivity()).openFragment(CreatejoinchoiceFragment.newInstance(b));
            }

        });
        return view;

    }
};