package com.example.tak_frontend.task;

import com.example.tak_frontend.profile.Profile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class TaskData {

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    private UUID taskId;
    private String taskTitle;
    private String taskStatus;
    private Date taskTime;
    private UUID houseID;

    public TaskData(String title, String status, Date time, UUID house){

        taskTitle = title;
        taskStatus = status;
        taskTime = time;
        houseID = house;
    }

    public static final LinkedList<TaskData> toList(JSONObject object){

        return new LinkedList<TaskData>();
    }
}
