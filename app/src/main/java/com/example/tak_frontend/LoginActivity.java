package com.example.tak_frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.hotspot2.pps.Credential;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.auth0.android.Auth0;
import com.auth0.android.Auth0Exception;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.storage.CredentialsManagerException;
import com.auth0.android.authentication.storage.SecureCredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.VoidCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;


public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS";
    public static final String EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN";
    private static final String TAG = LoginActivity.class.getName();
    private static final String EXTRA_ID_TOKEN = "com.auth0.ID_TOKEN";
    private Auth0 auth0;
    private Button  loginButton;
    private SecureCredentialsManager credentialsManager;
    private NewTakViewModel _viewModel;

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
    }
    private void login() {
        WebAuthProvider.login(auth0)
                .withScheme("demo")
                .withAudience("https://tak")
                .withScope("openid profile email offline_access read:current_user update:current_user_metadata")
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
                                Log.d(TAG, "ID: " + credentials.getIdToken());
                                Log.d(TAG, "Token: " + credentials.getAccessToken());

                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                SharedPreferences.Editor editor = pref.edit();

                                editor.putString("accessToken", credentials.getAccessToken());
                                editor.putString("idToken", credentials.getIdToken());
                                editor.commit();

                                getViewModel(credentials);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
    }
    private void getViewModel(Credentials credentials){
         _viewModel = new ViewModelProvider(this,
                new TakViewModelFactory(getApplication(), credentials.getAccessToken(), credentials.getIdToken()))
                .get(NewTakViewModel.class);
         _viewModel.getProfile();
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
        credentialsManager.getCredentials(new BaseCallback<Credentials, CredentialsManagerException>() {
            @Override
            public void onSuccess(final Credentials credentials) {
                // Move to the next activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(EXTRA_ACCESS_TOKEN, credentials.getAccessToken());
                intent.putExtra(EXTRA_ID_TOKEN, credentials.getIdToken());

                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(CredentialsManagerException error) {
                // Credentials could not be retrieved.
                finish();
            }
        });
    }
    private final AuthCallback loginCallback = new AuthCallback() {
        @Override
        public void onFailure(@NonNull final Dialog dialog) {
            // Show error dialog
        }

        @Override
        public void onFailure(AuthenticationException exception) {
            // Show error message
        }

        @Override
        public void onSuccess(@NonNull Credentials credentials) {
            // User successfully authenticated
            credentialsManager.saveCredentials(credentials);
            showNextActivity();
        }
    };
    private void doLogout() {
        WebAuthProvider.logout(auth0)
                .withScheme("demo")
                .start(this, logoutCallback);
    }

    private VoidCallback logoutCallback = new VoidCallback() {
        @Override
        public void onSuccess(Void payload) {
            credentialsManager.clearCredentials();
        }

        @Override
        public void onFailure(Auth0Exception error) {
            // Log out canceled, keep the user logged in
            showNextActivity();
        }
    };

}
