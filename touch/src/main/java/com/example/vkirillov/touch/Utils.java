package com.example.vkirillov.touch;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by vkirillov on 13.10.2015.
 */
public final class Utils {
    public static final String LOG_TAG = "touch_events";

    private Utils(){}

    public static void describeMotionEvent(MotionEvent event){
        StringBuilder touchString = new StringBuilder("On touch event ");
        touchString.append(String.format("x: %.1f, y: %.1f, ", event.getX(), event.getY()));
        touchString.append(String.format("down time: %d, ", event.getDownTime()));
        touchString.append(String.format("elapsed: %d, ", event.getEventTime() - event.getDownTime()));
        touchString.append(String.format("pressure: %.1f, ", event.getPressure()));
        touchString.append(String.format("size: %.1f, ", event.getSize()));
        touchString.append(String.format("action: %d, ", event.getAction()));
        touchString.append(String.format("edge: %d", event.getEdgeFlags()));
        Log.i(LOG_TAG, touchString.toString());
    }

    public static void log(String format, Object ... args){
        Log.i(LOG_TAG, String.format(format, args));
    }
}
