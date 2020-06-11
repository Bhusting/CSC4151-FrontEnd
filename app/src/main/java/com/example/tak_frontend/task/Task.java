package com.example.tak_frontend.task;

import com.example.tak_frontend.profile.Profile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.UUID;

public class Task {

    public UUID taskId;

    public String taskName;

    public String endTime;

    public UUID houseId;

    public Task(){

    }

    public Task(String name, String time){
        taskName = name;
        endTime = time;
    }

    public static LinkedList<Task> toList(String json) {
        Gson gson = new Gson();

        Type listType = new TypeToken<LinkedList<Task>>() {}.getType();

        LinkedList<Task> list = gson.fromJson(json, listType);

        return list;
    }


    public static Task deserialize(String json){
         Gson gson = new Gson();

         Task task = gson.fromJson(json, Task.class);

         return task;
    }
}
