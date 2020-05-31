package com.example.tak_frontend.task;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;

import java.util.UUID;

public class TaskModal extends Fragment {

    private static final String TAG = ".TaskModal";
    private NewTakViewModel _viewModel;
    private SharedPreferences pref;
    private EditText taskTitleText;
    private EditText taskTimeText;
    private Button taskCreateButton;
    private Button back;


    public TaskModal() {
        // Required empty public constructor
    }

    public static TaskModal newInstance() {
        TaskModal fragment = new TaskModal();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("New Task");
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Declare View to be Returned
        View rootView = inflater.inflate(R.layout.new_task, container, false);

        taskTitleText = rootView.findViewById(R.id.editTaskTitle);
        taskTimeText =  rootView.findViewById(R.id.editTaskTime);
        taskCreateButton = rootView.findViewById(R.id.createTask);
        back = rootView.findViewById(R.id.backToTask);

        taskCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){
                    TaskDTO newTask = new TaskDTO() ;
                    newTask.setDuration(taskTimeText.getText().toString());
                    newTask.setTaskName(taskTitleText.getText().toString());
                    newTask.setHouseId(_viewModel.getLiveHouse().getValue().houseId);
                    newTask.setChannel(_viewModel.getLiveHouse().getValue().channel);
                    newTask.setTaskId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
                    _viewModel.CreateTask(newTask);
                    Toast.makeText(getActivity(), "TaskCreated", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).openFragment(TaskFragment.newInstance());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openFragment(TaskFragment.newInstance());
            }
        });


        //Inflates View
        return rootView;
    }
}
