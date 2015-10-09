package com.example.vkirillov.transitions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Scene mAScene;
    private Scene mAnotherScene;
    private boolean mReverse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the scene root for the scenes in this app
        ViewGroup mSceneRoot = (ViewGroup) findViewById(R.id.sceneRoot);
        mAScene = Scene.getSceneForLayout(mSceneRoot, R.layout.a_scene, this);
        mAnotherScene = Scene.getSceneForLayout(mSceneRoot, R.layout.another_scene, this);

        Button btn = (Button) findViewById(R.id.btnAutoTransition);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeTransition(R.transition.auto);
            }
        });

        btn = (Button) findViewById(R.id.btnFadeTransition);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeTransition(R.transition.fade);
            }
        });
    }

    private void makeTransition(int resourceId) {
        //Reversed transition from frame 2 to frame 1
        if(mReverse){
            Transition mAutoTransition = TransitionInflater.from(MainActivity.this)
                    .inflateTransition(resourceId);
            mAutoTransition.setDuration(5000);
            TransitionManager.go(mAScene, mAutoTransition);
        }else{
            //Transition from frame 1 to frame 2
            Transition mAutoTransition = TransitionInflater.from(MainActivity.this)
                .inflateTransition(resourceId);
            mAutoTransition.setDuration(5000);
            TransitionManager.go(mAnotherScene, mAutoTransition);
        }
        mReverse = !mReverse;
    }
}
