package com.example.tak_frontend;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Objects;


public class ChoreFragment<stringRequest> extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private LinkedList<ChoreData> choreList = new LinkedList<>();

    public ChoreFragment() {
        // Required empty public constructor
    }

    public static ChoreFragment newInstance(String param1, String param2) {
        ChoreFragment fragment = new ChoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Chore");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

            ChoreData temp = new ChoreData();
            choreList.add(temp);
            choreList.add(temp);
            choreList.add(temp);
            choreList.add(temp);
            choreList.add(temp);
            choreList.add(temp);

        Log.d(".ChoreFragment", "onCreate: before init");
        Toast.makeText(this.getContext(), ("List size: " + Integer.toString(choreList.size())), Toast.LENGTH_SHORT).show();
      //  initRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Declare View to be Returned
        View rootView = inflater.inflate(R.layout.fragment_chore, container, false);
        //Find RecyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_chore);
        //Set Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Create and Set View Adapter
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getContext(), choreList);
        recyclerView.setAdapter(adapter);
        //Inflates View
        return rootView;
    }

    public boolean ping(){
        return false;
    }
};