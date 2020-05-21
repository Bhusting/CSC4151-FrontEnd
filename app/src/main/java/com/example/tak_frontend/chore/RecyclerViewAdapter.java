package com.example.tak_frontend.chore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tak_frontend.R;

import java.util.LinkedList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private LinkedList<ChoreData> chores;
    private Context mContext;

    //Constructor
    public RecyclerViewAdapter(Context context, LinkedList<ChoreData> choresList) {
        mContext = context;
        chores = choresList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.tak_card, parent, false);
        ViewHolder holder = new ViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: 2");

        holder.imageTitle.setText(chores.get(position).choreTitle);
        holder.imageDate.setText("null");//chores.get(position).choreTime.toString()
        holder.imageStatus.setText(chores.get(position).choreStatus);

        holder.parentLayout.setOnClickListener(v -> {
            Log.d(TAG, "onClick: clicked on: " + chores.get(position).choreTitle);

            Toast.makeText(mContext, chores.get(position).choreTitle, Toast.LENGTH_SHORT).show();
        });

    }


    @Override
    public int getItemCount() {
        return chores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView imageTitle;
        TextView imageStatus;
        TextView imageDate;
        ConstraintLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTitle = itemView.findViewById(R.id.card_title);
            imageStatus = itemView.findViewById(R.id.card_completed);
            imageDate = itemView.findViewById(R.id.card_time);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
