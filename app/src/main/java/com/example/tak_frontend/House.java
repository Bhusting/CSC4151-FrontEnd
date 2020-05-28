package com.example.tak_frontend;

import com.google.gson.Gson;

import java.util.UUID;

public class House {
    public UUID houseID;
    public String houseName;
    public UUID channel;

    public static final House Deserialize(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, House.class);
    }
}
