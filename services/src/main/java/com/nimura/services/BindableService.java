package com.nimura.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service itself
 */
public class BindableService extends Service {
    public static final String TAG = "BindableService";
    private final ServiceBinder mBinder = new ServiceBinder();
    private ExecutorService mExecutorService;

    /**
     *
     */
    public class ServiceBinder extends Binder{
        public BindableService getService(){
            return BindableService.this;
        }
    }

    public interface OperationListener{
        void onOperationDone(String result);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newFixedThreadPool(5);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void getSome(final OperationListener listener){
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(listener != null) {
                    listener.onOperationDone("Bindable operation is done");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        mExecutorService.shutdownNow();
        super.onDestroy();
    }
}
