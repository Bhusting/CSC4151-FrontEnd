package com.example.tak_frontend.chore;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.Date;
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
}
