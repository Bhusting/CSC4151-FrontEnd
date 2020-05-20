package com.example.tak_frontend.leaderboard;

import com.example.tak_frontend.profile.Profile;

import java.util.LinkedList;
import java.util.List;

public class LeaderboardData
{
    
    private LinkedList<Profile> leaderboard;

    public LeaderboardData(){
        leaderboard = new LinkedList<Profile>();
    }


    public LinkedList<Profile> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(LinkedList<Profile> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
