package com.example.vkirillov.asyncprogressdialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.AsyncTask;

/**
 * Created by vkirillov on 25.09.2015.
 */
public class SampleAcyncTask extends AsyncTask<String, Integer, Integer> {

    public static final String TAG = "SampleAcyncTask";

    private MonitoredFragment retainedFragment;

    /**Activity completion indicator*/
    private boolean bDone = false;

    public SampleAcyncTask(MonitoredFragment retainedFragment) {
        this.retainedFragment = retainedFragment;
    }

    //Show dialog
    @Override
    protected void onPreExecute() {
        showDialogFragment();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        bDone = true;
        //Conditions to take into account
        //1. activity not there
        //2. Activity is there but stopped
        //3. Activity is UI ready
        if(retainedFragment.isUIready()){
            closeProgressDialog();
        }
    }

    @Override
    protected Integer doInBackground(String... params) {
        return null;
    }

    /**
     * Shows progress dialog
     */
    private void showDialogFragment(){
        Activity act = retainedFragment.getActivity();
        ProgressDialogFragment pdf = new ProgressDialogFragment();
        pdf.show(act.getFragmentManager(), TAG);
    }

    /**
     * Returns progress dialog if it exists
     * @return
     */
    private ProgressDialogFragment getDialog(){
        Activity act = retainedFragment.getActivity();
        if(act == null){
            return null;
        }
        return (ProgressDialogFragment) act
                .getFragmentManager()
                .findFragmentByTag(TAG);
    }

    /**
     * Closes progress dialog
     */
    private void closeProgressDialog(){
        DialogFragment dialogFragment = getDialog();
        if(dialogFragment != null){
            dialogFragment.dismiss();
        }
    }

    private void setProgressOnProgressDialog(){
        ProgressDialogFragment pdf = getDialog();
        if(pdf != null){
            //TODO
        }
    }
}
