package com.example.tak_frontend.task;

import java.util.Date;

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

    public TaskData(String title, String status, Date time){

        taskTitle = title;
        taskStatus = status;
        taskTime = time;
    }
}
