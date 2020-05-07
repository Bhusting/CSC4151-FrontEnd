package com.example.tak_frontend.chore;

import android.content.Context;
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

import java.util.LinkedList;


public class ChoreFragment extends Fragment implements ChoreModal.ChoreModalListener {


    private LinkedList<ChoreData> choreList = new LinkedList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;


    public ChoreFragment() {
        // Required empty public constructor
    }

    public static ChoreFragment newInstance() {
        ChoreFragment fragment = new ChoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Chore");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Declare View to be Returned
        View rootView = inflater.inflate(R.layout.fragment_chore, container, false);
        //Find RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_chore);
        //Set Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Create and Set View Adapter
        adapter = new RecyclerViewAdapter(this.getContext(), choreList);
        recyclerView.setAdapter(adapter);

        //New Chore Dialog
        FloatingActionButton fab = rootView.findViewById(R.id.myFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(".ChoreFragment", "fab Clicked");
                showEditDialog();
            }
        });



        //Inflates View
        return rootView;
    }

    private void showEditDialog(){
        ChoreModal modal = new ChoreModal();
        modal.show(getParentFragmentManager(), "chore modal");
        modal.setTargetFragment(ChoreFragment.this, 1);
    }


    @Override
    public void applyData(ChoreData data) {
            choreList.add(data);
            adapter.notifyDataSetChanged();
    }
};