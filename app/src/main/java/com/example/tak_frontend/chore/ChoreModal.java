package com.example.tak_frontend.chore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.TakViewModel;
import com.example.tak_frontend.MVVM.TakViewModelFactory;
import com.example.tak_frontend.chore.chore_date_pickers.ChoreModalDay;
import com.example.tak_frontend.chore.chore_date_pickers.ChoreModalMonth;
import com.example.tak_frontend.chore.chore_date_pickers.ChoreModalWeek;
import com.example.tak_frontend.chore.chore_date_pickers.ChoreModalYear;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ChoreModal extends Fragment {

    private static final String TAG = ".ChoreModal";
    private TakViewModel viewModel;
    private Bundle b;
    private Button backButton;
    BottomNavigationView datePicker;
    private TextView choreName;

    public ChoreModal(){

    }

    public static ChoreModal newInstance(Bundle args){
        ChoreModal fragment = new ChoreModal();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("New Chore");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        b = getArguments();
        viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity().getApplication(), b))
                .get(TakViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chore_modal, container, false);
        backButton = rootView.findViewById(R.id.backButtonChore);
        datePicker = rootView.findViewById(R.id.nav_date_picker);
        choreName = rootView.findViewById(R.id.choreModalTitleTextView);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openFragment(ChoreFragment.newInstance(b));
            }
        });


        return rootView;
    }

    public void openFragment(Fragment fragment){
        //Begin transaction
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.modal_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
        switch (item.getItemId()) {
            case R.id.modal_day:
                openFragment(ChoreModalDay.newInstance(b));
                return true;
            case R.id.modal_week:
                openFragment(ChoreModalWeek.newInstance(b));
                return true;
            case R.id.modal_month:
                openFragment(ChoreModalMonth.newInstance(b));
                return true;
            case R.id.modal_year:
                openFragment(ChoreModalYear.newInstance(b));
                return true;
        }
        return false;
    };
}