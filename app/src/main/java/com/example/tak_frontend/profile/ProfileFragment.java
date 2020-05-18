package com.example.tak_frontend.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.tak_frontend.HttpClient;
import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.TakViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;


public class ProfileFragment extends Fragment {


    private static final String TAG = ".ProfileFragment";
    private static final String BASE_URL = "https://takkapp.azurewebsites.net/Profile/";
    private Profile profile;
    private TextView houseTextView;
    private TextView profileName;
    private TextView xpTextView;
    private String accessToken;
    private String idToken;
    private String[] idProfile;
    private TakViewModel  viewModel;






    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

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

        //Get Tokens

        Bundle b = this.getArguments();
        accessToken = b.getString("AuthToken");
        idToken = b.getString("IdToken");


        viewModel = new ViewModelProvider(this).get(TakViewModel.class);


/*        viewModel.getProfile().observe(this, profileRepo -> {
            profile = profileRepo;
            refresh();
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: profile");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        houseTextView = view.findViewById(R.id.houseString_Profile);
        profileName = view.findViewById(R.id.residentName_Profile);
        xpTextView = view.findViewById(R.id.xpValueString_Profile);
        refresh();
        return view;
    }

    public void refresh(){
        //houseTextView.setText(profile.HouseId.toString());
        profile = new Profile(viewModel.getProfile());
        profileName.setText(profile.FirstName + " " + profile.LastName);
        xpTextView.setText(String.valueOf(profile.XP));
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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