package com.nimura.roboguiceinject;

import android.app.NotificationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.inject.Inject;

import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;


@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {
    //Turn the annotation database off
    static { RoboGuice.setUseAnnotationDatabases(false);}

    //Views
    @InjectView(R.id.txtView)
    TextView mTextView;
    @InjectView(R.id.btnGo)
    Button mBtnGo;
    @InjectView(R.id.btnRunSingletons)
    Button mBtnRunSingletons;

    //Resource
    @InjectResource(R.string.myString)
    String myString;

    //Services
    @Inject
    Vibrator mVibrator;
    @Inject
    NotificationManager mNotificationManager;

    //Singletons
    @Inject
    MySingleton mMySingleton;
    @Inject
    MyContextSingleton mMyContextSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextView.setText(myString);

        mBtnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGo();
            }
        });
        mBtnRunSingletons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRunSingletons();
            }
        });
    }

    private void onGo(){
        mVibrator.vibrate(1000L);
        mNotificationManager.cancelAll();
    }

    private void onRunSingletons(){
        mMySingleton.go();
        mMyContextSingleton.go();
    }
}
