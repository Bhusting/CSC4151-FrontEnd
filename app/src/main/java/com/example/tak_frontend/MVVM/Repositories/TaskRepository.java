package com.example.tak_frontend.MVVM.Repositories;

import com.example.tak_frontend.MVVM.Dao.TakDao;
import com.example.tak_frontend.MVVM.Dao.TakRequestBuilder;
import com.example.tak_frontend.profile.House;
import com.example.tak_frontend.task.Task;
import com.example.tak_frontend.task.TaskDTO;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.Request;

public class TaskRepository {

    private final Gson gson = new Gson();

    private TakRequestBuilder _requestBuilder;

    public TaskRepository(String accessToken) {
        _requestBuilder = new TakRequestBuilder(accessToken);
    }

    public LinkedList<Task> GetTasks(UUID houseId) {

        Request request = _requestBuilder.BuildGet("Task/House/" + houseId.toString());

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();

            LinkedList<Task> tasks = Task.toList(json);

            return tasks;
        }
        catch(Exception e) {

        }

        return null;

    }

    public boolean CreateTask(TaskDTO task) {
        Request request = _requestBuilder.BuildPost("Task", MediaType.parse("application/json; charset=utf-8"), gson.toJson(task));

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();

            return true;
        }
        catch(Exception e) {

        }

        return false;

    }

    public boolean DeleteTask(UUID taskId) {
        Request request = _requestBuilder.BuildDelete("Task/" + taskId.toString());

        try {
            TakDao takDao = new TakDao();
            String json = takDao.execute(request).get();

            return true;
        }
        catch(Exception e) {

        }

        return false;
    }


}
