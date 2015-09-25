package com.example.vkirillov.asyncprogressdialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by vkirillov on 25.09.2015.
 */
public class ProgressDialogFragment extends DialogFragment {
    private static final String TAG = "ProgressDialogFragment";

    private ProgressDialog pd;

    public ProgressDialogFragment(){
        setCancelable(false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        pd = new ProgressDialog(getActivity());
        pd.setTitle("title");
        pd.setMessage("in progress ...");
        pd.setIndeterminate(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return pd;
    }


}
