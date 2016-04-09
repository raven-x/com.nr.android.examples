package com.example.vkirillov.handlersample;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * HandlerThread is a thread with a message queue that incorporates a Thread, a Looper,
 and a MessageQueue.
 */
public class MyHandlerThread extends HandlerThread {

    private Handler handler;

    public MyHandlerThread() {
        super("MyHandlerThread");
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        handler = new Handler(getLooper()){

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 123:
                        break;
                }
            }
        };
    }


}
