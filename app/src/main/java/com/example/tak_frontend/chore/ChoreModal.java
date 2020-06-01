package com.example.tak_frontend.chore;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;
import com.example.tak_frontend.chore.chore_date_pickers.ChoreModalDay;
import com.example.tak_frontend.chore.chore_date_pickers.ChoreModalMonth;
import com.example.tak_frontend.chore.chore_date_pickers.ChoreModalWeek;
import com.example.tak_frontend.chore.chore_date_pickers.ChoreModalYear;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ChoreModal extends Fragment {

    private static final String TAG = ".ChoreModal";
    private NewTakViewModel _viewModel;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private SharedPreferences pref;
    private Button backButton;
    private Button createButton;
    private EditText choreName;
    private RadioGroup radioGroup;
    public ChoreModal(){

    }

    public static ChoreModal newInstance(){
        ChoreModal fragment = new ChoreModal();
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
        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        _viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity()
                        .getApplication(),
                        pref.getString("accessToken", ""),
                        pref.getString("idToken", "")))
                .get(NewTakViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chore_modal, container, false);
        backButton = rootView.findViewById(R.id.backButtonChore);
        datePicker = rootView.findViewById(R.id.choreDatePicker);
        choreName = rootView.findViewById(R.id.choreModalTitleTextView);
        timePicker = rootView.findViewById(R.id.choreTimePicker);
        createButton = rootView.findViewById(R.id.createChoreButton);
        radioGroup = rootView.findViewById(R.id.choreRadioGroup);
        createButton.setOnClickListener(v -> {
            String minutes = String.valueOf(timePicker.getMinute());
            String hour = String.valueOf(timePicker.getHour());
            if(timePicker.getMinute() < 10){
                minutes = '0' + minutes;
            }
            if(timePicker.getHour() < 10){
                hour = '0' + hour;
            }

            String months = String.valueOf(datePicker.getMonth() + 1);
            String days = String.valueOf(datePicker.getDayOfMonth());
            String year = String.valueOf(datePicker.getYear());

            if(datePicker.getMonth() < 10){
                months = '0' + months;
            }
            if(datePicker.getDayOfMonth() < 10){
                days = '0' + days;
            }
            if(datePicker.getYear() < 10){
                year = '0' + year;
            }



            String date = months + '/' + days + '/' + year;//'00/00/00'
            String time = hour + ':' + minutes; //'00:00'
            short choreType = getChoreType(radioGroup);


            Log.d(TAG, "Date and Time: " + date + " - " + time);

            ChoreData chore = new ChoreData();
            chore.choreName = choreName.getText().toString();
            chore.choreTypeId = choreType;
            chore.completionDate = date;
            chore.completionTime = time;
            chore.houseId = _viewModel.getLiveHouse().getValue().houseId;

            Log.d(TAG, "DEBUG" + date +  " : " + time);
            Toast.makeText(getContext(),date +  " : " + time, Toast.LENGTH_SHORT).show();
            _viewModel.CreateChore(chore);


            ((MainActivity) getActivity()).openFragment(ChoreFragment.newInstance());

        });

        backButton.setOnClickListener(v -> {
                ((MainActivity) getActivity()).openFragment(ChoreFragment.newInstance());

        });


        return rootView;
    }

    short getChoreType(RadioGroup group){
        switch(group.getCheckedRadioButtonId()){
            case R.id.choreRadioDay:
                return 0;
            case R.id.choreRadioWeek:
                return 1;
            case R.id.choreRadioMonth:
                return 2;
            case R.id.choreRadioYear:
                return 3;
            default:
                return -1;
        }
    }
}