package com.example.vkirillov.propertyanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;

public class textbox_and_button extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textbox_and_button);
        final EditText txt = (EditText) findViewById(R.id.txt);
        final Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float x = btn.getX();
                float targetX = btn.getWidth() + x;
                ObjectAnimator btnLeftCoordOa = ObjectAnimator.ofFloat(btn, "x", targetX);
                ObjectAnimator btnFadeIn = ObjectAnimator.ofFloat(btn, "alpha", 0.0f);
                //txt.getMeasuredWidth() + btn.getMeasuredWidth()
                ValueAnimator txtWidthOa = ValueAnimator.ofInt(txt.getMeasuredWidth(), 1000);
                txtWidthOa.addUpdateListener(new LayoutWidthAnimatorUpdateListener(txt));

                AnimatorSet as = new AnimatorSet();
                as.playTogether(btnLeftCoordOa, btnFadeIn,  txtWidthOa);

                as.setDuration(2000);
                as.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        btn.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                as.start();
            }
        });
        btn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else {

                }
            }
        });
    }

    private class LayoutWidthAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener{

        private View view;

        public LayoutWidthAnimatorUpdateListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int val = (int)animation.getAnimatedValue();
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            lp.width = val;
            view.setLayoutParams(lp);
            view.invalidate();
        }
    }
}
