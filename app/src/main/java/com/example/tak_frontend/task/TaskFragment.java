package com.example.tak_frontend.task;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tak_frontend.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;

import okhttp3.internal.concurrent.Task;


public class TaskFragment extends Fragment {


    private LinkedList<TaskData> TaskList = new LinkedList<>();
    private RecyclerView recyclerView;
    private TaskRecyclerViewAdapter adapter;


    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Task");
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


        applyData(new TaskData("Dishwasher", "running", Date.from(Instant.now())));

        //New Task Dialog
        FloatingActionButton fab = rootView.findViewById(R.id.myFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(".TaskFragment", "fab Clicked");

            }
        });



        //Inflates View
        return rootView;
    }



    /*    TaskModal.TaskModalListener modalListener(){
            return false;
        };*/
    public void applyData(TaskData data) {
        TaskList.add(data);
        adapter.notifyDataSetChanged();
    }
};