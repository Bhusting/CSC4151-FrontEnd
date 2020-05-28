package com.example.tak_frontend.chore.chore_date_pickers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ChoreModalWeek extends Fragment {

    public static ChoreModalWeek newInstance(Bundle b) {
        ChoreModalWeek fragment = new ChoreModalWeek();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
