package com.nimura.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class BindableServiceExtendingBinder extends Service {
    private final IBinder mBinder = new LocalBinder();
    private final Random mGenerator = new Random();

    public class LocalBinder extends Binder{
        BindableServiceExtendingBinder getService(){
            return BindableServiceExtendingBinder.this;
        }
    }

    public BindableServiceExtendingBinder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Client accessible method
     * @return
     */
    public int getRandomNumber(){
        return mGenerator.nextInt(100);
    }
}
