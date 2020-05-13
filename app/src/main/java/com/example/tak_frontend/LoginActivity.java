package com.example.tak_frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;
import com.auth0.android.Auth0;
import com.auth0.android.Auth0Exception;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.storage.SecureCredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.AuthenticationCallback;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.VoidCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

import javax.xml.transform.ErrorListener;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS";
    public static final String EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN";
    private static final String TAG = LoginActivity.class.getName();
    private static final String EXTRA_ID_TOKEN = "com.auth0.ID_TOKEN";
    private Auth0 auth0;
    private Button  loginButton;
    private SecureCredentialsManager credentialsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
        credentialsManager = new SecureCredentialsManager(this, new AuthenticationAPIClient(auth0), new SharedPreferencesStorage(this));
        if (getIntent().getBooleanExtra(EXTRA_CLEAR_CREDENTIALS, false)) {
            logout();
        }
        if (credentialsManager.hasValidCredentials()) {
            // Obtain the existing credentials and move to the next activity
            showNextActivity();
        }
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyApp", "It clicked");
                login();
            }
        });
        loginButton = findViewById(R.id.signUpButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyApp", "It clicked");

            }
        });
        //.withScope("openid offline_access")
    }
    private void login() {
        WebAuthProvider.login(auth0)
                .withScheme("demo")
                .withAudience("https://tak")
                .start(this, new AuthCallback() {
                    @Override
                    public void onFailure(@NonNull final Dialog dialog) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(final AuthenticationException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(@NonNull final Credentials credentials) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(EXTRA_ACCESS_TOKEN, credentials.getAccessToken());
                                //intent.putExtra(EXTRA_ACCESS_TOKEN, credentials.getIdToken());
                                Log.d(TAG, "ID: " + credentials.getIdToken());
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
    }
    private void logout() {
        WebAuthProvider.logout(auth0)
                .withScheme("demo")
                .start(this, new VoidCallback() {
                    @Override
                    public void onSuccess(Void payload) {

                    }

                    @Override
                    public void onFailure(Auth0Exception error) {
                        //Log out canceled, keep the user logged in
                        showNextActivity();
                    }
                });
    }

    private void showNextActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
