package com.example.tak_frontend.profile;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;
public class Profile {

    private  static  final  String TAG = ".Profile";

    public UUID ProfileId;

    public String FirstName;

    public String LastName;

    public int XP;

    public UUID HouseId;

    public String Email;


    public Profile(@NotNull Profile profile){
        ProfileId = profile.ProfileId;
        FirstName = profile.FirstName;
        LastName = profile.LastName;
        this.XP = profile.XP;
        HouseId = profile.HouseId;
        Email = profile.Email;
    }

    public Profile(UUID profileId, String firstName, String lastName, int XP, UUID houseId, String email) {
        ProfileId = profileId;
        FirstName = firstName;
        LastName = lastName;
        this.XP = XP;
        HouseId = houseId;
        Email = email;
    }

    public static Profile fromJson(JSONObject object) throws JSONException {
        UUID profID = UUID.fromString(object.getString("profileId"));
        String fnTemp = object.getString("firstName");
        String lnTemp = object.getString("lastName");
        int xp = object.getInt("xp");
        UUID houseID = UUID.fromString(object.getString("houseId"));
        houseID = UUID.fromString(object.getString("houseId"));
        String emailTemp = object.getString("email");
        Profile profile = new Profile(profID, fnTemp, lnTemp, xp, houseID, emailTemp);
        return profile;
    }
}
