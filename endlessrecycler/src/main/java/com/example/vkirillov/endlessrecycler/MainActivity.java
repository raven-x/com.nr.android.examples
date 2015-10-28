package com.example.vkirillov.endlessrecycler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vkirillov.endlessrecycler.model.Model;
import com.example.vkirillov.endlessrecycler.view.ILoadListener;
import com.example.vkirillov.endlessrecycler.view.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Collection;
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

        handler = new Handler();

        mModel.addAll(generate(0, 15));

        mRecyclerView = (RecyclerView) findViewById(R.id.endlessRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerAdapter(mRecyclerView, mModel);
        adapter.setVisibleThreshold(2);
        adapter.setOnLoadListener(new OnLoadListener());
        mRecyclerView.setAdapter(adapter);
    }

    private Collection<Model> generate(int start, int count){
        List<Model> result = new ArrayList<>(count);
        for(int i=0;i<count;i++){
            Model entry = new Model();
            int value = start * 10 + i;
            entry.setTitle(Integer.toString(value));
            entry.setContent(String.format("Article %d", value));
            result.add(entry);
        }
        return result;
    }

    private class OnLoadListener implements ILoadListener{

        @Override
        public void onLoad(final int currentPage) {
            //This model_item will be added as progress bar for loading progress notification
            mModel.add(null);
            adapter.notifyItemInserted(mModel.size() - 1);

            //Delay loading for 5 seconds
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Generate a new set of data
                    Collection<Model> temp = generate(currentPage, 10);

                    //Remove progress model_item
                    mModel.remove(mModel.size() - 1);
                    adapter.notifyItemRemoved(mModel.size() - 1);

                    //Update model and view
                    mModel.addAll(temp);
                    adapter.setLoaded();
                    //Not effective but suitable for demonstration
                    adapter.notifyDataSetChanged();
                }
            }, 5000);
        }
    }
}
