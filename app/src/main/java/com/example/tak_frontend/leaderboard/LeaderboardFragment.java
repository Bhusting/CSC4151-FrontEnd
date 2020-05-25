package com.example.tak_frontend.leaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.auth0.utils.Asserts;
import com.example.tak_frontend.R;
import com.example.tak_frontend.TakViewModel;
import com.example.tak_frontend.TakViewModelFactory;
import com.example.tak_frontend.chore.ChoreData;
import com.example.tak_frontend.profile.Profile;

import java.util.ArrayList;
import java.util.LinkedList;


public class LeaderboardFragment extends Fragment {


    private static final String TAG = ".LeaderboardFragment";
    private TakViewModel viewModel;
    private Bundle b;
    private LeaderboardData leaderboard;
    private ListView listView;
    private LeaderboardAdapter adapter;

    private ArrayList<LeadboardListItem> arrayList;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    public static LeaderboardFragment newInstance(Bundle b) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Leaderboard");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Declare View to be Returned
        View rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        //Find ListView
        listView = (ListView) rootView.findViewById(R.id.leaderboard_listView);

        arrayList = new ArrayList<>();
        adapter = new LeaderboardAdapter(this.getContext(),
                R.layout.leaderboard_list_item,
                arrayList);

        listView.setAdapter(adapter);

        return rootView;
    }


    public void refresh(){
        //Refresh Layout Data
        for(int i = 0; i < leaderboard.getSize(); i++){
            arrayList.add(new LeadboardListItem(leaderboard.getLeaderboard().get(i)));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

/*        b = getArguments();
        viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity().getApplication(), b))
                .get(TakViewModel.class);

        viewModel.fetchLeaderboard();
        viewModel.getLeaderboard().observe(getViewLifecycleOwner(), new Observer<LeaderboardData>() {
            @Override
            public void onChanged(LeaderboardData leaderboardData) {
                Log.d(TAG, "Leaderboard Data Changed");
                leaderboard = leaderboardData;
                refresh();
            }
        });*/
    }

    public void applyData(LeadboardListItem item){
        arrayList.add(item);
        adapter.notifyDataSetChanged();
    }
};