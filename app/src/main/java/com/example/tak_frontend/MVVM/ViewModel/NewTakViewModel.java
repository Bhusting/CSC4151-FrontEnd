package com.example.tak_frontend.MVVM.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.auth0.android.jwt.JWT;
import com.example.tak_frontend.MVVM.Repositories.ChoreRepository;
import com.example.tak_frontend.MVVM.Repositories.HouseRepository;
import com.example.tak_frontend.MVVM.Repositories.ProfileRepository;
import com.example.tak_frontend.MVVM.Repositories.TaskRepository;
import com.example.tak_frontend.chore.ChoreData;
import com.example.tak_frontend.leaderboard.LeaderboardData;
import com.example.tak_frontend.profile.House;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.task.Task;
import com.example.tak_frontend.task.TaskDTO;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.UUID;

public class NewTakViewModel extends AndroidViewModel {
    private MutableLiveData<LinkedList<ChoreData>> allChores = new MutableLiveData<>();
    private MutableLiveData<Profile> profileLiveData = new MutableLiveData<>();
    private MutableLiveData<LinkedList<Task>> allTasks = new MutableLiveData<>();
    private MutableLiveData<LinkedList<Profile>> leaderboardLiveData = new MutableLiveData<>();
    private MutableLiveData<House> houseLiveData = new MutableLiveData<>();

    private final ProfileRepository _profileRepository;
    private final HouseRepository _houseRepository;
    private final TaskRepository _taskRepository;

    private String _email;
    private String _firstName;
    private String _lastName;

    public NewTakViewModel(@NonNull Application application, String accessToken, String idToken) {
        super(application);

        _profileRepository = new ProfileRepository(accessToken, idToken);
        _houseRepository =  new HouseRepository(accessToken);
        _taskRepository = new TaskRepository(accessToken);

        GetUserInfo(idToken);
    }

    @NotNull
    private void GetUserInfo(String idToken) {
        JWT jwt = new JWT(idToken);
        _email = jwt.getClaim("email").asString();
        _firstName = jwt.getClaim("given_name").asString();
        _lastName = jwt.getClaim("family_name").asString();
    }

    //
    //---------------------------------Profile------------------------------------------------------
    //

    public Profile getProfile() {

        Profile profile = _profileRepository.GetProfileByEmail(_email, _firstName, _lastName);

        profileLiveData.setValue(profile);

        return profile;
    }

    public LinkedList<Profile> getLeaderboard() {

        LinkedList<Profile> profiles = _profileRepository.GetAllProfilesInHouse(profileLiveData.getValue().houseId);

        leaderboardLiveData.setValue(profiles);

        return profiles;
    }

    public void AddXP() {

        _profileRepository.AddXP(profileLiveData.getValue().profileId);

        Profile profile = profileLiveData.getValue();

        profile.xp++;

        profileLiveData.setValue(profile);
    }

    public void UpdateHouse() {
        Profile profile = profileLiveData.getValue();
        House house = houseLiveData.getValue();

        _profileRepository.UpdateHouse(profile.profileId, house.houseID);

        profile.houseId = house.houseID;

        profileLiveData.setValue(profile);
    }

    //
    //---------------------------------House--------------------------------------------------------
    //


    public House GetHouse() {

        House house = _houseRepository.GetHouse(profileLiveData.getValue().houseId);

        houseLiveData.setValue(house);

        return house;
    }

    public House CreateHouse(String houseName) {

        House house = _houseRepository.CreateHouse(houseName);

        houseLiveData.setValue(house);

        return house;
    }

    //
    //-------------------------------------Task-----------------------------------------------------
    //

    public LinkedList<Task> GetAllTasks() {

        LinkedList<Task> tasks = _taskRepository.GetTasks(houseLiveData.getValue().houseID);

        allTasks.setValue(tasks);

        return tasks;
    }

    public LinkedList<Task> CreateTask(TaskDTO task) {

        _taskRepository.CreateTask(task);

        try {
            wait(2000);

            LinkedList<Task> tasks = _taskRepository.GetTasks(houseLiveData.getValue().houseID);

            allTasks.setValue(tasks);

            return tasks;
        }
        catch(Exception e) {

        }

        return null;
    }

    public void DeleteTask(UUID taskId) {

        _taskRepository.DeleteTask(taskId);

        LinkedList<Task> tasks = allTasks.getValue();

        int indexToDelete = 0;
        for (int x = 0; x < allTasks.getValue().size(); x++) {

            if (tasks.get(x).TaskId == taskId) {
                indexToDelete = x;
                break;
            }
        }

        tasks.remove(indexToDelete);

        allTasks.setValue(tasks);
    }

    //
    //---------------------------------LiveData-----------------------------------------------------
    //

    public LiveData<Profile> getLiveProfile() { return profileLiveData; }

    public LiveData<House> getLiveHouse() { return houseLiveData; }

    public LiveData<LinkedList<Profile>> getLiveLeaderboard() { return leaderboardLiveData; }

    public LiveData<LinkedList<ChoreData>> getLiveChores() { return allChores; }

    public LiveData<LinkedList<Task>> getLiveTasks() { return allTasks; }



}