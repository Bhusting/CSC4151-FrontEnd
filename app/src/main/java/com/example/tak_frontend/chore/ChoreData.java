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
    public String ChoreName;
    public String CompletionDate;
    public String CompletionTime;
    public UUID HouseId;
    public short ChoreTypeId;

    public ChoreData() {
        ChoreId = UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public ChoreData(ChoreData data){
        ChoreId = data.ChoreId;
        ChoreName = data.ChoreName;
        CompletionDate = data.CompletionDate;
        CompletionTime = data.CompletionTime;
        HouseId = data.HouseId;
        ChoreTypeId = data.ChoreTypeId;
    }
    public static LinkedList<ChoreData> DeserializeList(String json) {

        Gson gson = new Gson();

        Type listType = new TypeToken<LinkedList<Task>>() {}.getType();
        LinkedList<ChoreData> list = gson.fromJson(json, listType);

        return list;

    }
}
