package com.nimura.services;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vkirillov on 29.12.2015.
 */
public class DownloadService extends Service {
    private static final String TAG = "DownloadService";
    private final AtomicInteger mCommandCount = new AtomicInteger();
    private ExecutorService mExecutorService;

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newFixedThreadPool(5);
        Log.d(TAG, "Service started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mCommandCount.incrementAndGet();
        if(intent != null){
            downloadFile(intent.getData());
        }
        return START_REDELIVER_INTENT;
    }

    private void downloadFile(final Uri uri){
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    //Download imitation
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "File downloaded");
                if(mCommandCount.decrementAndGet() <= 0){
                    //The service will stop self then the last task is finished
                    stopSelf();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        mExecutorService.shutdownNow();
        Log.d(TAG, "Service destroyed");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
