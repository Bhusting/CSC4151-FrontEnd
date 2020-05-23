package com.example.tak_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.jwt.JWT;
import com.auth0.android.management.ManagementException;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;
import com.example.tak_frontend.chore.ChoreFragment;
import com.example.tak_frontend.leaderboard.LeaderboardFragment;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.profile.ProfileFragment;
import com.example.tak_frontend.task.TaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = ".MainActivity";
    private static final String API_URL = String.valueOf(R.string.com_auth0_domain);
    private Auth0 auth0;
    private UsersAPIClient usersClient;
    private AuthenticationAPIClient authenticationAPIClient;

    protected String accessToken;
    protected String idToken;
    private TakViewModel  viewModel;
    private String curFragment;
    private String choreFrag = "chore";
    private String taskFrag = "task";
    private String leadFrag = "lead";
    private String profileFrag = "profile";
    private Bundle b;


    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: entered main");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Auth0 auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);

        String[] temp = getIntent().getStringArrayExtra("Tokens");

        accessToken = temp[0];
        idToken = temp[1];

        //Bundle to hold Tokens
        b = new Bundle();
        b.putString("AuthToken", getTokens()[0]);
        b.putString("IdToken", getTokens()[1]);



        usersClient = new UsersAPIClient(auth0, accessToken);
        authenticationAPIClient = new AuthenticationAPIClient(auth0);

        viewModel = new ViewModelProvider(this,
                new TakViewModelFactory(this.getApplication(), b))
                .get(TakViewModel.class);



        ProfileFragment.newInstance(b);




        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(TaskFragment.newInstance("", ""));
        bottomNavigation.setSelectedItemId(R.id.navigation_task);

        //Toast toast = Toast.makeText(this, accessToken, Toast.LENGTH_LONG);
        //toast.show();
       // Log.d(TAG, "Token: " + accessToken);


    }

    public String[] getTokens(){
        return (getIntent().getStringArrayExtra("Tokens"));
    }

    public void openFragment(Fragment fragment) {
        //Bundle to hold Tokens
        Bundle b = new Bundle();
       // b.putString("AuthToken", getTokens()[0]);
       // b.putString("IdToken", getTokens()[1]);
        //Add bundle to Fragment
        fragment.setArguments(b);
        //Begin transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_chore:
                        if(curFragment != choreFrag){
                            curFragment = choreFrag;
                            openFragment(ChoreFragment.newInstance());
                        }
                        return true;
                    case R.id.navigation_leaderboard:
                        if(curFragment != leadFrag) {
                            curFragment = leadFrag;
                            openFragment(LeaderboardFragment.newInstance());
                        }
                        return true;
                    case R.id.navigation_profile:
                        if(curFragment != profileFrag){
                            curFragment = profileFrag;
                            openFragment(ProfileFragment.newInstance(b));
                        }
                        return true;
                    case R.id.navigation_task:
                        if(curFragment != taskFrag){
                            curFragment = taskFrag;
                            openFragment(TaskFragment.newInstance("", ""));
                        }
                        return true;
                }
                return false;
            };
    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(LoginActivity.EXTRA_CLEAR_CREDENTIALS, true);
        startActivity(intent);
        finish();
    }

}

