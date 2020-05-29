package com.example.tak_frontend.MVVM;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.auth0.android.jwt.JWT;
import com.example.tak_frontend.profile.House;
import com.example.tak_frontend.chore.ChoreData;
import com.example.tak_frontend.leaderboard.LeaderboardData;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.task.TaskDTO;
import com.example.tak_frontend.task.TaskData;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

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
        //fetchAllTasks();
    }

    //Resets all Data
    public void clear(){
        allChores = new MutableLiveData<>();
        profileLiveData = new MutableLiveData<>();
        allTasks = new MutableLiveData<>();
        leaderboardLiveData = new MutableLiveData<>();
        houseLiveData = new MutableLiveData<>();

    }

    //----------------------------------------Profile-----------------------------------------------

    //Checks if Profile has House
    public boolean hasHouse(){
        if (profileLiveData.getValue().houseId.toString() != "00000000-0000-0000-0000-000000000000")
            return false;
        else
            return true;
    }


    //Sends GET request for Profile By Email
    //First request made
    public void fetchProfileByEmail(){
        FetchProfileByEmailAsync task = new FetchProfileByEmailAsync();
        task.execute(email);
    }


    private class FetchProfileByEmailAsync extends AsyncTask<String, Void, ValueHolder>{

        @Override
        protected ValueHolder doInBackground(String... strings) {
           return client.getProfileByEmail(strings[0]);
        }
        @Override
        protected void onPostExecute(ValueHolder valueHolder) {
            if(valueHolder.getCode() == 200){
                profileLiveData.postValue((Profile) valueHolder.getObject());
                houseIDRepo = ((Profile) valueHolder.getObject()).houseId;
                profileIDRepo = ((Profile) valueHolder.getObject()).profileId;
                fetchLeaderboard();
                getHouseByProfileId();
            }
            if (valueHolder.getCode() == 204){
                createProfile();
            } else{
                Log.d(TAG, "onPostExecute: Something went very wrong. . .");
            }
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
            profileIDRepo = profile.profileId;
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
            profileIDRepo = uuid;
            fetchProfileById();
        }
    }

    //Sends POST request to delete profile
    public void deleteProfile(){
        DeleteProfileAsync task = new DeleteProfileAsync();
        task.execute(profileIDRepo);

    }
    private class DeleteProfileAsync extends AsyncTask<UUID, Void, Void> {
        @Override
        protected Void doInBackground(UUID... uuids) {
            client.deleteProfile(uuids[0]);
            return null;
        }
    }

    //Sends POST request to reset houseId
    public void resetHouse(){
        ResetHouseAsync task = new ResetHouseAsync();
        task.execute();
    }

    private class ResetHouseAsync extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
            client.updateHouseId(profileIDRepo, uuid);
            return null;
        }
    }

    //Sends POST request to update houseId
    public void updateHouse(UUID uuid){
        UpdateHouseAsync task = new UpdateHouseAsync();
        task.execute(uuid);
    }

    private class UpdateHouseAsync extends AsyncTask<UUID, Void, Void>{
        @Override
        protected Void doInBackground(UUID... uuids) {
            client.updateHouseId(profileIDRepo, uuids[0]);
            return null;
        }
    }

/*    //Sends
    public boolean joinHouse(UUID uuid){
        JoinHouse task = new JoinHouse();
        task.execute(uuid);
    }*/

    private class JoinHouse extends AsyncTask<UUID, Boolean, Boolean>{
        @Override
        protected Boolean doInBackground(UUID... uuids) {
            House house = client.getHouseById(uuids[0]);
            if (house == null){
                return false;
            } else {
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
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
            return client.getHouseById(uuids[0]);
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
        CreateHouseAsync task = new CreateHouseAsync();
        task.execute(name);

    }
    private class CreateHouseAsync extends AsyncTask<String, Void, ValueHolder>{
        @Override
        protected ValueHolder doInBackground(String... strings) {
            return client.createHouse(strings[0]);
        }

        @Override
        protected void onPostExecute(ValueHolder holder) {
            if (holder.getCode() == 202) {
                Log.d(TAG, "onPostExecute: House Created code: " + holder.getCode() + " uuid: " + ((UUID) holder.getObject()).toString());
                houseIDRepo = (UUID) holder.getObject();
                updateHouse(houseIDRepo);
                getHouseById();
            } else {
                Log.d(TAG, "onPostExecute: error code: " + holder.getCode());
            }

        }
    }

    public void isHouseValid(UUID uuid){
        IsHouseValidAsync task = new IsHouseValidAsync();
        HelperClass helperClass = new HelperClass();
        helperClass.uuid = uuid;
        helperClass.bool = false;
        task.execute(helperClass);
    }
    private class HelperClass{
        public UUID uuid;
        public boolean bool;
    }
    private class IsHouseValidAsync extends AsyncTask<HelperClass, Void, HelperClass>{
        @Override
        protected HelperClass doInBackground(HelperClass... helperClasses) {
            HelperClass returnHelper = new HelperClass();
            returnHelper.bool = client.isHouseValid(helperClasses[0].uuid);
            returnHelper.uuid = helperClasses[0].uuid;
            return returnHelper;
        }

        @Override
        protected void onPostExecute(HelperClass helperClass) {
           if(helperClass.bool){
               UpdateHouseAsync task = new UpdateHouseAsync();
               task.execute(helperClass.uuid);

           } else {

           }
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
    public void newTask(TaskDTO dto){
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

    public LiveData<House> getHouse()  { return houseLiveData; }


}
