package com.example.vkirillov.touch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private VelocityTracker velocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TouchButton btnTouch = (TouchButton) findViewById(R.id.btnTouch);
        btnTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Utils.log("---From activity onTouch");
                Utils.describeMotionEvent(event);

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(velocityTracker == null){
                            velocityTracker = VelocityTracker.obtain();
                        }else {
                            velocityTracker.clear();
                        }
                        velocityTracker.addMovement(event);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        velocityTracker.addMovement(event);
                        velocityTracker.computeCurrentVelocity(1000);
                        Utils.log("X velocity is "
                                + velocityTracker.getXVelocity() +
                                " pixels per second");
                        Utils.log("Y velocity is "
                                + velocityTracker.getYVelocity() +
                                " pixels per second");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        Utils.log("X velocity is "
                                + velocityTracker.getXVelocity() +
                                " pixels per second");
                        Utils.log("Y velocity is "
                                + velocityTracker.getYVelocity() +
                                " pixels per second");
                        velocityTracker.recycle();
                        velocityTracker = null;
                        break;
                }

                //Returning true will indicate that handler consumed the event
                return true;
            }
        });

    }
}
