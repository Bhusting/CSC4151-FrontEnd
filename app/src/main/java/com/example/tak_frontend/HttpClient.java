package com.example.tak_frontend;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient  extends AppCompatActivity {

    private static final String TAG = ".HttpClient";
    private static final String BASE_URL = "https://takkapp.azurewebsites.net/";
    public static final MediaType JSON = MediaType.parse("text/plain; charset=utf-8");
    private String accessToken;
    private JSONObject responseJSON;


    private OkHttpClient client;


    public HttpClient(String token){
        client = new OkHttpClient();
        accessToken = token;
    }

   public JSONObject httpGET(String URL){
        //HTTP Response stored here
        responseJSON = new JSONObject();
        responseJSON = null;
        //Build HTTP Request
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(URL)
                .get()
                .build();
        Log.d(TAG,"GET Request Built.");
        //Enqueue Request
        try {
             Log.d(TAG, "GET Enqueueing!");
             client.newCall(request).enqueue(
                     new Callback() {
                 @Override
                 public void onFailure(@NotNull Call call, @NotNull IOException e) {
                     Log.d(TAG, "GET Call Failed: " + e.getCause());
                     e.printStackTrace();
                 }
                 @Override
                 public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                     Log.d(TAG, "GET Response code: " + response.code());
                     String myResponse = response.body().string();
                     if (response.code() == 200) { //code = 200
                         try {
                             responseJSON = new JSONObject(myResponse);
                         } catch (JSONException e){
                             e.printStackTrace();
                             Log.d(TAG, "GET JSONException: " + e.getCause());
                         }
                     } else {
                         Log.d(TAG, "GET Bad HTTP response: " + response.message());
                         try {
                             responseJSON = new JSONObject(myResponse);
                         } catch (JSONException e) {
                             e.printStackTrace();
                             Log.d(TAG, "GET JSONException: " + e.getCause());
                         }
                     }
                 }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJSON;
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
