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

    private static final String TAG = "RecyclerViewAdapter";
    int selected_position = 0;
    private LinkedList<Task> tasks;
    private Context mContext;
    private NewTakViewModel _viewModel;
    private SharedPreferences pref;

    //Constructor
    public TaskRecyclerViewAdapter(Context context, LinkedList<Task> taskList, NewTakViewModel _viewModel) {
        mContext = context;
        this._viewModel = _viewModel;
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

/*        holder.options.setOnClickListener(v -> {
            PopupMenu pop = new PopupMenu(mContext, holder.options);

            pop.inflate(R.menu.card_menu);

            pop.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.edit_card:
                        _viewModel.DeleteTask(tasks.get(position).TaskId);
                        tasks.remove(position);
                        setTasks(tasks);
                        notifyDataSetChanged();
                        break;
                    case R.id.delete_card:
                        int i;

                        _viewModel.DeleteTask(tasks.get(position).TaskId);
                        tasks.remove(position);
                        setTasks(tasks);
                        notifyDataSetChanged();
                        break;
                }
                return false;
            });
        });*/

        holder.imageTitle.setText(tasks.get(position).TaskName);
        holder.imageDate.setText(tasks.get(position).EndTime);
       // holder.imageStatus.setText(tasks.get(position).);
    }

    public void setTasks(LinkedList<Task> t){
        this.tasks = t;
        notifyDataSetChanged();
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
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTitle = itemView.findViewById(R.id.card_title);
            imageStatus = itemView.findViewById(R.id.card_completed);
            imageDate = itemView.findViewById(R.id.card_time);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            itemView.setOnClickListener(this);

        }
    }
}
