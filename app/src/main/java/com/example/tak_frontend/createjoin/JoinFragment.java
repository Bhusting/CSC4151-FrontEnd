package com.example.tak_frontend.createjoin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.MainActivity;
import com.example.tak_frontend.R;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;
import com.example.tak_frontend.profile.House;
import com.example.tak_frontend.profile.Profile;
import com.example.tak_frontend.profile.ProfileFragment;

import java.util.UUID;


public class JoinFragment extends Fragment {

    private Button confirmJoinButton;
    private Button backToChoiceButton;
    private TextView houseCodeTextView;
    private SharedPreferences pref;
    private NewTakViewModel viewModel;


    public JoinFragment() {
        // Required empty public constructor
    }

    public static JoinFragment newInstance() {
        JoinFragment fragment = new JoinFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Join a House");

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_joinhouse, container, false);


        backToChoiceButton = (Button) view.findViewById(R.id.backJoinButton);
        confirmJoinButton = (Button) view.findViewById(R.id.joinButton);
        houseCodeTextView = view.findViewById(R.id.houseCodeInput);
        TextView errorView = view.findViewById(R.id.textView);

        confirmJoinButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                confirmJoinButton.setEnabled(false);
                backToChoiceButton.setEnabled(false);
                Log.i("joinButton", "Join Button clicked");
                boolean valid = false;
                UUID guid = null;

                try {
                    guid = UUID.fromString(houseCodeTextView.getText().toString());
                    valid = true;
                    houseCodeTextView.setBackgroundColor(Color.parseColor("#f0fff4"));
                }
                catch( Exception e) {
                    valid = false;
                    houseCodeTextView.setBackgroundColor(Color.RED);
                    errorView.setVisibility(View.VISIBLE);
                    confirmJoinButton.setEnabled(true);
                    backToChoiceButton.setEnabled(true);
                }

                if (valid) {
                    House house = viewModel.GetHouseById(guid);

                    if (house == null) {
                        Toast toast = Toast.makeText(getContext(), "House Doesn't Exsist", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        viewModel.JoinHouse(house);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            }

        });

        backToChoiceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View w)
            {
                Log.i("BackToChoice", "BacktoChoice Button clicked");

                ((MainActivity) getActivity()).openFragment(CreatejoinchoiceFragment.newInstance());
            }

        });
        return view;
    }
};