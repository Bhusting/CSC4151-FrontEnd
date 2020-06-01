package com.example.tak_frontend.leaderboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;
import com.example.tak_frontend.profile.Profile;

import java.util.ArrayList;
import java.util.LinkedList;


public class LeaderboardFragment extends Fragment {


    private static final String TAG = ".LeaderboardFragment";
    private NewTakViewModel viewModel;
    private SharedPreferences pref;
    private LeaderboardData leaderboard;
    private ListView listView;
    private LeaderboardAdapter adapter;

    private ArrayList<LeadboardListItem> arrayList;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    public static LeaderboardFragment newInstance() {
        LeaderboardFragment fragment = new LeaderboardFragment();
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
        arrayList = new ArrayList<>();
        for(int i = 0; i < leaderboard.getSize(); i++){
            arrayList.add(new LeadboardListItem(leaderboard.getLeaderboard().get(i)));
        }
        adapter.clear();
        adapter.addAll(arrayList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity()
                        .getApplication(),
                        pref.getString("accessToken", ""),
                        pref.getString("idToken", "")))
                .get(NewTakViewModel.class);
        viewModel.getLiveLeaderboard().observe(getViewLifecycleOwner(), profiles -> {
            if(profiles == null)
                Log.d(TAG, "Leaderboard LiveData Null");
            else {
                leaderboard = new LeaderboardData();
                leaderboard.setLeaderboard(profiles);
                refresh();
            }
        });
    }
};