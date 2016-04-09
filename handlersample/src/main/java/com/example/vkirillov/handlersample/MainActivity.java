package com.example.vkirillov.handlersample;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/***
 * Demonstrates using of Handler
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "handlersample";

    private Button btnSendMessage;
    private Button btnSendRunnable;
    private ThreadWithHandler thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thread = new ThreadWithHandler();
        thread.start();

        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
        btnSendRunnable = (Button) findViewById(R.id.btnSendRunnable);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = thread.getHandler().obtainMessage(123);
                Bundle bundle = new Bundle();
                bundle.putString("some", "some");
                msg.setData(bundle);

                //send to recipient from which the message was obtained
                msg.sendToTarget();
                //thread.getHandler().sendMessage(msg); //or send it in a common way
            }
        });
        btnSendRunnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Do some");
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        thread.getHandler().getLooper().quitSafely();//safely stops the message loop
        super.onDestroy();
    }
}
