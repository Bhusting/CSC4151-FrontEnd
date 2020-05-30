package com.example.tak_frontend.chore;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.tak_frontend.task.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class ChoreData {
    public UUID ChoreId;
    public String ChoreString;
    public String CompletionTime;

    public static LinkedList<ChoreData> DeserializeList(String json) {

        Gson gson = new Gson();

        Type listType = new TypeToken<LinkedList<Task>>() {}.getType();
        LinkedList<ChoreData> list = gson.fromJson(json, listType);

        return list;
    }
}
