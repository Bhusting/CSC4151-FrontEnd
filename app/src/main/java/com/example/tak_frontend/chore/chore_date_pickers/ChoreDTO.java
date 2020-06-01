package com.example.tak_frontend.chore.chore_date_pickers;

import com.example.tak_frontend.chore.chore_date_pickers.dates.Days;
import com.example.tak_frontend.chore.chore_date_pickers.dates.Months;
import com.example.tak_frontend.chore.chore_date_pickers.dates.Weeks;

import java.sql.Time;
import java.util.List;

public class ChoreDTO {

    private String choreTitle;
    private String Status;
    private Time time;

    private List<Days> days;
    private List<Weeks>  weeks;
    private List<Months> months;

    public ChoreDTO(){

    }




}
