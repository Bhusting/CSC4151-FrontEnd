package com.example.tak_frontend.profile;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;
import com.example.tak_frontend.createjoin.CreatejoinchoiceFragment;


public class ProfileFragment extends Fragment {


    private static final String TAG = ".ProfileFragment";
    private TextView houseTextView;
    private TextView profileName;
    private TextView xpTextView;
    private Button leaveHouse;
    private Button createJoinHouse;
    private Button getHouseCode;
    private SharedPreferences pref;
    private NewTakViewModel  _viewModel;



    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Profile");

        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        _viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity()
                        .getApplication(),
                        pref.getString("accessToken", ""),
                        pref.getString("idToken", "")))
                .get(NewTakViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        houseTextView = view.findViewById(R.id.houseString_Profile);
        profileName = view.findViewById(R.id.residentName_Profile);
        xpTextView = view.findViewById(R.id.xpValueString_Profile);
        leaveHouse = view.findViewById(R.id.leaveButton_Profile);
        createJoinHouse = view.findViewById(R.id.houseCreateJoinButton);
        getHouseCode = view.findViewById(R.id.getHouseId);


        try {
            refreshProfile(_viewModel.getLiveProfile().getValue());
            refreshHouse(_viewModel.getLiveHouse().getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Button Clicked!
        leaveHouse.setOnClickListener(v -> {
            _viewModel.LeaveHouse();
            houseTextView.setText(":(");
        });

        //Button Clicked!
        createJoinHouse.setOnClickListener(v -> {
            ((MainActivity) getActivity()).openFragment(CreatejoinchoiceFragment.newInstance());
        });

        getHouseCode.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) ((MainActivity) getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("houseId", _viewModel.getLiveProfile().getValue().houseId.toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        });
        _viewModel.getLiveProfile().observe(getViewLifecycleOwner(), profile -> refreshProfile(profile));

        _viewModel.getLiveHouse().observe(getViewLifecycleOwner(), house -> refreshHouse(house));

        if(!_viewModel.HasHouse()){
            ((MainActivity) getActivity()).openFragment(CreatejoinchoiceFragment.newInstance());
            Toast.makeText(getContext(), "Create or Join a House please", Toast.LENGTH_LONG).show();
        }

        return view;
    }
    public void refreshProfile(Profile profile){
        profileName.setText(profile.firstName + " " + profile.lastName);
        xpTextView.setText(String.valueOf(profile.xp));
    }

    public void refreshHouse(House house){
        houseTextView.setText(house.houseName);
    }
};