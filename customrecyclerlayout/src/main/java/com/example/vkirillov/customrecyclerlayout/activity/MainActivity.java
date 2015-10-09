package com.example.vkirillov.customrecyclerlayout.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.vkirillov.customrecyclerlayout.R;
import com.example.vkirillov.customrecyclerlayout.data.DataProvider;
import com.example.vkirillov.customrecyclerlayout.data.FakeDataProvider;
import com.example.vkirillov.customrecyclerlayout.model.Article;
import com.example.vkirillov.customrecyclerlayout.view.ArticleAdapter;
import com.example.vkirillov.customrecyclerlayout.view.ArticleLayoutManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DataProvider dataProvider;
    private ArticleLayoutManager articleLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        articleLayoutManager = new ArticleLayoutManager();
        recyclerView.setLayoutManager(articleLayoutManager);
        ArticleAdapter articleAdapter = new ArticleAdapter();
        recyclerView.setAdapter(articleAdapter);
        dataProvider = new FakeDataProvider(this);
        List<Article> articles = dataProvider.getArticles();
        articleAdapter.setArticles(articles);
        recyclerView.setChildDrawingOrderCallback(new RecyclerView.ChildDrawingOrderCallback() {
            @Override
            public int onGetChildDrawingOrder(int childCount, int i) {
                return childCount - i - 1;
            }
        });
        articleAdapter.setItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int pos) {
                //articleLayoutManager.openItem(pos);
                //TODO
            }
        });
        findViewById(R.id.test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(4);
            }
        });
    }
}
