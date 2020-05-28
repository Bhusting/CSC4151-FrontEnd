package com.example.tak_frontend.MVVM;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tak_frontend.profile.House;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.task.TaskDTO;
import com.example.tak_frontend.task.TaskData;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TakDao extends AppCompatActivity {

    private static final String TAG = ".TakDao";

    public static final MediaType JSON = MediaType.parse("text/plain; charset=utf-8");
    private static final String BASE_URL = "https://takkapp.azurewebsites.net/";
    private String accessToken;



    private OkHttpClient client;


    public TakDao(String token){
        client = new OkHttpClient();
        accessToken = token;
    }


    //----All Task API Paths----

    //Gets all a Houses Tasks
    //Returns a List of Tasks
    public LinkedList<TaskData> fetchAllTasks(UUID houseID) throws IOException{
        LinkedList<TaskData> list = new LinkedList<TaskData>();
        String URL = BASE_URL + "Task/House/" + houseID;
        Log.d(TAG, "fetchAllTasks: URL: " + URL);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(URL)
                .get()
                .build();
        Log.d(TAG, "fetchAllTasks: Request Built");

        try {
            Log.d(TAG, "fetchAllTasks: GET Executing!");
            Response response = client.newCall(request).execute();
            JSONObject object = new JSONObject(response.body().toString());
            TaskData temp;
            return (TaskData.toList(object));

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "fetchAllTasks: error: " + e.getCause());
            return null;
        }
    }

    //Creates a New Task
    //Returns a new taskId
    public int createTask(TaskDTO dto){
        return  1;
    }

    //Delete a Task
    //Returns void
    public void deleteTask(UUID taskID){

    }

    //-----------------------------------------House------------------------------------------------

    //Get House by houseId
    //Returns House
    public House getHouseById(UUID houseId){
        String url = BASE_URL + "House/" + houseId.toString();
        Log.d(TAG, "getHouseById: url: " + url);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .get()
                .build();
        Log.d(TAG, "getHouseById: Request Built" + request.toString());
        try{
            Log.d(TAG, "getHouseById: GET Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "getHouseById: GET code: " + response.code());
            String json = response.body().string();

            if (response.code() == 200){
                Log.d(TAG, "getHouseById: body: " + json);
                House house = House.Deserialize(json);
                return house;
            }
            else{
                Log.d(TAG, "getHouseById: GET error code: " + response.code() + " message: "
                        + response.message());
                return  null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getHouseById: error: " + e.getCause());
            return  null;
        }
    }

    //Get House by profileId
    //Returns House
    public House getHouseByProfileId(UUID profileId){
        String url = BASE_URL + "House/Profile/" + profileId.toString();
        Log.d(TAG, "getHouseByProfile: url: " + url);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .get()
                .build();
        Log.d(TAG, "getHouseByProfile: Request Built" + request.toString());

        try{
            Log.d(TAG, "getHouseByProfile: GET Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "getHouseByProfile: GET code: " + response.code());
            String json = response.body().string();
            if (response.code() == 200){
                Log.d(TAG, "getHouseByProfile: body: " + json);
                House house = House.Deserialize(json);
                return house;
            }
            else{
                Log.d(TAG, "getHouseByProfile: GET error code: " + response.code() + " message: "
                        + response.message());
                return  null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getHouseByProfile: error: " + e.getCause());
            return  null;
        }
    }

    //Create a New House
    //Returns new houseId
    public ValueHolder createHouse(String houseName){
        String url = BASE_URL + "House/" + houseName;
        Log.d(TAG, "createHouse: url: " + url);

        RequestBody requestBody = RequestBody.create(new byte[0], null);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .post(requestBody)
                .build();
        Log.d(TAG, "createHouse: Request Built" + request.toString());

        try{
            Log.d(TAG, "createHouse: POST Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "createHouse: POST code: " + response.code());
            String json = response.body().string();
            if (response.code() == 202){
                Log.d(TAG, "createHouse: body: " + json);
                Gson gson = new Gson();
                UUID uuid = gson.fromJson(json, UUID.class);
                return new ValueHolder(response.code(), uuid);
            }
            else{
                Log.d(TAG, "createHouse: POST error code: " + response.code() + " message: "
                        + response.message());
                return  new ValueHolder(response.code(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "createHouse: error: " + e.getCause());
            return  null;
        }
    }

    //Delete a House
    //Returns void
    public void deleteHouse(UUID houseId){
        String url = BASE_URL + "House/" + houseId.toString();
        Log.d(TAG, "deleteHouse: url: " + url);
    }

    //----------------------------------------Profile-----------------------------------------------

    //Gets Profile by Id
    //Returns Profile
    public Profile getProfileById(UUID profileId){
        String url = BASE_URL + "Profile/" + profileId.toString();
        Log.d(TAG, "getProfileById: url: " + url);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .get()
                .build();
        Log.d(TAG, "getProfileById: Request Built" + request.toString());

        try{
            Log.d(TAG, "getProfileById: GET Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "getProfileById: GET code: " + response.code());
            String json = response.body().string();

            if (response.code() == 200){
                Log.d(TAG, "getProfileById: body: " + json);
                Profile profile = Profile.Deserialize(json);
                return profile;
            }
            else{
                Log.d(TAG, "getProfileById: GET error code: " + response.code() + " message: "
                        + response.message());
                return  null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getProfileById: error: " + e.getCause());
            return  null;
        }
    }

    //Gets Profile XP by Id
    //Returns int
    public int getProfileXp(UUID profileId){
        String url = BASE_URL + "Profile/" + profileId.toString() + "/XP";
        Log.d(TAG, "getProfileXp: url: " + url);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .get()
                .build();
        Log.d(TAG, "getProfileByXp: Request Built" + request.toString());

        try{
            Log.d(TAG, "getProfileByXp: GET Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "getProfileByXp: GET code: " + response.code());
            String json = response.body().string();

            if (response.code() == 200){
                Log.d(TAG, "getProfileByXp: body: " + json);
                Profile profile = Profile.Deserialize(json);
                return profile.xp;
            }
            else{
                Log.d(TAG, "getProfileByXp: GET error code: " + response.code() + " message: " + response.message());
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getProfileByXp: error: " + e.getCause());
            return 0;
        }
    }

    //Gets Profile by Email
    //Returns Profile
    public ValueHolder getProfileByEmail(String email){
        String url = BASE_URL + "Profile/Email";
        Log.d(TAG, "getProfileByEmail: url: " + url + " body: " + email);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("email", email)        //Add header key "email" value email
                .url(url)
                .get()
                .build();

        Log.d(TAG, "getProfileByEmail: Request Built: " + request.toString());
        try {
            Log.d(TAG, "getProfileByEmail: GET Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "getProfileByEmail: GET code: " + response.code());
            String json = response.body().string();
            int code = response.code();

            if (response.code() == 200){
                Log.d(TAG, "getProfileByEmail: body: " + json);
                Profile profile = Profile.Deserialize(json);
                return new ValueHolder(code, profile);
            }
            if (response.code() == 204){
                Log.d(TAG, "getProfileByEmail: Must Create Profile");
                return new ValueHolder(code, null);
            }
            else{
                Log.d(TAG, "getProfileByEmail: GET error code: " + response.code() + " message: " + response.message());
                return  new ValueHolder(code, null);
            }

        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "getProfileByEmail: error: " + e.getCause());
            return new ValueHolder(0, null);
        }
    }

    //Gets all Profiles by House
    //Returns a list of Profiles
    public LinkedList<Profile> getAllProfileByHouse(UUID houseId){
        String url = BASE_URL + "Profile/House/" + houseId.toString();
        Log.d(TAG, "getAllProfileByHouse: url: " + url);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .get()
                .build();

        Log.d(TAG, "getAllProfileByHouse: Request Built: " + request.toString());
        try {
            Log.d(TAG, "getAllProfileByHouse: GET Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "getAllProfileByHouse: GET code: " + response.code());
            String json = response.body().string();

            if (response.code() == 200){
                Log.d(TAG, "getAllProfileByHouse: body: " + json);
                LinkedList<Profile> list = Profile.DeserializeList(json);
                return  list;
            }
            else{
                Log.d(TAG, "getAllProfileByHouse: GET error code: " + response.code() + " message: " + response.message());
                return  null;
            }

        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "getAllProfileByHouse: error: " + e.getCause());
            return null;
        }
    }

    //Create new Profile
    //Returns a profileId
    public UUID createProfile(String fName, String lName, String email){
        String url = BASE_URL + "Profile/" + fName + '/' + lName + '/';
        Log.d(TAG, "createProfile: url: " + url + " body: " + email);

        RequestBody body = RequestBody.create(email, JSON);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .post(body)
                .build();

        Log.d(TAG, "createProfile: Request Built: " + request.toString());
        try{
            Log.d(TAG, "createProfile: POST Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "createProfile: POST code:" + response.code());
            String json = response.body().string();

            if (response.code() == 202){
                Log.d(TAG, "createProfile: body: " + json);
                Gson gson = new Gson();
                return gson.fromJson(json, UUID.class);
            }
            else{
                Log.d(TAG, "createProfile: POST error code: " + response.code() + " message: " + response.message());
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "createProfile: error: " + e.getCause());
            return null;
        }
    }

    //Update Profile houseId
    //Returns void
    public void updateHouseId(UUID profileId, UUID houseId){
        String url = BASE_URL + "Profile/" + profileId.toString() + "/House/" + houseId.toString();
        Log.d(TAG, "updateHouseId: url: " + url);

        RequestBody requestBody = RequestBody.create(new byte[0], null);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .post(requestBody)
                .build();

        Log.d(TAG, "updateHouseId: Request Built: " + request.toString());
        try{
            Log.d(TAG, "updateHouseId: POST Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "updateHouseId: POST code:" + response.code());
            if (response.code() == 202){
                Log.d(TAG, "updateHouseId: 202: " + response.message());
            }
            else{
                Log.d(TAG, "updateHouseId: POST error code: " + response.code() + " message: " + response.message());
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "updateHouseId: error: " + e.getCause());
        }
    }

    //Add Xp to Profile
    //Returns void
    public void addXp(UUID profileId){
        String url = BASE_URL + "Profile/" + profileId.toString() + "/XP";
        Log.d(TAG, "addXp: url: " + url);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .build();

        Log.d(TAG, "addXp: Request Built: " + request.toString());
        try{
            Log.d(TAG, "addXp: POST Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "addXp: POST code:" + response.code());
            if (response.code() == 200){
                Log.d(TAG, "addXp: 200: " + response.message());
            }
            else{
                Log.d(TAG, "addXp: POST error code: " + response.code() + " message: " + response.message());
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "addXp: error: " + e.getCause());
        }
    }


    //Delete Profile
    //Returns void
    public void deleteProfile(UUID profileId){
        String url = BASE_URL + "Profile/" + profileId.toString();
        Log.d(TAG, "deleteProfile: url: " + url);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .delete()
                .build();

        Log.d(TAG, "deleteProfile: Request Built: " + request.toString());
        try{
            Log.d(TAG, "deleteProfile: POST Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "deleteProfile: POST code:" + response.code());
            if (response.code() == 202){
                Log.d(TAG, "deleteProfile: 202: " + response.message());
            }
            else{
                Log.d(TAG, "deleteProfile: POST error code: " + response.code() + " message: " + response.message());
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "deleteProfile: error: " + e.getCause());
        }
    }

    //----Chore API Stuff----

}
