package com.example.tak_frontend;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tak_frontend.chore.ChoreData;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.task.TaskData;

import java.util.List;

public class TakRepository {

    private LiveData<List<ChoreData>> allChores;

    private LiveData<List<TaskData>> allTasks;

    private LiveData<Profile> profileLiveData;

    public TakRepository(Application application){

    }
}
