package com.example.tak_frontend.chore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tak_frontend.R;


public class ChoreModal extends DialogFragment {
    private EditText choreTitle;
    private EditText choreStatus;
    private ChoreModalListener listener;
    private Context mContext;


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_chore_modal, null);

        builder.setView(view)
                .setTitle("New Chore")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ChoreData data = new ChoreData();
                        data.choreTitle = choreTitle.getText().toString();
                        data.choreStatus = choreStatus.getText().toString();
                        listener.applyData(data);
                    }
                });

        choreTitle = view.findViewById(R.id.chore_modal_title_text);
        choreStatus = view.findViewById(R.id.chore_modal_date_text);
        //choreData.choreTime = Date.from(Instant.now());

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try {
            listener = (ChoreModalListener) getActivity();
        } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() +
                        "must implement ChoreModalListener");
        }
        super.onAttach(context);
    }

    public interface ChoreModalListener{
        void applyData(ChoreData data);
    }
}