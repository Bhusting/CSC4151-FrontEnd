package com.example.tak_frontend.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;
import com.example.tak_frontend.profile.Profile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;
import java.util.UUID;


public class TaskFragment extends Fragment {

    private static final String TAG = ".TaskFragment";
    private LinkedList<Task> TaskList = new LinkedList<>();
    private RecyclerView recyclerView;
    private TaskRecyclerViewAdapter adapter;
    private NewTakViewModel _viewModel;
    private SharedPreferences pref;
    private Context context;



    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Task");

        context = this.getContext();

        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        _viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity()
                        .getApplication(),
                        pref.getString("accessToken", ""),
                        pref.getString("idToken", "")))
                .get(NewTakViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Get Tasks
        _viewModel.getLiveTasks().observe(getViewLifecycleOwner(), tasks -> refreshTasks(tasks));
        //Declare View to be Returned
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);
        //Find RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_task);

        //Create and Set View Adapter
        adapter = new TaskRecyclerViewAdapter(this.getContext(), TaskList, _viewModel);
        recyclerView.setAdapter(adapter);
        //Set Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //New Task Modal
        FloatingActionButton fab = rootView.findViewById(R.id.myFABtask);
        fab.setOnClickListener(v -> {
            PopupMenu pop = new PopupMenu(getContext(), getView().findViewById(R.id.myFABtask));
            pop.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.default_dishwasher:
                        _viewModel.CreateTask(TaskDTO.dishwasher(_viewModel.getLiveHouse().getValue()));
                        return true;
                    case R.id.default_washer:
                        _viewModel.CreateTask(TaskDTO.washer(_viewModel.getLiveHouse().getValue()));
                        return true;
                    case R.id.default_dryer:
                        _viewModel.CreateTask(TaskDTO.dryer(_viewModel.getLiveHouse().getValue()));
                        return true;
                    case R.id.new_task:
                        ((MainActivity) getActivity()).openFragment(TaskModal.newInstance());
                        return true;
                }
                return false;
            });
            pop.inflate(R.menu.tasks_menu);
            pop.show();
        });

        //Inflates View
        return rootView;
    }

    public void refreshTasks(LinkedList<Task> tasks){


        if(tasks != null){

            if(tasks.size() == 1){
                adapter = new TaskRecyclerViewAdapter(context, tasks, _viewModel);
                recyclerView.setLayoutManager( new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            } else {
                LinkedList<Task> flip = new LinkedList<>();
                for (int i = (tasks.size() - 1); i > 0; i--) {
                    flip.add(tasks.get(i));
                }
                adapter = new TaskRecyclerViewAdapter(context, flip, _viewModel);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        }

    }
};