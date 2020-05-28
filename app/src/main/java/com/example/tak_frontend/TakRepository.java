package com.example.tak_frontend;

import android.app.Application;
import android.os.AsyncTask;
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
        fetchProfile();
        fetchAllTasks();
    }

    //Sends GET request for Profile
    public void fetchProfile(){
        FetchProfileAsync task = new FetchProfileAsync();
        task.execute(email);
    }
    private class FetchProfileAsync extends AsyncTask<String, Void, Profile>{

        @Override
        protected Profile doInBackground(String... strings) {
           return client.getProfileByEmail(strings[0]);
        }
        @Override
        protected void onPostExecute(Profile profile) {
            profileLiveData.postValue(profile);
            fetchLeaderboard();
        }
    }

    //Sends GET request for Leaderboard
    public void fetchLeaderboard(){
        if (houseIDRepo != null) {
            FetchLeaderboardAsync task = new FetchLeaderboardAsync();
            task.execute(houseIDRepo);
        }
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

    public void createProfile() throws IOException {

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
