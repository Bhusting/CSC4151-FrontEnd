package com.example.tak_frontend.auth0;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.example.tak_frontend.LoginActivity;
import com.example.tak_frontend.MainActivity;

public class Auth0Client {
    private final Activity _loginActivity;
    private final Class<MainActivity> _mainActivity;
    private Auth0 auth0;

    public Auth0Client(Activity loginActivity, Class<MainActivity> mainActivity) {
        _loginActivity = loginActivity;
        _mainActivity = mainActivity;
        auth0 = new Auth0(loginActivity);
        auth0.setOIDCConformant(true);
    }


    private void login() {
        WebAuthProvider.login(auth0)
                .withScheme("demo")
                .withAudience("https://tak")
                .withScope("openid profile email offline_access read:current_user update:current_user_metadata")
                .start(_loginActivity, new AuthCallback() {
                    @Override
                    public void onFailure(@NonNull final Dialog dialog) {
                        _loginActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(final AuthenticationException exception) {

                        _loginActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(_loginActivity, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(@NonNull final Credentials credentials) {
                        _loginActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(_loginActivity, _mainActivity);
                                String[] temp = {credentials.getAccessToken(), credentials.getIdToken()};

                                intent.putExtra("Tokens", temp);
                                _loginActivity.startActivity(intent);
                                _loginActivity.finish();
                            }
                        });
                    }
                });
    }


}
