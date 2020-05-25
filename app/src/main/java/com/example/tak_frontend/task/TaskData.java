package com.example.tak_frontend.task;

import com.example.tak_frontend.profile.Profile;

import java.util.Date;
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

    private String taskTitle;
    private String taskStatus;
    private Date taskTime;
    private UUID houseID;

    public TaskData(String title, String status, Date time, Profile p){

        taskTitle = title;
        taskStatus = status;
        taskTime = time;
        houseID = p.HouseId;
    }
}
