package com.example.tak_frontend.task;

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

import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.TakViewModel;
import com.example.tak_frontend.MVVM.TakViewModelFactory;

import java.util.UUID;

public class TaskModal extends Fragment {

    private static final String TAG = ".TaskModal";
    private TakViewModel viewModel;
    private Bundle b;
    private EditText taskTitleText;
    private EditText taskTimeText;
    private Button taskCreateButton;
    private Button back;


    public TaskModal() {
        // Required empty public constructor
    }

    public static TaskModal newInstance(Bundle args) {
        TaskModal fragment = new TaskModal();
        fragment.setArguments(args);
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

        b = getArguments();
        viewModel = new ViewModelProvider(getActivity(),
                new TakViewModelFactory(getActivity().getApplication(), b))
                .get(TakViewModel.class);
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
                if(GoodData()){
                    TaskDTO newTask = new TaskDTO() ;
                    newTask.setDuration(taskTimeText.getText().toString());
                    newTask.setTaskName(taskTitleText.getText().toString());
                    newTask.setHouseId(new UUID(0L, 0L));
                    newTask.setChannel(new UUID(0L, 0L));
                    viewModel.newTaskDTO(newTask);
                    Toast.makeText(getActivity(), "TaskCreated", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).openFragment(TaskFragment.newInstance(b));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openFragment(TaskFragment.newInstance(b));
            }
        });


        //Inflates View
        return rootView;
    }

    private boolean GoodData(){

       // if ((taskTitleText.getText().toString() != null) && (taskTitleT))

        return true;
    }

}
