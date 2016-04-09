package com.example.vkirillov.gesture;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private TextView textView;
    private GestureLibrary gestureLibrary;
    private GestureOverlayView overlayView;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1f;
    private Matrix matrix = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.img);
        textView = (TextView) findViewById(R.id.txt);

        //Load precompiled gesture from library
        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if(!gestureLibrary.load()){
            //Something went wrong
            finish();
        }
        //Get overlay widget
        overlayView = (GestureOverlayView) findViewById(R.id.overlayWidget);
        overlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                onGesture(gesture);
            }
        });

        //Use the standard gesture
        mScaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();
                mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
                matrix.setScale(mScaleFactor, mScaleFactor);
                image.setImageMatrix(matrix);
                image.invalidate();
                return true;
            }

        });
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScaleDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    /**
     * Process gesture
     * @param gesture
     */
    private void onGesture(Gesture gesture){
        List<Prediction> predictions = gestureLibrary.recognize(gesture);
        if(!predictions.isEmpty()){
            Prediction prediction = predictions.get(0);
            if(prediction.score > 1.0){
                if("prev".equals(prediction.name)){
                    Toast.makeText(this, "Gesture \"Prev detected\" ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
