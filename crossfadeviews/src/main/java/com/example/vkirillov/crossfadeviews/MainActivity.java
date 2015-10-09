package com.example.vkirillov.crossfadeviews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final int CROSSFADING_DURATION = 3000;

    private View mContentView;
    private View mLoadingView;
    private boolean mReversed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Invisible by default
        mContentView = findViewById(R.id.content);
        mLoadingView = findViewById(R.id.loading_spinner);
        Button btn = (Button) findViewById(R.id.btnMakeTransition);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReversed){
                    animateViewTransition(mContentView, mLoadingView, CROSSFADING_DURATION);
                }else{
                    animateViewTransition(mLoadingView, mContentView, CROSSFADING_DURATION);
                }
                mReversed = !mReversed;
            }
        });
    }

    private void animateViewTransition(final View from, View to, int duration) {
        //Set target view visible, but transparent
        to.setAlpha(0f);
        to.setVisibility(View.VISIBLE);

        //Animate target view from transparent to opaque state
        to.animate()
                .alpha(1)
                .setDuration(duration)
                .setListener(null);

        //Animate source view from opaque to transparent
        from.animate()
                .alpha(0)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //Set source view visibility to GONE state
                        from.setVisibility(View.GONE);
                    }

                });
    }
}
