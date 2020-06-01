package com.example.tak_frontend.MVVM.Repositories;

import com.example.tak_frontend.MVVM.Dao.TakDao;
import com.example.tak_frontend.MVVM.Dao.TakRequestBuilder;
import com.example.tak_frontend.profile.House;
import com.google.gson.Gson;

import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.Request;

public class HouseRepository {

    private final Gson gson = new Gson();

    private TakRequestBuilder _requestBuilder;

    public HouseRepository(String accessToken) {
        _requestBuilder = new TakRequestBuilder(accessToken);
    }

    public House GetHouse(UUID houseId) {
        Request request = _requestBuilder.BuildGet("House/" + houseId.toString());

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();

            House house = House.Deserialize(json);

            return house;
        }
        catch(Exception e) {

        }

        return null;
    }

    public House CreateHouse(String houseName) {
        Request request = _requestBuilder.BuildPost("House/" + houseName, MediaType.parse("text/plain; charset=utf-8"), "");

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();
            UUID houseId = gson.fromJson(json, UUID.class);

            try{
                wait(2000);
            }
            catch (Exception e) {

            }

            House house = GetHouse(houseId);


            return house;

        } catch (Exception e) {

        }

        return null;
    }

}
