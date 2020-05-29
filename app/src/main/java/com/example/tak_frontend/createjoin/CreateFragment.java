package com.example.tak_frontend.createjoin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.TakViewModel;
import com.example.tak_frontend.MVVM.TakViewModelFactory;



public class CreateFragment extends Fragment {


    private static final String TAG = ".CreateFragment";
    private Bundle b;
    private Button confirmHouseButton;
    private Button backToChoiceButton;
    private TextView houseName;

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
        houseName = view.findViewById(R.id.editTextCreateHouse);


        confirmHouseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Log.d("confirmHouseButton", "Confirm House Name clicked");
                if (viewModel.createHouse(houseName.getText().toString())){
                    ((MainActivity) getActivity()).openFragment(CreatejoinchoiceFragment.newInstance(b));
                Toast toast = Toast.makeText(getContext(), "House Created", Toast.LENGTH_SHORT);
                toast.show();
                }
                else {
                    Toast toast = Toast.makeText(getContext(), "You already have a house", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        backToChoiceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Log.d(TAG, "onClick: ");
                ((MainActivity) getActivity()).openFragment(CreatejoinchoiceFragment.newInstance(b));
            }

        });
        return view;


   

    }
}