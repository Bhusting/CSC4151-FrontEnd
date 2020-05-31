package com.example.tak_frontend.MVVM.Dao;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TakDao extends AsyncTask<Request, Void, String> {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(Request... requests) {

        try {
            Response response = client.newCall(requests[0]).execute();

            Log.d("TakDao", response.toString());

            String json = response.body().string();
            Log.d("Response ", json);

            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
