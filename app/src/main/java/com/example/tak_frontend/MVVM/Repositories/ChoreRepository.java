package com.example.tak_frontend.MVVM.Repositories;

import com.example.tak_frontend.MVVM.Dao.TakRequestBuilder;
import com.google.gson.Gson;

public class ChoreRepository {

    private final Gson gson = new Gson();

    private TakRequestBuilder _requestBuilder;

    public ChoreRepository(String accessToken) {
        _requestBuilder = new TakRequestBuilder(accessToken);
    }


}
