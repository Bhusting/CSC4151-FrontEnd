package com.example.tak_frontend.MVVM.Dao;

import android.util.Log;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class TakRequestBuilder {

    private final String BASE_URL = "https://takkapp.azurewebsites.net/";

    private final String _accessToken;

    public TakRequestBuilder(String accessToken) {
        _accessToken = accessToken;
    }

    public Request BuildGet(String path) {
        String url = BASE_URL + path;

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + _accessToken)
                .url(url)
                .get()
                .build();

        return request;
    }

    public Request BuildEmailGet(String email) {
        String url = BASE_URL + "Profile/Email";

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + _accessToken)
                .addHeader("Email", email)
                .url(url)
                .get()
                .build();

        return request;
    }

    public Request BuildPost(String path, MediaType mediaType, String content) {
        String url = BASE_URL + path;

        RequestBody body = RequestBody.create(content, mediaType);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + _accessToken)
                .url(url)
                .post(body)
                .build();

        return request;
    }

    public Request BuildDelete(String path) {
        String url = BASE_URL + path;

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + _accessToken)
                .url(url)
                .delete()
                .build();

        return request;
    }
}
