package com.example.tak_frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.management.UsersAPIClient;
import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;
import com.example.tak_frontend.chore.ChoreFragment;
import com.example.tak_frontend.createjoin.CreatejoinchoiceFragment;
import com.example.tak_frontend.leaderboard.LeaderboardFragment;
import com.example.tak_frontend.profile.ProfileFragment;
import com.example.tak_frontend.task.TaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = ".MainActivity";
    private static final String API_URL = String.valueOf(R.string.com_auth0_domain);
    private Auth0 auth0;
    private UsersAPIClient usersClient;
    private AuthenticationAPIClient authenticationAPIClient;
    private SharedPreferences pref;
    private String accessToken;
    private String idToken;
    private NewTakViewModel viewModel;
    private String curFragment;
    private String choreFrag = "chore";
    private String taskFrag = "task";
    private String leadFrag = "lead";
    private String profileFrag = "profile";

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: entered main");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        accessToken = pref.getString("accessToken", "");
        idToken = pref.getString("idToken", "");


        Auth0 auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);

        usersClient = new UsersAPIClient(auth0, accessToken);
        authenticationAPIClient = new AuthenticationAPIClient(auth0);

        viewModel = new ViewModelProvider(this,
                new TakViewModelFactory(this.getApplication(), accessToken, idToken))
                .get(NewTakViewModel.class);

        viewModel.GetHouse();
        viewModel.getLeaderboard();
        viewModel.GetAllTasks();

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigation.setSelectedItemId(R.id.navigation_task);

        Log.d(TAG, "Token: " + accessToken);


    }

    public void openFragment(Fragment fragment) {
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
                            openFragment(ProfileFragment.newInstance());
                        }
                        return true;
                    case R.id.navigation_task:
                        if(curFragment != taskFrag){
                            curFragment = taskFrag;
                            openFragment(TaskFragment.newInstance());
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

