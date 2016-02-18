package com.example.vkirillov.propertyanimation;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AnimatedFrameActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_frame);

        fragment = new AnimatedFragment();

        //Begin fragment transaction
        ft = getFragmentManager().beginTransaction();
        //Specific animation resources for fragments that are entering and exiting in this transaction
        ft.setCustomAnimations(R.animator.fragment_appearance, R.animator.fragment_disappearance);
        //Repace frame layout mock with created fragment
        ft.replace(R.id.animatedFragment, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
