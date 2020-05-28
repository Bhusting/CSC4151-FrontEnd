package com.example.tak_frontend;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
    private MutableLiveData<House> houseLiveData = new MutableLiveData<>();
    private MutableLiveData<LinkedList<TaskDTO>> allTaskDTO = new MutableLiveData<>();



    private TakDao client;

    private String accessToken;
    private String idToken;
    private String email;
    private String fName;
    private String lName;
    private UUID houseIDRepo;
    private UUID profileIDRepo;
    private Application application;

    public TakRepository(Application application, String tempAccess, String tempID) {
        accessToken = tempAccess;
        idToken = tempID;
        allTaskDTO.setValue(new LinkedList<TaskDTO>());
        allTasks.setValue(new LinkedList<TaskData>());
        allChores.setValue(new LinkedList<ChoreData>());
        this.application = application;
        getProfile();
        client = new TakDao(accessToken);
        fetchAll();
    }


    @NotNull
    private void getProfile() {
        JWT jwt = new JWT(idToken);
        email = jwt.getClaim("email").asString();
        fName = jwt.getClaim("given_name").asString();
        lName = jwt.getClaim("family_name").asString();
    }

    public void fetchAll() {
        fetchProfileByEmail();
        fetchAllTasks();
    }

    //----------------------------------------Profile-----------------------------------------------

    //Checks if Profile has House
    public boolean hasHouse(){
        if (profileLiveData.getValue().houseId == null)
            return false;
        else
            return true;
    }


    //Sends GET request for Profile By Email
    public void fetchProfileByEmail(){
        FetchProfileByEmailAsync task = new FetchProfileByEmailAsync();
        task.execute(email);
    }
    private class FetchProfileByEmailAsync extends AsyncTask<String, Void, Profile>{

        @Override
        protected Profile doInBackground(String... strings) {
           return client.getProfileByEmail(strings[0]);
        }
        @Override
        protected void onPostExecute(Profile profile) {
                profileLiveData.postValue(profile);
                houseIDRepo = profile.houseId;
                profileIDRepo = profile.profileId;
                fetchLeaderboard();
                getHouseByProfileId();
        }
    }

    //Sends GET request for Profile By profileId
    public void fetchProfileById(){
        FetchProfileByIdAsync task = new FetchProfileByIdAsync();
        task.execute(profileIDRepo);
    }
    private class FetchProfileByIdAsync extends AsyncTask<UUID, Void, Profile>{
        @Override
        protected Profile doInBackground(UUID... uuids) {
            return client.getProfileById(uuids[0]);
        }

        @Override
        protected void onPostExecute(Profile profile) {
            profileLiveData.postValue(profile);
        }
    }

    //Sends POST request to Create Profile
    private void createProfile(){
        CreateProfileAsync task = new CreateProfileAsync();
        task.execute(fName, lName, email);
    }

    private class CreateProfileAsync extends AsyncTask<String, Void, UUID>{
        @Override
        protected UUID doInBackground(String... strings) {
            return client.createProfile(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(UUID uuid) {
            houseIDRepo = uuid;
        }
    }

    //----------------------------------------Leaderboard-------------------------------------------
    //Sends GET request for Leaderboard
    public void fetchLeaderboard(){
            FetchLeaderboardAsync task = new FetchLeaderboardAsync();
            task.execute(houseIDRepo);
    }
    private class FetchLeaderboardAsync extends AsyncTask<UUID, Void, LinkedList<Profile>>{

        @Override
        protected LinkedList<Profile> doInBackground(UUID... uuids) {
            return client.getAllProfileByHouse(uuids[0]);
        }

        @Override
        protected void onPostExecute(LinkedList<Profile> profiles) {
            LeaderboardData data = new LeaderboardData(profiles);
            leaderboardLiveData.postValue(data);
        }
    }
    //-----------------------------------------House------------------------------------------------
    //Sends GET request for House by Id
    public void getHouseById(){
        FetchHouseByIdAsync task = new FetchHouseByIdAsync();
        task.execute(houseIDRepo);
    }
    private class FetchHouseByIdAsync extends AsyncTask<UUID, Void, House>{
        @Override
        protected House doInBackground(UUID... uuids) {
            return client.getHouseById(houseIDRepo);
        }

        @Override
        protected void onPostExecute(House house) {
            Log.d(TAG, "onPostExecute: houseLiveData Posted");
            houseLiveData.postValue(house);
        }
    }

    //Sends GET request for House by profileId
    public void getHouseByProfileId(){
        FetchHouseByProfileIdAsync task = new FetchHouseByProfileIdAsync();
        task.execute(profileIDRepo);
    }
    private class FetchHouseByProfileIdAsync extends AsyncTask<UUID, Void, House>{
        @Override
        protected House doInBackground(UUID... uuids) {
            return client.getHouseByProfileId(uuids[0]);
        }

        @Override
        protected void onPostExecute(House house) {
            Log.d(TAG, "onPostExecute: houseLiveData Posted");
            houseLiveData.postValue(house);
        }
    }

    //Sends POST request to make a New House
    public void createHouse(String name){

    }
    private class CreateHouseAsync extends AsyncTask<String, Void, UUID>{
        @Override
        protected UUID doInBackground(String... strings) {
            return client.createHouse(strings[0]);
        }

        @Override
        protected void onPostExecute(UUID uuid) {
            houseIDRepo = uuid;
            fetchProfileById();
            Toast toast = Toast.makeText(application.getApplicationContext(), "House Created", Toast.LENGTH_SHORT);
        }
    }

    //Deletes a House
    public void deleteHouse(){

    }





    public void fetchAllTasks(){
        LinkedList<TaskData> list =
                null;
        try {
            if (profileLiveData.equals(null))
                list = client.fetchAllTasks(profileLiveData.getValue().houseId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(list != null)
          allTasks.postValue(list);
       else
           Log.d(TAG, "fetchAllTasks: response list null");

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

    public void fetchChoreByHouseId(UUID houseId){
        client.GetChoreByHouseId(houseId);
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
        return houseLiveData;
    }

    public LiveData<LinkedList<TaskDTO>> getTaskDTO(){
        return allTaskDTO;
    }

}
