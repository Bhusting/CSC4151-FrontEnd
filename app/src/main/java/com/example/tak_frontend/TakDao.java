package com.example.tak_frontend;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tak_frontend.leaderboard.LeaderboardData;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.task.TaskDTO;
import com.example.tak_frontend.task.TaskData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Function;

import okhttp3.Call;
import okhttp3.Callback;
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

    //----All House API Paths----

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
                Log.d(TAG, "getHouseById: GET error code: " + response.code() + " message: " + response.message());
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
                Log.d(TAG, "getHouseByProfile: GET error code: " + response.code() + " message: " + response.message());
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
    public UUID createHouse(String houseName){
        String url = BASE_URL + "House/" + houseName;
        Log.d(TAG, "createHouse: url: " + url);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .get()
                .build();
        Log.d(TAG, "createHouse: Request Built" + request.toString());

        try{
            Log.d(TAG, "createHouse: GET Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "createHouse: GET code: " + response.code());
            String json = response.body().string();
            if (response.code() == 200){
                Log.d(TAG, "createHouse: body: " + json);
                Gson gson = new Gson();
                UUID uuid = gson.fromJson(json, UUID.class);
                return uuid;
            }
            else{
                Log.d(TAG, "createHouse: GET error code: " + response.code() + " message: " + response.message());
                return  null;
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

    //----All Profile API Paths----

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
                Log.d(TAG, "getProfileById: GET error code: " + response.code() + " message: " + response.message());
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
    public Profile getProfileByEmail(String email){
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

            if (response.code() == 200){
                Log.d(TAG, "getProfileByEmail: body: " + json);
                Profile profile = Profile.Deserialize(json);
                return profile;
            }
            if (response.code() == 204){
                Log.d(TAG, "getProfileByEmail: Must Create Profile");
                Profile profile = new Profile();
                profile.firstName = "null";
                return profile;
            }
            else{
                Log.d(TAG, "getProfileByEmail: GET error code: " + response.code() + " message: " + response.message());
                return  null;
            }

        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "getProfileByEmail: error: " + e.getCause());
            return null;
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

            if (response.code() == 200){
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

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .build();

        Log.d(TAG, "updateHouseId: Request Built: " + request.toString());
        try{
            Log.d(TAG, "updateHouseId: POST Executing");
            Response response = client.newCall(request).execute();
            Log.d(TAG, "updateHouseId: POST code:" + response.code());
            if (response.code() == 200){
                Log.d(TAG, "updateHouseId: 200: " + response.message());
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
            if (response.code() == 200){
                Log.d(TAG, "deleteProfile: 200: " + response.message());
            }
            else{
                Log.d(TAG, "deleteProfile: POST error code: " + response.code() + " message: " + response.message());
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "deleteProfile: error: " + e.getCause());
        }
    }

    //----Chore API Stuff----\
    public void GetChoreByHouseId(UUID houseId) {
        String url = BASE_URL + "House/" + houseId.toString();
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            // Check the api request responded successfully

            boolean isSuccess = response.isSuccessful();
            if(isSuccess) {
                Log.i("RUQAYA -- Func_s", " getAllProfileByHouse() .(TakDao.java:345)");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Old code below, Don't Touch Please
/*
    public void convert(Object obj, JsonConverter type) throws JSONException {

        try {
            switch (type){

                case GETProfile:
                    JSONObject response = (JSONObject) obj;
                    repository.setProfile(Profile.fromJson(response));
                    break;
                case GETLeaderboard:
                    JSONArray responseArray = (JSONArray) obj;
                    Log.d(TAG, type.toString() + ':' + responseArray.toString());
                    repository.setLeaderboard(LeaderboardData.fromJson(responseArray));
                    break;
                default:
            }
        } catch (JSONException | IOException e ) {
            e.printStackTrace();
            Log.d(TAG, type.toString() + ", convert JSONexception");
        }


    }


    public void httpGETProfile(String URL, @NotNull JsonConverter type, String email) throws IOException {
        //Build HTTP Request
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Email", email)
                .url(URL)
                .get()
                .build();
        Log.d(TAG, type.toString() + ", GET Request Built.");
        //Enqueue Request
        try {
            Log.d(TAG, type.toString() + ", GET Enqueueing!");
            client.newCall(request).enqueue(
                    new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d(TAG, type.toString() + ", GET Call Failed: " + e.getCause());
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Log.d(TAG, type.toString() + ", GET Response code: " + response.code());
                            final String myResponse = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (response.code() == 200) { //code = 200
                                        try {
                                            if(type == JsonConverter.GETProfile){
                                                responseJSON = new JSONObject(myResponse);
                                                convert(responseJSON, type);
                                            }if(type == JsonConverter.GETLeaderboard){
                                                responseJSONArray = new JSONArray(myResponse);
                                                convert(responseJSONArray, type);
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d(TAG, type.toString() + ", GET JSONException: " + e.getCause());
                                        }
                                    } else {
                                        if (response.code() == 204){
                                            try {
                                                repository.createProfile();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        Log.d(TAG, type.toString() + ", GET Bad HTTP response: " + response.message());
                                        try {
                                            responseJSON = new JSONObject(myResponse);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d(TAG, type.toString() + ", GET JSONException: " + e.getCause());
                                        }
                                    }
                                }
                            });
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void httpGET(String URL, @NotNull JsonConverter type) throws IOException {
       //HTTP Response stored here
       responseJSON = new JSONObject();
       responseJSONArray = new JSONArray();
       responseJSON = null;
       responseJSONArray = null;
       //Build HTTP Request
       Request request = new Request.Builder()
               .addHeader("Authorization", "Bearer " + accessToken)
               .url(URL)
               .get()
               .build();
       Log.d(TAG, type.toString() + ", GET Request Built.");
       //Enqueue Request
       try {
           Log.d(TAG, type.toString() + ", GET Enqueueing!");
           client.newCall(request).enqueue(
                   new Callback() {
                       @Override
                       public void onFailure(@NotNull Call call, @NotNull IOException e) {
                           Log.d(TAG, type.toString() + ", GET Call Failed: " + e.getCause());
                           e.printStackTrace();
                       }

                       @Override
                       public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                           Log.d(TAG, type.toString() + ", GET Response code: " + response.code());
                           final String myResponse = response.body().string();
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {

                                   if (response.code() == 200) { //code = 200
                                       try {
                                           if(type == JsonConverter.GETProfile){
                                               responseJSON = new JSONObject(myResponse);
                                               convert(responseJSON, type);
                                           }if(type == JsonConverter.GETLeaderboard){
                                               responseJSONArray = new JSONArray(myResponse);
                                               convert(responseJSONArray, type);
                                           }


                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                           Log.d(TAG, type.toString() + ", GET JSONException: " + e.getCause());
                                       }
                                   } else {
                                       if (response.code() == 204){
                                           try {
                                               repository.createProfile();
                                           } catch (IOException e) {
                                               e.printStackTrace();
                                           }
                                       }
                                       Log.d(TAG, type.toString() + ", GET Bad HTTP response: " + response.message());
                                       try {
                                           responseJSON = new JSONObject(myResponse);
                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                           Log.d(TAG, type.toString() + ", GET JSONException: " + e.getCause());
                                       }
                                   }
                               }
                           });
                       }
                   });
       } catch (Exception e) {
           e.printStackTrace();
       }
    }


    public void httpPOST(String url, String json) throws IOException {
       Log.d(TAG, "POST url: " + url);
       Log.d(TAG, "POST json: " + json);

       RequestBody body = RequestBody.create(json, JSON);

       Request request = new Request.Builder()
               .addHeader("Authorization", "Bearer " + accessToken)
               .url(url)
               .post(body)
               .build();
       Log.d(TAG,"POST Request Built.");

       try {
           Log.d(TAG, "POST Enqueueing!");
           client.newCall(request).enqueue(new Callback() {
               @Override
               public void onFailure(@NotNull Call call, @NotNull IOException e) {
                   Log.d(TAG, "POST Call Failed: " + e.getCause());
                   e.printStackTrace();
               }

               @Override
               public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                   Log.d(TAG, "POST Response code: " + response.code());
                   String myResponse = response.body().string();
                   if (response.code() == 200) { //code = 200
                       repository.fetchProfile();
                       getParent().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               try {
                                   responseJSON = new JSONObject(myResponse);
                               } catch (JSONException e){
                                   e.printStackTrace();
                                   Log.d(TAG, "POST JSONException: " + e.getCause());
                               }
                           }
                       });
                   }
                   else {
                       Log.d(TAG, "POST Bad HTTP response: " + response.message());
                       try {
                           responseJSON = new JSONObject(myResponse);
                       } catch (JSONException e) {
                           e.printStackTrace();
                           Log.d(TAG, "POST JSONException: " + e.getCause());
                       }
                   }
               }
           });
       }catch (Exception e){
           e.printStackTrace();
       }

    }
*/




}
