package com.nimura.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;


public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1000;

    private Button btnNotify;
    private Button btnUpdateNotification;
    private Button btnNotifyExtended;
    private Button btnShowDynamicProgress;
    private Button btnShowStaticProgress;
    private Button btnShowCustomNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        btnNotify = (Button) findViewById(R.id.notify);
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
        btnUpdateNotification = (Button) findViewById(R.id.btnUpdateNotify);
        btnUpdateNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });
        btnNotifyExtended = (Button) findViewById(R.id.btnOpenExtendedLayout);
        btnNotifyExtended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyExtended();
            }
        });
        btnShowDynamicProgress = (Button) findViewById(R.id.btnShowDynamicProgress);
        btnShowDynamicProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowDynamicProgress();
            }
        });
        btnShowStaticProgress = (Button) findViewById(R.id.btnShowStaticProgress);
        btnShowStaticProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowStaticProgress();
            }
        });
        btnShowCustomNotification = (Button) findViewById(R.id.btnShowCustomNotification);
        btnShowCustomNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowCustomNotification();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_search_white_24dp)
                    .setContentTitle("Notification")
                    .setContentText("Hello world")
                    .setAutoCancel(true);
        Intent resultIntent = new Intent(this, NotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent)
            .addAction(R.drawable.abc_ic_go_search_api_mtrl_alpha, "go", resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void updateNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_search_white_24dp)
                        .setContentTitle("Notification")
                        .setContentText("New text !!!")
                        .setAutoCancel(true);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID, mBuilder.build());
    }

    //Creates an expanded notification layout with list of lines
    private void notifyExtended(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_search_white_24dp)
                        .setContentText("Hello world")
                        .setAutoCancel(true);
        Intent resultIntent = new Intent(this, NotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle
                    .setBigContentTitle("Big content")
                    .addLine("line 1")
                    .addLine("line 2")
                    .setSummaryText("+3 more");

        mBuilder.setStyle(inboxStyle);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void onShowDynamicProgress(){
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                    .setContentText("Download in progress")
                    .setSmallIcon(R.drawable.ic_search_white_24dp)
                    .setAutoCancel(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int incr = 0;
                for(;incr <= 100; incr += 5){
                    mBuilder.setProgress(100, incr, false);
                    //updates the notification
                    notificationManager.notify(0, mBuilder.build());
                    try{
                        Thread.sleep(500);
                    }catch (Exception e){
                        Log.d("notifications", "sleep failure");
                    }
                }

                //When the loop is finished, updates the notification
                mBuilder.setProgress(0, 0, false)
                        .setContentText("Download complete");
                notificationManager.notify(0, mBuilder.build());
            }
        }).start();
    }

    private void onShowStaticProgress(){
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentText("Download in progress")
                        .setSmallIcon(R.drawable.ic_search_white_24dp)
                        .setAutoCancel(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int incr = 0;
                for(;incr <= 100; incr += 5){
                    //set indeterminate state to true
                    mBuilder.setProgress(0, 0, true);
                    //updates the notification
                    notificationManager.notify(0, mBuilder.build());
                    try{
                        Thread.sleep(500);
                    }catch (Exception e){
                        Log.d("notifications", "sleep failure");
                    }
                }

                //When the loop is finished, updates the notification
                mBuilder.setProgress(0, 0, false)
                        .setContentText("Download complete");
                notificationManager.notify(0, mBuilder.build());
            }
        }).start();
    }

    private void onShowCustomNotification(){
        //Create view which can be displayed in another process
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.custom_notification_layout);

        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("title", "my title");
        intent.putExtra("text", "my text");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_search_white_24dp)
                .setTicker("ticker")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContent(remoteViews);

        //Sets view resources
        remoteViews.setImageViewResource(R.id.imagenotileft, R.drawable.ic_search_white_24dp);
        remoteViews.setImageViewResource(R.id.imagenotiright, R.drawable.ic_search_white_24dp);
        remoteViews.setTextViewText(R.id.title, "TITLE !!!");
        remoteViews.setTextViewText(R.id.text, "TEXT !!!");

        //Notify
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, builder.build());
    }
}
