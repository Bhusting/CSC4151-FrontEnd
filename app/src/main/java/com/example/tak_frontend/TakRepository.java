package com.example.tak_frontend;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.tak_frontend.chore.ChoreData;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.task.TaskData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public class TakRepository {


    private  static  final  String TAG = ".TakRepository";
    private static final String BASE_URL = "https://takkapp.azurewebsites.net/";

/*    private LiveData<List<ChoreData>> allChores;

    private LiveData<List<TaskData>> allTasks;

    */

    private MutableLiveData<List<ChoreData>> allChores = new MutableLiveData<>();
    private MutableLiveData<Profile> profileLiveData = new MutableLiveData<>();
    private MutableLiveData<List<TaskData>> allTasks = new MutableLiveData<>();


    private HttpClient client;

    private String accessToken;
    private String idToken;
    private String email;
    private String fName;
    private String lName;

    public TakRepository(Application application) {

    }

    public void ifTokenNotSet(String Atoken, String IDtoken ) {
        if (accessToken == null) {
            accessToken = Atoken;
        }
        if(idToken == null){
            idToken = IDtoken;
        }
        getProfile();
        client = new HttpClient(accessToken);
    }

    @NotNull
    private void getProfile() {
        JWT jwt = new JWT(idToken);
        email = jwt.getClaim("email").asString();
        fName = jwt.getClaim("given_name").asString();
        lName = jwt.getClaim("family_name").asString();
    }

    public void fetchAll() {

    }

    public void insert(ChoreData data) {

    }

    public void insert(TaskData data) {

    }

    public void insert(JSONObject repsonseJson) {
        if (repsonseJson != null){
            try {
                UUID profID = UUID.fromString(repsonseJson.get("profileId").toString());
                String fnTemp = repsonseJson.getString("firstName");
                String lnTemp = repsonseJson.getString("lastName");
                int xp = repsonseJson.getInt("xp");
                UUID houseID = UUID.fromString(repsonseJson.get("houseId").toString());
                String emailTemp = repsonseJson.getString("email");
                Profile profile = new Profile(profID, fnTemp, lnTemp, xp, houseID, email);
                profileLiveData. postValue(profile);
                Log.d(TAG, "Set Profile Data");
            } catch (JSONException e) {

            }
        } else {
            Log.d(TAG, "httpGET json Null");
        }
    }

    public void delete(ChoreData data) {

    }

    public void delete(TaskData data) {

    }

    public void delete(Profile data) {

    }

    public void update(ChoreData data) {

    }

    public void update(TaskData data) {

    }

    public void update(Profile data) {

    }
    public void getProfileRequest(){

        JSONObject repsonseJson = new JSONObject();
        Profile profile;
        String temp = email.replace(".com", "");
        Log.d(TAG, "httpGETProfile: Email : " + temp);
        String url = BASE_URL+ "Profile/Email/" + temp ;
        Log.d(TAG, "httpGETProfile: URL : " + url);
        repsonseJson = client.httpGET(url);
    }

    public List<ChoreData> getAllChores() {
        return allChores.getValue();
    }

    public List<TaskData> getAllTasks() {
        return allTasks.getValue();
    }

    public LiveData<Profile> getProfileLiveData() {
        return profileLiveData;
    }
}
