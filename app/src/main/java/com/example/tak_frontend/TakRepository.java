package com.example.tak_frontend;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.auth0.android.jwt.JWT;
import com.example.tak_frontend.chore.ChoreData;
import com.example.tak_frontend.leaderboard.LeaderboardData;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.task.TaskDTO;
import com.example.tak_frontend.task.TaskData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import cz.msebera.android.httpclient.client.ResponseHandler;
import okhttp3.internal.concurrent.Task;

public class TakRepository {


    private  static  final  String TAG = ".TakRepository";
    private static final String BASE_URL = "https://takkapp.azurewebsites.net/";

/*    private LiveData<List<ChoreData>> allChores;

    private LiveData<List<TaskData>> allTasks;

    */

    private MutableLiveData<LinkedList<ChoreData>> allChores = new MutableLiveData<>();
    private MutableLiveData<Profile> profileLiveData = new MutableLiveData<>();
    private MutableLiveData<LinkedList<TaskData>> allTasks = new MutableLiveData<>();
    private MutableLiveData<LeaderboardData> leaderboardLiveData = new MutableLiveData<>();
    private MutableLiveData<House> house = new MutableLiveData<>();
    private MutableLiveData<LinkedList<TaskDTO>> allTaskDTO = new MutableLiveData<>();


    private TakDao client;

    private String accessToken;
    private String idToken;
    private String email;
    private String fName;
    private String lName;
    private UUID houseIDRepo;

    public TakRepository(Application application, String tempAccess, String tempID) {
        accessToken = tempAccess;
        idToken = tempID;
        allTaskDTO.setValue(new LinkedList<TaskDTO>());
        allTasks.setValue(new LinkedList<TaskData>());
        allChores.setValue(new LinkedList<ChoreData>());
        getProfile();
        client = new TakDao(accessToken, this);
    }


    @NotNull
    private void getProfile() {
        JWT jwt = new JWT(idToken);
        email = jwt.getClaim("email").asString();
        fName = jwt.getClaim("given_name").asString();
        lName = jwt.getClaim("family_name").asString();
    }

    public void fetchAll() {
        fetchProfile();
        fetchLeaderboard();
        fetchAllTasks();
    }

    public void insert(ChoreData data) {

    }

    public void insert(TaskData data) {

    }

    public void insert(JSONObject responseJson) {

    }
    //Add
    protected void add(TaskData t){

        LinkedList<TaskData> list;

        if (allTasks.getValue().size() == 0){
            list = new LinkedList<>();
            list.add(t);
            allTasks.setValue(list);
        }
        else{
            list = allTasks.getValue();
            list.addLast(t);
            allTasks.setValue(list);
        }

    }


/*    static protected class profileFunc<T>{
        JSONObject responseJson;
        R profile;

        profileFunc(JSONObject jsonArg){
            responseJson = jsonArg;
            if  (responseJson != null){
                try {
                    UUID profID = UUID.fromString(responseJson.getString("profileId"));
                    String fnTemp = responseJson.getString("firstName");
                    String lnTemp = responseJson.getString("lastName");
                    int xp = responseJson.getInt("xp");
                    UUID houseID = UUID.fromString(responseJson.getString("houseId"));
                    houseIDRepo = UUID.fromString(responseJson.getString("houseId"));
                    String emailTemp = responseJson.getString("email");
                    Profile profile = new Profile(profID, fnTemp, lnTemp, xp, houseID, emailTemp);
                    profileLiveData.setValue(profile);
                    Log.d(TAG, "Set Profile Data");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Insert Profile JSON Exception");
                }
            } else {
                Log.d(TAG, "httpGET json Null");
            }

        }

    }*/




    //Sets Profile LiveData From JSON response
    public void setProfile(Profile p){
        if(p != null){
            Log.d(TAG, "Profile added to LiveData");
            profileLiveData.setValue(p);
            houseIDRepo = p.HouseId;
        } else
            Log.d(TAG, "Profile = null, not added");
    }
    public void setLeaderboard(LeaderboardData l){
        if(l != null){
            Log.d(TAG, "Leaderboard added to LiveData");
            leaderboardLiveData.setValue(l);
        } else
            Log.d(TAG, "Leaderboard = null, not added");
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
    //Sends GET request for Profile
    public void fetchProfile(){

        String url = BASE_URL + "Profile/Email";
        Log.d(TAG, "httpGETProfile: URL : " + url);

        try {
            client.httpGETProfile(url, JsonConverter.GETProfile, email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sends GET request for Leaderboard
    public void fetchLeaderboard(){
        if (houseIDRepo != null) {
            String url = BASE_URL + "Profile/House/" + houseIDRepo.toString();
            Log.d(TAG, "fetchLeaderboard: URL: " + url);
            try {
                client.httpGET(url, JsonConverter.GETLeaderboard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void fetchAllTasks(){
/*      LinkedList<TaskData> list =
               client.fetchAllTasks(profileLiveData.getValue().HouseId.toString());

       if(list != null)
          allTasks.postValue(list);
       else
           Log.d(TAG, "fetchAllTasks: response list null");*/

}

    public void createProfile() throws IOException {
        getProfile();
        String url = BASE_URL + "Profile/" + fName + '/' + lName;
        Log.d(TAG, "createProfile: URL: " + url + ' ' + email);

        try {
            client.httpPOST(url, email);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    //TODO sent POST request for new TaskDTO
    public void newTaskDTO(TaskDTO dto){
        LinkedList<TaskDTO> tempDTO = new LinkedList<>();
        tempDTO = allTaskDTO.getValue();
        tempDTO.addLast(dto);
        allTaskDTO.setValue(tempDTO);
        //Stuff

        //Get return from TakDao, add new task to tasklist
        //Need to be async, will update data
/*        LinkedList<TaskData> tempTask = new LinkedList<>();
        tempTask = allTasks.getValue();
        tempTask.addLast();
        allTasks.setValue(tempTask);*/

    }


    public List<ChoreData> getAllChores() {
        return allChores.getValue();
    }

    public List<TaskData> getAllTasks() {
        return allTasks.getValue();
    }

    public MutableLiveData<Profile> getProfileLiveData() {
        return profileLiveData;
    }

    public MutableLiveData<LeaderboardData> getLeaderboardData(){
        return leaderboardLiveData;
    }

    public LiveData<LinkedList<TaskData>> getTasks() { return allTasks; }

    public LiveData<House> getHouse()  {

       // House temp = new House();
        //temp.HouseID = profileLiveData.getValue().HouseId;
        //house.postValue(temp);
        return house;
    }

    public LiveData<LinkedList<TaskDTO>> getTaskDTO(){
        return allTaskDTO;
    }

}
