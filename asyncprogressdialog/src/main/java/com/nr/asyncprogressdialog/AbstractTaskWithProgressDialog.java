package com.nr.asyncprogressdialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * This does the job in the background thread
 * notifying progress dialog through the retained fragment
 * that always keeps a valid reference to the parent activity
 */
public abstract class AbstractTaskWithProgressDialog<Params, Result>
        extends AbstractRetainedTask<Params, Result> {

    private int mProgressDialogStyle;
    private String mTag;
    private String mTitle;
    private String mMessage;

    /**
     * Creates a new task with spinner style progress dialog
     * @param retainedFragment retained fragment to save activity reference
     * @param tag unique fragment tag
     * @param cancelable whether task cancelable or not
     */
    public AbstractTaskWithProgressDialog(RetainedTaskFragment retainedFragment,
                                          String title, String message,
                                          String tag, boolean cancelable) {
        this(retainedFragment, title, message, ProgressDialog.STYLE_SPINNER, tag, cancelable, 0);
    }

    /**
     * Creates a new task with progress bar style dialog
     * @param retainedFragment retained fragment to save activity reference
     * @param tag unique fragment tag
     * @param cancelable whether task cancelable or not
     * @param maxValue progress bar upper bound
     */
    public AbstractTaskWithProgressDialog(RetainedTaskFragment retainedFragment,
                                          String title, String message,
                                          String tag, boolean cancelable, int maxValue){
        this(retainedFragment, title, message, ProgressDialog.STYLE_HORIZONTAL, tag, cancelable, maxValue);
    }

    /**
     * Base constructor
     * @param retainedFragment retained fragment to save activity reference
     * @param title progress dialog title
     * @param message progress dialog message
     * @param progressDialogStyle progress dialog style
     * @param tag unique fragment tag
     * @param cancelable whether task cancelable or not
     * @param maxValue progress bar upper bound
     */
    private AbstractTaskWithProgressDialog(RetainedTaskFragment retainedFragment,
                                           String title, String message,
                                           int progressDialogStyle, String tag,
                                           boolean cancelable, int maxValue) {
        super(retainedFragment, cancelable, maxValue);
        mTitle = title;
        mMessage = message;
        mProgressDialogStyle = progressDialogStyle;
        mTag = tag;
    }

    @Override
    protected void setProgress(int progress){
        ProgressDialogFragment pdf = getDialog();
        if(pdf != null){
            pdf.setProgress(progress);
        }
    }

    @Override
    protected void showProgressControl(){
        Activity act = getActivity();
        if(act == null){
            return;
        }
        ProgressDialogFragment pdf = new ProgressDialogFragment();
        pdf.setArguments(initBundle());
        pdf.setCallback(this);
        pdf.show(act.getFragmentManager(), mTag);
    }

    @NonNull
    private Bundle initBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(ProgressDialogFragment.DIALOG_STYLE_ID, mProgressDialogStyle);
        bundle.putInt(ProgressDialogFragment.MAX_VALUE_ID, mMaxValue);
        bundle.putBoolean(ProgressDialogFragment.CANCELABLE_ID, mCancelable);
        bundle.putString(ProgressDialogFragment.TITLE_ID, mTitle);
        bundle.putString(ProgressDialogFragment.MESSAGE_ID, mMessage);
        return bundle;
    }

    @Override
    protected void closeProgressControl(){
        DialogFragment dialogFragment = getDialog();
        if(dialogFragment != null){
            dialogFragment.dismiss();
        }
    }

    /**
     * Returns the progress dialog if it exists
     * @return progress dialog object
     */
    private ProgressDialogFragment getDialog(){
        Activity act = getActivity();
        if(act == null){
            return null;
        }
        return (ProgressDialogFragment) act
                .getFragmentManager()
                .findFragmentByTag(mTag);
    }
}
