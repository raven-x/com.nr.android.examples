package com.nimura.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LocalServiceConnection mConnection = new LocalServiceConnection();
    private BindableListener bindableListener;
    private Button btnStartService;
    private Button btnStartDownload;
    private Button btnBindService;
    private Button btnStartIntentService;
    private BindableService mService;
    private boolean isBound;
    private ResultReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResultReceiver = new ResultReceiver(new Handler()){
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if(resultCode == SampleIntentService.RESPONSE_OK) {
                    String response = resultData.getString(SampleIntentService.RESPONSE);
                    if (response != null) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        bindableListener = new BindableListener(this);
        setContentView(R.layout.activity_main);
        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelloIntentService.startActionFoo(MainActivity.this, "fff", "hahaha");
            }
        });
        btnStartDownload = (Button) findViewById(R.id.btnStartDownload);
        btnStartDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.nr.ACTION_DOWNLOAD");
                intent.setData(Uri.parse("http://www.lionking.org"));
                startService(intent);
            }
        });
        btnBindService = (Button) findViewById(R.id.btnBindService);
        btnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.getSome(bindableListener);
            }
        });
        btnStartIntentService = (Button) findViewById(R.id.btnStartIntentService);
        btnStartIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SampleIntentService.class);
                intent.putExtra(SampleIntentService.RECEIVER, mResultReceiver);
                startService(intent);
            }
        });

        Intent intent = new Intent(MainActivity.this, BindableService.class);
        bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
        isBound = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isBound){
            //Unbind on destroy
            unbindService(mConnection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * Callback to receive service interface
     */
    private class LocalServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //Receive service interface
            mService = ((BindableService.ServiceBinder) service).getService();
            Log.d(TAG, "Established connection to bindable service");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Connection to bindable service terminated");
            mService = null;
        }
    }

    /**
     * Static and weak referenced to activity listener
     */
    private static class BindableListener implements BindableService.OperationListener{

        private final WeakReference<MainActivity> mWeakActivity;

        public BindableListener(MainActivity activity){
            mWeakActivity = new WeakReference<>(activity);
        }

        @Override
        public void onOperationDone(final String result) {
            final MainActivity activity = mWeakActivity.get();
            if(activity != null){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
