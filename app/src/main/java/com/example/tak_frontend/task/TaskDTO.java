package com.example.tak_frontend.task;

import com.example.tak_frontend.profile.House;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class TaskDTO {

    private UUID TaskId;
    private String TaskName;
    private UUID HouseId;
    private String Duration;
    private UUID Channel;



    @NotNull
    public static TaskDTO dishwasher(House house){
        TaskDTO task = new TaskDTO();
        task.TaskName = "Dishwasher";
        task.Duration = "1:00";
        task.HouseId = house.houseId;
        task.Channel = house.channel;
        task.TaskId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        return task;
    }

    @NotNull
    public static TaskDTO dryer(House house){
        TaskDTO task = new TaskDTO();
        task.TaskName = "Dryer";
        task.Duration = "1:30";
        task.HouseId = house.houseId;
        task.Channel = house.channel;
        task.TaskId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        return task;
    }

    @NotNull
    public static TaskDTO washer(House house){
        TaskDTO task = new TaskDTO();
        task.TaskName = "Washer";
        task.Duration = "1:30";
        task.HouseId = house.houseId;
        task.Channel = house.channel;
        task.TaskId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        return task;
    }

    public UUID getTaskId() {
        return TaskId;
    }

    public void setTaskId(UUID taskId) {
        TaskId = taskId;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public UUID getHouseId() {
        return HouseId;
    }

    public void setHouseId(UUID houseId) {
        HouseId = houseId;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public UUID getChannel() {
        return Channel;
    }

    public void setChannel(UUID channel) {
        Channel = channel;
    }

}
