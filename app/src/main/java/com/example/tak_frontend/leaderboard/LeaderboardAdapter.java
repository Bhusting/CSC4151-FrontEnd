package com.example.tak_frontend.leaderboard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tak_frontend.R;
import com.example.tak_frontend.chore.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<LeadboardListItem> {

    private static final String TAG = "LeaderboardAdapter";
    private final Context context;
    private final ArrayList<LeadboardListItem> data;
    private final int layoutResourceId;



    public LeaderboardAdapter(@NonNull Context context, int resource, ArrayList<LeadboardListItem> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = resource;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        int xp = getItem(position).getXp();

        LeadboardListItem listItem = new LeadboardListItem(name, xp);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layoutResourceId, parent, false);

        TextView viewName = (TextView) convertView.findViewById(R.id.leaderboard_person);
        TextView viewXp = (TextView) convertView.findViewById(R.id.leaderboard_xp);

        viewName.setText(name);
        viewXp.setText(String.valueOf(xp));

        return convertView;
    }


}
