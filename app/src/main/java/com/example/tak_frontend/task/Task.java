package com.example.tak_frontend.task;

import com.example.tak_frontend.profile.Profile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.UUID;

public class Task {

    public UUID TaskId;

    public String TaskName;

    public String EndTime;

    public UUID HouseId;

    public static LinkedList<Task> toList(String json) {
        Gson gson = new Gson();

        Type listType = new TypeToken<LinkedList<Task>>() {}.getType();

        LinkedList<Task> list = gson.fromJson(json, listType);

        return list;
    }
}
