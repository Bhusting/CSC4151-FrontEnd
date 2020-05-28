package com.example.tak_frontend.profile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.UUID;
public class Profile {

    private  static  final  String TAG = ".Profile";

    public UUID profileId;

    public String firstName;

    public String lastName;

    public int xp;

    public UUID houseId;

    public String email;


    public Profile(){
        profileId   = null;
        firstName   = null;
        lastName    = null;
        xp          = 0;
        houseId     = null;
        email       = null;
    }

    public Profile(UUID profileId, String firstName, String lastName, int XP, UUID houseId, String email) {
        this.profileId = profileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.xp = XP;
        this.houseId = houseId;
        this.email = email;
    }

    public static final Profile Deserialize(String json){
        Gson gson = new Gson();
        return  gson.fromJson(json, Profile.class);
    }

    public static final LinkedList<Profile> DeserializeList(String json){
        Type listType = new TypeToken<LinkedList<Profile>>() {}.getType();
        Gson gson = new Gson();
        LinkedList<Profile> list = gson.fromJson(json, listType);

        return list;
    }

}
