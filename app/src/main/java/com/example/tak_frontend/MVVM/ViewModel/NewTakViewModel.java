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

public class NewTakViewModel extends AndroidViewModel {
    private MutableLiveData<LinkedList<ChoreData>> allChores = new MutableLiveData<>();
    private MutableLiveData<Profile> profileLiveData = new MutableLiveData<>();
    private MutableLiveData<LinkedList<Task>> allTasks = new MutableLiveData<>();
    private MutableLiveData<LeaderboardData> leaderboardLiveData = new MutableLiveData<>();
    private MutableLiveData<House> houseLiveData = new MutableLiveData<>();
    private MutableLiveData<LinkedList<TaskDTO>> allTaskDTO = new MutableLiveData<>();

    private final ProfileRepository _profileRepository;
    private final HouseRepository _houseRepository;
    private final TaskRepository _taskRepository;

    private String _email;
    private String _firstName;
    private String _lastName;

    public NewTakViewModel(@NonNull Application application, String accessToken, String idToken) {
        super(application);

        _profileRepository = new ProfileRepository(accessToken, idToken);
        _houseRepository =  new HouseRepository();
        _taskRepository = new TaskRepository();

        GetUserInfo(idToken);
    }

    @NotNull
    private void GetUserInfo(String idToken) {
        JWT jwt = new JWT(idToken);
        _email = jwt.getClaim("email").asString();
        _firstName = jwt.getClaim("given_name").asString();
        _lastName = jwt.getClaim("family_name").asString();
    }

    public void setProfile() {

        Profile profile = _profileRepository.GetProfileByEmail(_email, _firstName, _lastName);

        profileLiveData.setValue(profile);
    }

    public LiveData<Profile> getProfile() {
        return profileLiveData;
    }






}
