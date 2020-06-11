package com.example.tak_frontend.task;

import com.example.tak_frontend.profile.House;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TaskDTO {

    private UUID taskId;
    private String taskName;
    private UUID houseId;
    private String duration;
    private UUID channel;



    @NotNull
    public static TaskDTO dishwasher(House house){
        TaskDTO task = new TaskDTO();
        task.taskName = "Dishwasher";
        task.duration = "01:00";
        task.houseId = house.houseId;
        task.channel = house.channel;
        task.taskId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        return task;
    }

    @NotNull
    public static TaskDTO dryer(House house){
        TaskDTO task = new TaskDTO();
        task.taskName = "Dryer";
        task.duration = "01:30";
        task.houseId = house.houseId;
        task.channel = house.channel;
        task.taskId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        return task;
    }

    @NotNull
    public static TaskDTO washer(House house){
        TaskDTO task = new TaskDTO();
        task.taskName = "Washer";
        task.duration = "01:30";
        task.houseId = house.houseId;
        task.channel = house.channel;
        task.taskId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        return task;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public UUID getHouseId() {
        return houseId;
    }

    public void setHouseId(UUID houseId) {
        this.houseId = houseId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public UUID getChannel() {
        return channel;
    }

    public void setChannel(UUID channel) {
        this.channel = channel;
    }

}
