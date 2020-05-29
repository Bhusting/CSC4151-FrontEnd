package com.example.tak_frontend.task;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class TaskDTO {

    private UUID TaskId;
    private String TaskName;
    private UUID HouseId;
    private String Duration;
    private UUID Channel;

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
