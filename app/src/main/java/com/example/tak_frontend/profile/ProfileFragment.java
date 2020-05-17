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

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.tak_frontend.HttpClient;
import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;

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
        idProfile = getProfile(idToken);
        String email = idProfile[0];
        email = email.replace(".com", "");
        Log.d(TAG, "httpGETProfile: Email : " + email);
        String url = BASE_URL+ "Email/" + email ;
        Log.d(TAG, "httpGETProfile: URL : " + url);

        HttpClient client = new HttpClient(accessToken);
        JSONObject jsonObject = client.httpGET(url);


        if (jsonObject != null){
            try {
                UUID profID = UUID.fromString(jsonObject.get("profileId").toString());
                String fnTemp = jsonObject.getString("firstName");
                String lnTemp = jsonObject.getString("lastName");
                int xp = jsonObject.getInt("xp");
                UUID houseID = UUID.fromString(jsonObject.get("houseId").toString());
                String emailTemp = jsonObject.getString("email");
                profile = new Profile(profID, fnTemp, lnTemp, xp, houseID, email);

            } catch (JSONException e) {

            }
        } else {
          Log.d(TAG, "httpGET json Null");
        }
        //Get Data from idToken
        idProfile = getProfile(idToken);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: profile");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        houseTextView = view.findViewById(R.id.houseString_Profile);
        profileName = view.findViewById(R.id.residentName_Profile);
        xpTextView = view.findViewById(R.id.xpValueString_Profile);
        houseTextView.setText(profile.HouseId.toString());
        profileName.setText(profile.FirstName + " " + profile.LastName);
        xpTextView.setText(String.valueOf(profile.XP));

        return view;
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



    @NotNull
    private String[] getProfile(String idToken) {
        JWT jwt = new JWT(idToken);
        Claim userEmail = jwt.getClaim("email");
        Claim userFName = jwt.getClaim("given_name");
        Claim userLName = jwt.getClaim("family_name");
        String[] temp ={ userEmail.asString(), userFName.asString(), userLName.asString()};

        return temp;
    }
};