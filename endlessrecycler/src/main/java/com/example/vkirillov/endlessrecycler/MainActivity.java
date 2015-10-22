package com.example.vkirillov.endlessrecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vkirillov.endlessrecycler.model.Model;
import com.example.vkirillov.endlessrecycler.view.ILoadListener;
import com.example.vkirillov.endlessrecycler.view.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<Model> mModel = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generate(0, 15);

        mRecyclerView = (RecyclerView) findViewById(R.id.endlessRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerAdapter(mRecyclerView, mModel);
        adapter.setVisibleThreshold(2);
        adapter.setOnLoadListener(new OnLoadListener());
        mRecyclerView.setAdapter(adapter);
    }

    private void generate(int start, int count){
        for(int i=0;i<count;i++){
            Model entry = new Model();
            int value = start * 10 + i;
            entry.setTitle(Integer.toString(value));
            entry.setContent(String.format("Article %d", value));
            mModel.add(entry);
        }
    }

    private class OnLoadListener implements ILoadListener{

        @Override
        public void onLoad(int currentPage) {
            generate(currentPage, 10);
            adapter.setLoaded();
            adapter.notifyDataSetChanged();
        }
    }
}
