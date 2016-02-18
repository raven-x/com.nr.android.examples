package com.example.vkirillov.broadcastreceiversample;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

/**
 * Service to process broadcast messages asynchronously
 */
public class ReceiverService extends IntentService {

    public ReceiverService() {
        super("ReceiverService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New timestamp data")
                .setContentText(Long.toString(intent.getLongExtra(Const.PARAM_TIME, 0L)));
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
