package com.example.vkirillov.animatedrecycler.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.vkirillov.animatedrecycler.R;
import com.example.vkirillov.animatedrecycler.model.Model;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Model> mModel = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter adapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.endlessRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new SlidingRightItemAnimator(150));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);

        adapter = new RecyclerAdapter(this, mModel);

        mRecyclerView.setAdapter(adapter);

        handler = new Handler();

        for (int i = 0; i < 5; i++) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Model entry = new Model();
                    entry.setTitle(String.format("Option %d", 1));
                    mModel.add(entry);
                    adapter.notifyItemInserted(mModel.size());
                }
            }, 500);
        }
    }

}
