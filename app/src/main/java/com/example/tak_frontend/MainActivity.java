package com.example.tak_frontend;

import android.content.Intent;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Header;
import com.android.volley.Response;
import com.auth0.android.Auth0;
import com.example.tak_frontend.chore.ChoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = ".MainActivity";
    private static final String API_URL = String.valueOf(R.string.com_auth0_domain);
    private Auth0 auth0;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: entered main");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(TaskFragment.newInstance("", ""));
        bottomNavigation.setSelectedItemId(R.id.navigation_task);

        String accessToken = getIntent().getStringExtra(LoginActivity.EXTRA_ACCESS_TOKEN);
        Toast toast = Toast.makeText(this, accessToken, Toast.LENGTH_LONG);
        toast.show();
        Log.d(TAG, "Token: " + accessToken);

        EditText temp = findViewById(R.id.editText);
        temp.setText(accessToken);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();


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
}

