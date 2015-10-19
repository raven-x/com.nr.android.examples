package com.example.vkirillov.propertyanimation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.txt);

        Button btn = (Button) findViewById(R.id.btnFadeOut);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAnimation(v);
            }
        });
        btn = (Button) findViewById(R.id.btnFadeOutIn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sequentialAnimation();
            }
        });
        btn = (Button) findViewById(R.id.btnFadeOutInBuilder);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sequentialBuilderAnimation();
            }
        });
        btn = (Button) findViewById(R.id.btnFadeOutInFromFile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sequentialFromFileAnimation();
            }
        });
        btn = (Button) findViewById(R.id.btnPropertyValueHolder);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                propertyValueHolderAnimation();
            }
        });
        btn = (Button) findViewById(R.id.btnViewPropertyAnimator);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPropertyAnimator();
            }
        });
        btn = (Button) findViewById(R.id.btnCustonEvaluator);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customEvaluator();
            }
        });
        btn = (Button) findViewById(R.id.btnKeyFrames);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyFramesAnimation();
            }
        });
        btn = (Button) findViewById(R.id.btnTxtBoxAndButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, textbox_and_button.class);
                startActivity(intent);
            }
        });
    }

    /**
     * First press - fade in
     * Second press - fade out and so on
     * @param v
     */
    private void toggleAnimation(View v){
        Button button = (Button) v;
        //From visible to invisible
        if(textView.getAlpha() != 0){
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 0.0f);
            fadeOut.setDuration(5000);
            fadeOut.start();
            button.setText("Fade in");
        }else{
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 1.0f);
            fadeIn.setDuration(5000);
            fadeIn.start();
            button.setText("Fade out");
        }
    }

    /**
     * Fade out then fade in sequentially
     */
    private void sequentialAnimation(){
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 0.0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 1.0f);
        AnimatorSet as = new AnimatorSet();
        as.playSequentially(fadeOut, fadeIn);
        as.setDuration(5000);
        as.start();
    }

    /**
     * Fade out then fade in using builder
     */
    private void sequentialBuilderAnimation(){
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 0.0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 1.0f);
        AnimatorSet as = new AnimatorSet();
        as.play(fadeOut).before(fadeIn);
        //Same thing
        //as.play(fadeIn).after(fadeOut);
        as.setDuration(5000);
        as.start();
    }

    /**
     * Fade out then fade in using XML declaration
     */
    private void sequentialFromFileAnimation(){
        AnimatorSet as = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.fade);
        as.setTarget(textView);
        as.start();
    }

    /**
     * Animate multiple values simultaneously
     */
    private void propertyValueHolderAnimation(){
        float h = textView.getHeight();
        float w = textView.getWidth();
        float x = textView.getX();
        float y = textView.getY();

        textView.setX(w);
        textView.setY(h);

        //From current X pos to x
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", x);
        //From current Y pos to y
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", y);

        ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(textView, pvhX, pvhY);
        oa.setDuration(5000);
        oa.setInterpolator(new AccelerateDecelerateInterpolator());
        oa.start();
    }

    /**
     * Uses optimized ViewPropertyAnimator
     */
    private void viewPropertyAnimator(){
        float h = textView.getHeight();
        float w = textView.getWidth();
        float x = textView.getX();
        float y = textView.getY();

        textView.setX(w);
        textView.setY(h);

        //Get a ViewPropertyAnimator from the text view
        ViewPropertyAnimator vpa = textView.animate();
        vpa.setDuration(5000);

        //Sets targets
        vpa.x(x);
        vpa.y(y);

        //The animation automatically starts then the UI thread gets to it
        vpa.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    /**
     * Use custom TypeEvaluator for custom view property
     */
    private void customEvaluator(){
        float h = textView.getHeight();
        float w = textView.getWidth();
        float x = textView.getX();
        float y = textView.getY();

        PointF startingPoint = new PointF(w, h);
        PointF targetPoint = new PointF(x, y);

        MyAnimatableView mav = new MyAnimatableView(textView);
        ObjectAnimator oa = ObjectAnimator.ofObject(mav, "currentPoint",
                new MyPointEvaluator(), startingPoint, targetPoint);
        oa.setDuration(5000);
        oa.setInterpolator(new AccelerateDecelerateInterpolator());
        oa.start();
    }

    /**
     * Keyframe animation
     */
    private void keyFramesAnimation(){
        float h = textView.getHeight();
        float w = textView.getWidth();
        float x = textView.getX();
        float y = textView.getY();

        //Start frame: 0.2, alpha: 0.8
        Keyframe kf0 = Keyframe.ofFloat(0.2f, 0.8f);
        //Middle frame: 0.5, alpha: 0.2
        Keyframe kf1 = Keyframe.ofFloat(0.5f, 0.1f);
        //End frame: 0.8, alpha: 0.8
        Keyframe kf2 = Keyframe.ofFloat(0.8f, 0.8f);

        //Property values holder using keyframes
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", w, x);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", h, y);

        ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(textView, pvhAlpha, pvhX, pvhY);
        oa.setDuration(5000);
        oa.start();
    }
}
