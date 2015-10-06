package com.nr.asyncprogressdialog;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Base AbstractRetainedTask
 */
public abstract class AbstractRetainedTask<Params, Result>
        extends AsyncTask<Params, Integer, Result>
        implements IProgressControlCallback {

    protected RetainedTaskFragment mRetainedFragment;
    protected int mMaxValue;
    protected boolean mCancelable;

    /**
     * Constructor
     * @param retainedFragment retained fragment to save activity reference
     * @param cancelable whether task cancelable or not
     * @param maxValue progress bar upper bound
     */
    protected AbstractRetainedTask(RetainedTaskFragment retainedFragment,
                                   boolean cancelable, int maxValue) {
        mRetainedFragment = retainedFragment;
        mCancelable = cancelable;
        mMaxValue = maxValue;
    }

    @Override
    protected void onPreExecute() {
        showProgressControl();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if(mRetainedFragment.isUiReady()) {
            setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Result integer) {
        //Conditions to take into account
        //1. activity not there
        //2. Activity is there but stopped
        //3. Activity is UI ready
        if(mRetainedFragment.isUiReady()){
            closeProgressControl();
        }
    }

    @Override
    public void onProgressControlCancel() {
        cancel(false);
    }

    protected Activity getActivity(){
        return mRetainedFragment.getActivity();
    }

    /**
     * Shows progress bar control
     */
    protected abstract void showProgressControl();

    /**
     * Sets the current progress value
     * @param progress current progress value
     */
    protected abstract void setProgress(int progress);

    /**
     * Closes progress bar control
     */
    protected abstract void closeProgressControl();
}
