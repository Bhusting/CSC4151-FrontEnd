package com.example.tak_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.management.ManagementException;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;
import com.example.tak_frontend.chore.ChoreFragment;
import com.example.tak_frontend.leaderboard.LeaderboardFragment;
import com.example.tak_frontend.profile.ProfileFragment;
import com.example.tak_frontend.task.TaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = ".MainActivity";
    private static final String API_URL = String.valueOf(R.string.com_auth0_domain);
    private Auth0 auth0;
    private UsersAPIClient usersClient;
    private AuthenticationAPIClient authenticationAPIClient;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: entered main");
        super.onCreate(savedInstanceState);

        Auth0 auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);

        String accessToken = getIntent().getStringExtra(LoginActivity.EXTRA_ACCESS_TOKEN);
        usersClient = new UsersAPIClient(auth0, accessToken);
        authenticationAPIClient = new AuthenticationAPIClient(auth0);

        getProfile(accessToken);

        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(TaskFragment.newInstance("", ""));
        bottomNavigation.setSelectedItemId(R.id.navigation_task);

        Toast toast = Toast.makeText(this, accessToken, Toast.LENGTH_LONG);
        toast.show();
        Log.d(TAG, "Token: " + accessToken);

    }

    public String getToken(){
        return (getIntent().getStringExtra(LoginActivity.EXTRA_ACCESS_TOKEN));
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_chore:
                        openFragment(ChoreFragment.newInstance());
                        return true;
                    case R.id.navigation_leaderboard:
                        openFragment(LeaderboardFragment.newInstance("", ""));
                        return true;
                    case R.id.navigation_profile:
                        openFragment(ProfileFragment.newInstance("", ""));
                        return true;
                    case R.id.navigation_task:
                        openFragment(TaskFragment.newInstance("", ""));
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
    public void testHTTP() throws JSONException {
   }

    private void getProfile(String accessToken) {
        authenticationAPIClient.userInfo(accessToken)
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile userinfo) {
                        usersClient.getProfile(userinfo.getId())
                                .start(new BaseCallback<UserProfile, ManagementException>() {
                                    @Override
                                    public void onSuccess(UserProfile profile) {
                                        // Display the user profile
                                        Log.d(TAG, "First: " + profile.getName());
                                        Log.d(TAG, "Last: " + profile.getFamilyName());
                                        Log.d(TAG, "ID: " + profile.getId());
                                        Log.d(TAG, "Email: " + profile.getEmail());
                                    }

                                    @Override
                                    public void onFailure(ManagementException error) {
                                        // Show error
                                    }
                                });
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        // Show error
                    }
                });
    }
}

