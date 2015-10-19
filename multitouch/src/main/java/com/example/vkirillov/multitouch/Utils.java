package com.example.vkirillov.multitouch;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by vkirillov on 13.10.2015.
 */
public final class Utils {
    public static final String LOG_TAG = "multitouch_events";

    private Utils(){}

    public static void describeMotionEvent(MotionEvent event){
        StringBuilder touchString = new StringBuilder("!---On touch event: \n");

        touchString.append(String.format("down time: %d, \n", event.getDownTime()));
        touchString.append(String.format("elapsed: %d, \n", event.getEventTime() - event.getDownTime()));
        touchString.append(String.format("action: %d, \n", event.getAction()));
        touchString.append(String.format("edge: %d, \n", event.getEdgeFlags()));
        int pointersCount = event.getPointerCount();
        touchString.append(String.format("number of pointers: %d\n", pointersCount));
        for(int pointerIndex=0; pointerIndex < pointersCount; pointerIndex++){
            int pointerId = event.getPointerId(pointerIndex);
            touchString.append(String.format("Pointer index: %d, pointer id: %d\n", pointerIndex, pointerId));
            touchString.append(String.format("\tx: %.1f, y: %.1f, \n", event.getX(pointerIndex), event.getY(pointerIndex)));
            touchString.append(String.format("\tpressure: %.1f, \n", event.getPressure(pointerIndex)));
            touchString.append(String.format("\tsize: %.1f, \n", event.getSize(pointerIndex)));
        }
        touchString.append('\n');
        Log.i(LOG_TAG, touchString.toString());
    }

    public static void log(String format, Object ... args){
        Log.i(LOG_TAG, String.format(format, args));
    }
}
