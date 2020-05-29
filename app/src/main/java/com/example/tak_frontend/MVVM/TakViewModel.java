package com.example.tak_frontend.MVVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tak_frontend.profile.House;
import com.example.tak_frontend.leaderboard.LeaderboardData;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.task.Task;
import com.example.tak_frontend.task.TaskDTO;

import java.util.LinkedList;

/*
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
    //public LiveData<LinkedList<Task>> getTasks(){return repository.getTasks(); }
    public LiveData<House> getHouse(){return repository.getHouse();}
    public LiveData<LinkedList<TaskDTO>> getTaskDTO() { return repository.getTaskDTO(); }


}
*/

