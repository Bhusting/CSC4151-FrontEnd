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

    private static final String TAG = ".HttpClient";

    public static final MediaType JSON = MediaType.parse("text/plain; charset=utf-8");
    private static final String BASE_URL = "https://takkapp.azurewebsites.net/";
    private String accessToken;
    private JSONObject responseJSON;
    private JSONArray responseJSONArray;
    private TakRepository repository;


    private OkHttpClient client;


    public TakDao(String token, TakRepository repositoryArg){
        client = new OkHttpClient();
        repository = repositoryArg;
       // Log.d(TAG, "Constructor Application ID: " + this.getApplication().toString());
        accessToken = token;
    }


    //--All Task API Paths--

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

    //--All House API Paths--

    //Get House by houseId
    //Returns House
    public House getHouseById(UUID houseId){
        return new House();
    }

    //Get House by profileId
    //Returns House
    public House getHouseByProfile(UUID profileId){
        return new House();
    }

    //Create a New House
    //Returns new houseId
    public int createHouse(String houseName){
        return 1;
    }

    //Delete a House
    //Returns void
    public void deleteHouse(UUID houseId){

    }

    //--All Profile API Paths


    //Old code below, Don't Touch Please
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




}
