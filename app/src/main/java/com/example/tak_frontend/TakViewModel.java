package com.example.tak_frontend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.tak_frontend.chore.ChoreData;
import com.example.tak_frontend.leaderboard.LeaderboardData;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.task.TaskDTO;
import com.example.tak_frontend.task.TaskData;
import java.util.LinkedList;
import java.util.UUID;


public class TakViewModel extends AndroidViewModel {

    private TakRepository repository;

    public TakViewModel(@NonNull Application application, String tempAccess, String tempID) {
        super(application);

        repository = new TakRepository(application, tempAccess, tempID);
    }

    //Fetches all data from server
    public void refresh() {
        repository.fetchAll();
    }



    //-----------------------------------------House------------------------------------------------
    public boolean createHouse(String name){
        if(repository.hasHouse())
            return false;
        else {
            repository.createHouse(name);
            return true;
        }
    }
    public void joinHouse(){
        //TODO
    }
    public void fetchHouse(){
        repository.getHouseById();
    }
    //-----------------------------------------Profile----------------------------------------------
    //POST changes Profile TODO
    public void update(Profile profile) {

    }
    //GET for profile
    public void fetchProfile(){ repository.fetchProfileByEmail(); }
    //-----------------------------------------Task-------------------------------------------------
    public void fetchTasks(){repository.fetchAllTasks();}
    //Create new TaskDTO on backend
    public void newTaskDTO(TaskDTO dto){
        repository.newTaskDTO(dto);
    }

    //-----------------------------------------Chore------------------------------------------------
    // Call this from activity/fragment to fetch chore bby house id
    public void fetchChoreByHouseId(UUID houseId){ repository.fetchChoreByHouseId(houseId); }
    //-----------------------------------------Leaderboard------------------------------------------
    //GET for leaderboard
    public void fetchLeaderboard() { repository.fetchLeaderboard();
    }

    //Delete HTTP request, TODO
    public void delete(Profile profile) { }





    //LiveData Getters
    public LiveData<Profile> getProfile(){
        return repository.getProfileLiveData();
        }
    public LiveData<LeaderboardData> getLeaderboard(){
        return repository.getLeaderboardData();
        }
    public LiveData<LinkedList<TaskData>> getTasks(){return repository.getTasks(); }
    public LiveData<House> getHouse(){return repository.getHouse();}
    public LiveData<LinkedList<TaskDTO>> getTaskDTO() { return repository.getTaskDTO(); }


}

