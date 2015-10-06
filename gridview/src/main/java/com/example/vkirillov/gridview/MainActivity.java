package com.example.vkirillov.gridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.grid);
        List<Product> products = new LinkedList<>();
        products.add(new Product("Coca-Cola"));
        products.add(new Product("Pepsi"));
        products.add(new Product("Fanta"));
        products.add(new Product("Sprite"));
        gridView.setAdapter(new ButtonAdapter(this, products));
    }
}
