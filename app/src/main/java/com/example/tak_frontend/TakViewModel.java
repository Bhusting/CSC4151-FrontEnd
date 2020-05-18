package com.example.tak_frontend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tak_frontend.chore.ChoreData;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.task.TaskData;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TakViewModel extends AndroidViewModel {

    private TakRepository repository;
    private String idToken;
    private String accessToken;
/*    private LiveData<List<ChoreData>> allChores;

    private LiveData<List<TaskData>> allTasks;*/

    private MutableLiveData<List<ChoreData>> allChores = new MutableLiveData<>();
    private MutableLiveData<Profile> allProfile = new MutableLiveData<>();
    private MutableLiveData<List<TaskData>> allTasks = new MutableLiveData<>();



    public TakViewModel(@NonNull Application application) {
        super(application);
        repository = new TakRepository(application);
    }

    public void getProfileRequest(){
        repository.getProfileRequest();
    }


    public void setTokens(String id, String access){
        idToken = id;
        accessToken = access;
        repository.ifTokenNotSet(accessToken, idToken);
    }
    public void refresh(){
        repository.fetchAll();
    }
/*    public void insert(Profile profile){
        repository.insert(profile);
    }*/
    public void update(Profile profile){
        repository.update(profile);
    }
    public void delete(Profile profile){
        repository.delete(profile);
    }
    public Profile getProfile(){
        return allProfile.getValue();
    }
}

