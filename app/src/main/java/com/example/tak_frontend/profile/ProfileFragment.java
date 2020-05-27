package com.example.tak_frontend.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.TakViewModel;
import com.example.tak_frontend.TakViewModelFactory;
import com.example.tak_frontend.createjoin.CreatejoinchoiceFragment;


public class ProfileFragment extends Fragment {


    private static final String TAG = ".ProfileFragment";
    private Profile profile;
    private TextView houseTextView;
    private TextView profileName;
    private TextView xpTextView;
    private Button leaveHouse;
    private Button createJoinHouse;
    private Bundle b;

    private TakViewModel  viewModel;



    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(Bundle b) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(b);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Profile");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: profile");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        houseTextView = view.findViewById(R.id.houseString_Profile);
        profileName = view.findViewById(R.id.residentName_Profile);
        xpTextView = view.findViewById(R.id.xpValueString_Profile);
        leaveHouse = view.findViewById(R.id.leaveButton_Profile);
        createJoinHouse = view.findViewById(R.id.houseCreateJoinButton);

        //Button Clicked!
        leaveHouse.setOnClickListener(v -> {

        });

        //Button Clicked!
        createJoinHouse.setOnClickListener(v -> {
            ((MainActivity) getActivity()).openFragment(CreatejoinchoiceFragment.newInstance(b));
        });

        return view;
    }

    public void refresh(){
        if (profile != null) {
            //houseTextView.setText(profile.HouseId.toString());
            profileName.setText(profile.FirstName + " " + profile.LastName);
            xpTextView.setText(String.valueOf(profile.XP));
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        b = getArguments();
        viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity().getApplication(), b))
                .get(TakViewModel.class);

        viewModel.getProfile().observe(getViewLifecycleOwner(), new Observer<Profile>(){
            @Override
            public void onChanged(Profile obsProfile) {
                Log.d(TAG, "Profile Data Changed");
                profile = obsProfile;
                refresh();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




};