package com.example.tak_frontend.leaderboard;

import com.example.tak_frontend.profile.Profile;

public class LeadboardListItem extends LeaderboardData {


    private String name;
    private int xp;


    public LeadboardListItem(Profile p){
        name = p.FirstName;
        xp = p.XP;
    }

    public LeadboardListItem(String name, int xp){
        this.name = name;
        this.xp = xp;
    }

    public String getName() {
        return name;
    }

    public int getXp() {
        return xp;
    }



}
