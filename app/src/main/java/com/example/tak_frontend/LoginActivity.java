package com.example.tak_frontend;

import androidx.appcompat.app.AppCompatActivity;

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

import javax.xml.transform.ErrorListener;

public class LoginActivity extends AppCompatActivity {



    private static final String TAG = LoginActivity.class.getName();

    Button  loginButton;


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://postman-echo.com/get?foo1=bar1&foo2=bar2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyApp", "It clicked");
                sendAndRequestResponse();
/*                Intent intent;
                intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);*/
            }
        });

    }

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);
        Log.i("MyApp", "It Sent");
        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, response -> {

            Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
            Log.i("MyApp", "It Returned");

        }, error -> Log.i(TAG,"Error :" + error.toString()));

        mRequestQueue.add(mStringRequest);
        Log.i("MyApp", "It Sent");
    }
}
