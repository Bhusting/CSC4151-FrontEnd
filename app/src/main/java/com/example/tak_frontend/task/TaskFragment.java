package com.example.tak_frontend.task;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.TakViewModel;
import com.example.tak_frontend.TakViewModelFactory;
import com.example.tak_frontend.profile.Profile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;
import java.util.UUID;


public class TaskFragment extends Fragment {

    private static final String TAG = ".TaskFragment";
    private LinkedList<TaskData> TaskList = new LinkedList<>();
    private LinkedList<TaskDTO> TaskListDTO = new LinkedList<>();
    private RecyclerView recyclerView;
    private TaskRecyclerViewAdapter adapter;
    private TakViewModel viewModel;
    private Bundle b;


    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(Bundle args) {
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Task");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        b = getArguments();
        viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity().getApplication(), b))
                .get(TakViewModel.class);

        viewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<LinkedList<TaskData>>() {
            @Override
            public void onChanged(LinkedList<TaskData> taskData) {
                Log.d(TAG, "Task Data Changed");
                TaskList = taskData;
            }
        });
        viewModel.getTaskDTO().observe(getViewLifecycleOwner(), new Observer<LinkedList<TaskDTO>>() {
            @Override
            public void onChanged(LinkedList<TaskDTO> taskDTO) {
                Log.d(TAG, "Task DTO Changed");
                TaskListDTO = taskDTO;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Declare View to be Returned
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);
        //Find RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_task);
        //Set Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Create and Set View Adapter
        adapter = new TaskRecyclerViewAdapter(this.getContext(), TaskList);
        recyclerView.setAdapter(adapter);

        Profile p = new Profile(UUID.randomUUID(), "sam", "yeet", 10, UUID.randomUUID(), "swynnr@gmail.com");

        applyData(new TaskData("Dishwasher", "running", Date.from(Instant.now()), p));

        FloatingActionButton fab = rootView.findViewById(R.id.myFABtask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openFragment(TaskModal.newInstance(b));
            }
        });


        //Inflates View
        return rootView;
    }

    public void applyData(TaskData data) {
        TaskList.add(data);
        adapter.notifyDataSetChanged();
    }
};