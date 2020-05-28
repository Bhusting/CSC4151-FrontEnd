package com.example.tak_frontend.chore.chore_date_pickers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ChoreModalDay extends Fragment {

    public static ChoreModalDay newInstance(Bundle b) {
        ChoreModalDay fragment = new ChoreModalDay();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
