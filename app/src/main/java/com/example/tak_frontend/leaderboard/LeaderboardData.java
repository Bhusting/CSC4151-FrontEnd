package com.example.tak_frontend.leaderboard;

import com.auth0.jwt.JWT;
import com.example.tak_frontend.profile.Profile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
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

    public int getSize(){ return leaderboard.size(); }

    public void setLeaderboard(LinkedList<Profile> leaderboard) {
        this.leaderboard = leaderboard;
    }

    protected void addProfile(Profile p){this.leaderboard.addLast(p);}

    public static LeaderboardData fromJson(@NotNull JSONArray array) throws IOException, JSONException {
        LeaderboardData leaderboardData = new LeaderboardData();
        Profile p;

        for(int i = 0; i < array.length(); i++){
            leaderboardData.addProfile( Profile.fromJson(array.getJSONObject(i)));
        }

        return leaderboardData;
    }


}
