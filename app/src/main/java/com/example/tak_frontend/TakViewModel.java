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

import kotlinx.coroutines.scheduling.Task;


public class TakViewModel extends AndroidViewModel {

    private TakRepository repository;



    public TakViewModel(@NonNull Application application, String tempAccess, String tempID) {
        super(application);

        repository = new TakRepository(application, tempAccess, tempID);
        repository.fetchAll();
    }

    //Fetches all data from server
    public void refresh() {
        repository.fetchAll();
    }
    //GET for profile
    public void fetchProfile(){ repository.fetchProfile();
        }
    //GET for leaderboard
    public void fetchLeaderboard() { repository.fetchLeaderboard();
    }

    //POST changes Profile TODO
    public void update(Profile profile) {
        repository.update(profile);
    }

    //Delete HTTP request, TODO
    public void delete(Profile profile) {
        repository.delete(profile);
    }

    public void fetchTasks(){repository.fetchAllTasks();}



    //Create new TaskDTO on backend
    public void newTaskDTO(TaskDTO dto){
        repository.newTaskDTO(dto);
    }

    public void DEBUGaddTask(TaskData t){
        repository.add(t);
    }
    //Returns Profile LiveData
    public LiveData<Profile> getProfile(){
        return repository.getProfileLiveData();
        }
    //Returns Leaderboard List LiveData
    public LiveData<LeaderboardData> getLeaderboard(){
        return repository.getLeaderboardData();
        }

    public LiveData<LinkedList<TaskData>> getTasks(){return repository.getTasks(); }

    public LiveData<House> getHouse(){return repository.getHouse();}

    public LiveData<LinkedList<TaskDTO>> getTaskDTO() { return repository.getTaskDTO(); }


}

