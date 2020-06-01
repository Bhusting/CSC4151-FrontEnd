package com.example.tak_frontend.createjoin;

import android.content.SharedPreferences;
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

import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;
import com.example.tak_frontend.profile.ProfileFragment;


public class CreateFragment extends Fragment {


    private static final String TAG = ".CreateFragment";
    private SharedPreferences pref;
    private Button confirmHouseButton;
    private Button backToChoiceButton;
    private TextView houseName;

    private NewTakViewModel viewModel;


    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance() {
        CreateFragment fragment = new CreateFragment();
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
        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity()
                        .getApplication(),
                        pref.getString("accessToken", ""),
                        pref.getString("idToken", "")))
                .get(NewTakViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_createhouse, container, false);

        confirmHouseButton = (Button) view.findViewById(R.id.acceptCreateButton);
        backToChoiceButton = (Button) view.findViewById(R.id.backCreateButton);
        houseName = view.findViewById(R.id.editTextCreateHouse);


        confirmHouseButton.setOnClickListener(v -> {
            Log.d("confirmHouseButton", "Confirm House Name clicked");
            if (viewModel.CreateHouse(houseName.getText().toString()) == null) {
                Toast toast = Toast.makeText(getContext(), "House not Created :(", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getContext(), "House Created!", Toast.LENGTH_SHORT);
                toast.show();
                ((MainActivity) getActivity()).openFragment(ProfileFragment.newInstance());
            }
        });
        backToChoiceButton.setOnClickListener(v -> {
            ((MainActivity) getActivity()).openFragment(CreatejoinchoiceFragment.newInstance());
        });
        return view;
    }
}
