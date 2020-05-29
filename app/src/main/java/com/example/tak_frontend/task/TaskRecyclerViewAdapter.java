package com.example.tak_frontend.task;

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
import com.example.tak_frontend.chore.ChoreData;

import java.util.LinkedList;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private LinkedList<Task> tasks = new LinkedList<Task>();
    private Context mContext;

    //Constructor
    public TaskRecyclerViewAdapter(Context context, LinkedList<Task> taskList) {
        mContext = context;
        tasks = taskList;
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

        /*
        holder.imageTitle.setText(tasks.get(position).getTaskTitle());
        holder.imageDate.setText(tasks.get(position).getTaskTime().toString());
        holder.imageStatus.setText(tasks.get(position).getTaskStatus());*/
    }

    public void setTasks(LinkedList<Task> t){
        this.tasks= t;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return tasks.size();
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
