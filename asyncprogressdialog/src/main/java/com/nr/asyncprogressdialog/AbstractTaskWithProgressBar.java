package com.nr.asyncprogressdialog;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

/**
 * This does the job in the background thread
 * notifying progress bar through the retained fragment
 * that always keeps a valid reference to the parent activity
 */
public abstract class AbstractTaskWithProgressBar<Params, Result>
        extends AbstractRetainedTask<Params, Result> {

    private int mProgressBarId;

    /**
     * Constructor
     *
     * @param retainedFragment retained fragment to save activity reference
     * @param progressBarId progress bar ID
     * @param cancelable       whether task cancelable or not
     */
    public AbstractTaskWithProgressBar(RetainedTaskFragment retainedFragment, int progressBarId,
                                          boolean cancelable) {
        this(retainedFragment, progressBarId, cancelable, -1);
    }

    /**
     * Constructor
     *
     * @param retainedFragment retained fragment to save activity reference
     * @param progressBarId progress bar ID
     * @param cancelable       whether task cancelable or not
     * @param maxValue         progress bar upper bound
     */
    public AbstractTaskWithProgressBar(RetainedTaskFragment retainedFragment, int progressBarId,
                                          boolean cancelable, int maxValue) {
        super(retainedFragment, cancelable, maxValue);
        mProgressBarId = progressBarId;
    }

    @Override
    protected void showProgressControl() {
        ProgressBar progressBar = getProgressBar();
        if(progressBar != null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setProgress(int progress) {
        ProgressBar progressBar = getProgressBar();
        if(progressBar != null){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(progress);
        }
    }

    @Override
    protected void closeProgressControl() {
        ProgressBar progressBar = getProgressBar();
        if(progressBar != null){
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Returns progress bar if it exists
     * @return progress bar object
     */
    private ProgressBar getProgressBar(){
        Activity act = getActivity();
        if(act == null){
            return null;
        }
        return (ProgressBar) act.findViewById(mProgressBarId);
    }
}
