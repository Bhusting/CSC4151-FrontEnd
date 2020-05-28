package com.example.tak_frontend.chore.chore_date_pickers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ChoreModalYear extends Fragment {

    public static ChoreModalYear newInstance(Bundle b) {
        ChoreModalYear fragment = new ChoreModalYear();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
