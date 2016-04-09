package com.example.vkirillov.handlersample;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;

/**
 * Thread with handler which processes messages
 */
public class ThreadWithHandler extends Thread{
    private static final String TAG = "handlersample";
    private Object startupLock = new Object();
    private Handler handler;

    public Handler getHandler() {
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare(); //prepare message queue
        initIdleHandler();
        //create handler
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 123:
                        processPrintSome(msg);
                        break;
                }
                super.handleMessage(msg);
            }
        };
        //or new Handler(callback);
        Looper.loop();//start message queue processing
    }

    /**
     * Optional idle handler initialization
     */
    private void initIdleHandler(){
        // Get the message queue of the current thread
        MessageQueue mq = Looper.myQueue();
        //Add idle handle
        //IMPORTANT: this must not be a long running operation
        mq.addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Log.d(TAG, "Processing something during message queue idle ...");
                return true; //The handler is kept active
            }
        });
    }

    private void processPrintSome(Message msg) {
        try {
            //long running operation imitation
            Thread.sleep(2000);
            Log.d(TAG, "Received: " + msg.getData().getString("some"));

            //Do something on UI thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Do something on UI thread");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
