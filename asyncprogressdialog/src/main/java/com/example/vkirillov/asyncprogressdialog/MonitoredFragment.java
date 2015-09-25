package com.example.vkirillov.asyncprogressdialog;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by vkirillov on 25.09.2015.
 */
public class MonitoredFragment extends Fragment {

    public static final String TAG = "MonitoredFragment";

    private boolean bUIready = false;

    public static MonitoredFragment getInstance(){
        MonitoredFragment fragment = new MonitoredFragment();
        return fragment;
    }

    public static MonitoredFragment createRetainedMonitoredFragment(Activity act){
        MonitoredFragment fragment = MonitoredFragment.getInstance();
        fragment.setRetainInstance(true);

        FragmentManager fm = act.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(fragment, MonitoredFragment.TAG);
        ft.commit();

        return fragment;
    }

    /**
     * Returns retained monitored frament if it exists
     * or creates new retained fragment
     * @param act
     * @return
     */
    public static MonitoredFragment establishRetainedMonitoredFragment(Activity act){
        Fragment fragment = getRetainedMonitoredFragment(act);
        if(fragment == null){
            return createRetainedMonitoredFragment(act);
        }
        return (MonitoredFragment) fragment;
    }

    /**
     * Returns retained monitored frament if it exists
     * @param act
     * @return
     */
    public static MonitoredFragment getRetainedMonitoredFragment(Activity act){
        Fragment fragment = act.getFragmentManager().findFragmentByTag(TAG);
        if(fragment == null){
            return null;
        }
        return (MonitoredFragment) fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        bUIready = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        bUIready = false;
    }

    /**
     * Returns if UI ready for interaction
     * @return
     */
    public boolean isUIready(){
        return bUIready;
    }
}
