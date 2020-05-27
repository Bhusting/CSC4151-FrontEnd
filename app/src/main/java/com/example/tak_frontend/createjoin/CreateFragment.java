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
import com.example.tak_frontend.TakViewModel;
import com.example.tak_frontend.TakViewModelFactory;


public class CreateFragment extends Fragment {

    private Bundle b;
    private Button confirmHouseButton;
    private Button backToChoiceButton;

    private TakViewModel viewModel;

    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance(Bundle args) {
        CreateFragment fragment = new CreateFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("createhouse");
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
                //do viewmodel stuff
            }

        });

        backToChoiceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View w)
            {
                Log.i("joinButton", "Back to Choice Button clicked");
                ((MainActivity) getActivity()).openFragment(CreatejoinchoiceFragment.newInstance(b));
            }

        });
        return inflater.inflate(R.layout.fragment_createhouse, container, false);
    }
};