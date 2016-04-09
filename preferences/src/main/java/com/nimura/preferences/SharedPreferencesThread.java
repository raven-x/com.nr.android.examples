package com.nimura.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.*;
import android.os.Process;
import android.preference.PreferenceManager;

/**
 * Created by vkirillov on 03.12.2015.
 */
public class SharedPreferencesThread extends HandlerThread {

    private final int SET_LIONESS = 1;

    private final SharedPreferences mPreferences;
    private Handler mHandler;
    private Handler mUiHandler;

    public SharedPreferencesThread(Context context, Handler uiHandler) {
        super("SharedPreferencesThread", Process.THREAD_PRIORITY_BACKGROUND);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mUiHandler = uiHandler;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper()){
            @Override
            public void handleMessage(Message msg) {
                SharedPreferences.Editor editor = mPreferences.edit();
                switch (msg.what){
                    case SET_LIONESS:
                        String lioness = msg.getData().getString("LIONESS", null);
                        editor.putString("lioness_preference", lioness);
                        mUiHandler.sendEmptyMessage(0);
                        break;
                }
                editor.commit();
            }
        };
    }

    public void setFavouriteLioness(String value){
        Message message = mHandler.obtainMessage(SET_LIONESS);
        Bundle bundle = new Bundle();
        bundle.putString("LIONESS", value);
        message.setData(bundle);
        mHandler.sendMessage(message);
    }
}
