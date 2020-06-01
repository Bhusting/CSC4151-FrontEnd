package com.example.tak_frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.tak_frontend.MVVM.ViewModel.NewTakViewModel;
import com.example.tak_frontend.MVVM.ViewModel.TakViewModelFactory;
import com.example.tak_frontend.createjoin.CreatejoinchoiceFragment;
import com.example.tak_frontend.profile.Profile;

import java.util.UUID;

public class HouseActivity extends AppCompatActivity {
    private NewTakViewModel viewModel;
    private SharedPreferences pref;
    private String accessToken;
    private String idToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        accessToken = pref.getString("accessToken", "");
        idToken = pref.getString("idToken", "");

        viewModel = new ViewModelProvider(this,
                new TakViewModelFactory(this.getApplication(), accessToken, idToken))
                .get(NewTakViewModel.class);

        Profile profile = viewModel.getLiveProfile().getValue();

        UUID empty = UUID.fromString("00000000-0000-0000-0000-000000000000");

        if (profile.houseId.equals(empty)) {

            openFragment(CreatejoinchoiceFragment.newInstance());

        }
        else {

            Intent intent = new Intent(HouseActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

    }

    public void openFragment(Fragment fragment) {
        //Begin transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
