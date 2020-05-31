package com.example.tak_frontend.MVVM.Repositories;

import com.example.tak_frontend.MVVM.Dao.TakDao;
import com.example.tak_frontend.MVVM.Dao.TakRequestBuilder;
import com.example.tak_frontend.chore.ChoreData;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.Request;

public class ChoreRepository {

    private final Gson gson = new Gson();

    private TakRequestBuilder _requestBuilder;

    public ChoreRepository(String accessToken) {
        _requestBuilder = new TakRequestBuilder(accessToken);
    }

    public LinkedList<ChoreData> GetChoresForToday(UUID houseId) {

        Request request = _requestBuilder.BuildGet("Chore/House/" + houseId.toString() + "/Today");

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();

            LinkedList<ChoreData> list =  ChoreData.DeserializeList(json);

            return list;
        }
        catch(Exception e) {

        }

        return null;

    }

    public UUID CreateChore(ChoreData chore) {

        Request request = _requestBuilder.BuildPost("Chore/", MediaType.parse("application/json; charset=utf-8"), gson.toJson(chore));

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();

            UUID choreId =  gson.fromJson(json, UUID.class);

            return choreId;
        }
        catch(Exception e) {

        }
        return null;
    }

    public UUID CompleteChore(UUID choreId) {

        Request request = _requestBuilder.BuildPost("Chore/" + choreId, MediaType.parse("application/json; charset=utf-8"), "");

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();

            return choreId;
        }
        catch(Exception e) {

        }

        return null;
    }

    public boolean DeleteChore(UUID choreId) {

        Request request = _requestBuilder.BuildDelete("Chore/" + choreId);

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();

            return true;
        }
        catch(Exception e) {

        }

        return false;
    }

}
