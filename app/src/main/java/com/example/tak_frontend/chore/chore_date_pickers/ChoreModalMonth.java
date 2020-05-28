package com.example.tak_frontend.chore.chore_date_pickers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ChoreModalMonth extends Fragment {

    public static ChoreModalMonth newInstance(Bundle b) {
        ChoreModalMonth fragment = new ChoreModalMonth();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
