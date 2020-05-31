package com.example.tak_frontend.MVVM.Repositories;

import com.example.tak_frontend.MVVM.Dao.TakDao;
import com.example.tak_frontend.MVVM.Dao.TakRequestBuilder;
import com.example.tak_frontend.profile.Profile;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.Request;

public class ProfileRepository {
    private final Gson gson = new Gson();

    private TakRequestBuilder _requestBuilder;

    public ProfileRepository(String accessToken, String idToken) {
        _requestBuilder = new TakRequestBuilder(accessToken);
    }

    public Profile GetProfileByEmail(String email, String firstName, String lastName) {
        Request emailRequest = _requestBuilder.BuildEmailGet(email);

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(emailRequest).get();

            if (json != "") {
                Profile profile = Profile.Deserialize(json);
                return profile;
            }
            else {
                Request createProfileRequest = _requestBuilder.BuildPost("Profile/"+ firstName + "/" + lastName, MediaType.parse("text/plain; charset=utf-8"), email);

                takDao = new TakDao();
                json = takDao.execute(createProfileRequest).get();
                if (json != "") {
                    UUID profileId = gson.fromJson(json, UUID.class);

                    Request getProfileRequest = _requestBuilder.BuildGet("Profile/" + profileId.toString());
                    json = "";
                    while (json == "") {
                        takDao = new TakDao();
                        json = takDao.execute(getProfileRequest).get();
                    }

                    if (json != "") {
                        Profile profile = Profile.Deserialize(json);
                        return profile;
                    }
                    else {
                        return null;
                    }
                }


            }

        }
        catch (Exception e) {

        }
        return null;
    }

    public LinkedList<Profile> GetAllProfilesInHouse(UUID houseId) {

        Request request = _requestBuilder.BuildGet("Profile/House/" + houseId.toString());

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();

            LinkedList<Profile> profiles = Profile.DeserializeList(json);

            return profiles;
        }
        catch(Exception e) {

        }

        return null;
    }

    public boolean AddXP(UUID profileId) {
        Request request = _requestBuilder.BuildPost("Profile/" + profileId.toString() + "/XP", MediaType.parse("text/plain; charset=utf-8"), "");

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();
            return true;
        }
        catch(Exception e) {

        }

        return false;
    }

    public boolean UpdateHouse(UUID profileId, UUID houseId) {
        Request request = _requestBuilder.BuildPost("Profile/" + profileId.toString() + "/House/" + houseId.toString(), MediaType.parse("text/plain; charset=utf-8"), "");

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();
            return true;
        }
        catch (Exception e) {

        }

        return false;
    }
}
