package com.example.tak_frontend.chore;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.tak_frontend.task.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChoreData {
    public UUID choreId;
    public String choreName;
    public String completionDate;
    public String completionTime;
    public UUID houseId;
    public short choreTypeId;



    public ChoreData() {
        choreId = UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public ChoreData(String name, String date, String time){
        choreName = name;
        completionDate = date;
        completionTime = time;
    }
    public ChoreData(ChoreData data){
        choreId = data.choreId;
        choreName = data.choreName;
        completionDate = data.completionDate;
        completionTime = data.completionTime;
        houseId = data.houseId;
        choreTypeId = data.choreTypeId;
    }

    public static ChoreData dummy(){
        ChoreData data = new ChoreData();
        data.choreName = "dummy";
        data.completionDate = "00/00/00";
        data.completionTime = "00:00";
        data.choreTypeId = 1;
        return data;
    }
    public static LinkedList<ChoreData> DeserializeList(String json) {

        Gson gson = new Gson();

        Type listType = new TypeToken<LinkedList<ChoreData>>() {}.getType();
        LinkedList<ChoreData> list = gson.fromJson(json, listType);

        return list;

    }
}
