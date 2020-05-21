package com.example.tak_frontend.chore;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.Date;

public class ChoreData {
    String choreStatus;
    String choreTitle;
    Date choreTime;

    ChoreData(){
        choreTitle = "temp";
        choreStatus = "temp";
        choreTime = null;
    }

    ChoreData (String tempStatus, String tempTitle, Date tempTime){
        choreStatus = tempStatus;
        choreTitle = tempTitle;
        choreTime = tempTime;
    }
}
