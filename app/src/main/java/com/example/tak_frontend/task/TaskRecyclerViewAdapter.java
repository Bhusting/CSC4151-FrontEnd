package com.example.tak_frontend.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;
import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.chore.ChoreData;

import java.util.LinkedList;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "TaskRecyclerViewAdapter";
    private LinkedList<Task> tasks;
    private Context mContext;
    private NewTakViewModel _viewModel;

    //Constructor
    public TaskRecyclerViewAdapter(Context context, LinkedList<Task> taskList, NewTakViewModel _viewModel) {
        mContext = context;
        tasks = taskList;
        this._viewModel = _viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tak_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Task task = tasks.get(position);

        holder.imageDate.setText(task.endTime);
        holder.imageTitle.setText(task.taskName);
        Toast toast = Toast.makeText(mContext, "Clicked Card :" + String.valueOf(position), Toast.LENGTH_LONG);
        holder.options.setOnClickListener(v -> {
            PopupMenu pop = new PopupMenu(mContext, holder.options);
            pop.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.edit_card:
                        //_viewModel.
                        toast.setText("WIP");
                        toast.show();
                        return true;
                    case R.id.delete_card:

                        _viewModel.DeleteTask(task.taskId);
                        Log.d(TAG, "ViewHolder: delete task UUID: " + task.taskId);
                        toast.show();
                        return true;
                }
                return false;
            });
            pop.inflate(R.menu.card_menu);
            pop.show();
        });

    }

    @Override
    public int getItemCount() {
        if(tasks == null)
            return 0;
        else
            return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView imageTitle;
        TextView imageStatus;
        TextView imageDate;
        TextView options;
        ConstraintLayout parentLayout;


        @Override
        public void onClick(View v) {
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTitle = itemView.findViewById(R.id.card_title);
            imageStatus = itemView.findViewById(R.id.card_completed);
            imageDate = itemView.findViewById(R.id.card_time);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            options = itemView.findViewById(R.id.cardOptions);
            itemView.setOnClickListener(this);
        }
    }
}
