package com.example.tak_frontend.chore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.R;

import java.util.LinkedList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "ChoreRecyclerViewAdapter";
    private LinkedList<ChoreData> chores;
    private Context mContext;
    private NewTakViewModel _viewModel;

    //Constructor
    public RecyclerViewAdapter(Context context, LinkedList<ChoreData> choresList, NewTakViewModel _viewModel) {
        mContext = context;
        chores = choresList;
        this._viewModel = _viewModel;
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

        final ChoreData chore = chores.get(position);

        holder.imageTitle.setText(chore.choreName);
        holder.imageDate.setText(chore.completionDate);
        holder.options.setOnClickListener(v -> {
            PopupMenu pop = new PopupMenu(mContext, holder.options);
            pop.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.complete_card:
                        _viewModel.CompleteChore(chore.choreId);
                        _viewModel.AddXP();

                        Toast.makeText(mContext, "Adding xp", Toast.LENGTH_SHORT);
                        return true;
                    case R.id.ignore_card:
                        _viewModel.CompleteChore(chore.choreId);

                }
                return false;
            });
            pop.inflate(R.menu.chores_menu);
            pop.show();
        });

    }

    @Override
    public int getItemCount() {
        if (chores == null)
            return 0;
        else
            return chores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView imageTitle;
        TextView imageStatus;
        TextView imageDate;
        TextView options;
        ConstraintLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTitle = itemView.findViewById(R.id.card_title);
            imageStatus = itemView.findViewById(R.id.card_completed);
            imageDate = itemView.findViewById(R.id.card_time);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            options = itemView.findViewById(R.id.cardOptions);
        }
    }
}
