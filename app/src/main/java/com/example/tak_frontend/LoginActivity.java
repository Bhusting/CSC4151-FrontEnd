package com.example.tak_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {


    Button  loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyApp", "It clicked");
                Intent intent;
                intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }


}
