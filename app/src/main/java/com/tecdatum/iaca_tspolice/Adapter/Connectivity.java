package com.tecdatum.iaca_tspolice.Adapter;

/**
 * Created by HI on 3/14/2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.tecdatum.iaca_tspolice.R;

import androidx.fragment.app.DialogFragment;


/**
 * TODO
 */public class Connectivity extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.connectivity, null))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Connectivity.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}