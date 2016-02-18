package com.nimura.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by vkirillov on 15.01.2016.
 */
public class SampleIntentService extends IntentService {
    public static final String RECEIVER = "receiver";
    public static final String RESPONSE = "response";
    public static final int RESPONSE_OK = 111;

    public SampleIntentService() {
        super("Sample intent service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResultReceiver resultReceiver = intent.getParcelableExtra(RECEIVER);
        if(resultReceiver != null){
            Bundle resultBundle = new Bundle();
            resultBundle.putString(RESPONSE, "Hello from sample intent service");
            resultReceiver.send(RESPONSE_OK, resultBundle);
        }
    }
}
