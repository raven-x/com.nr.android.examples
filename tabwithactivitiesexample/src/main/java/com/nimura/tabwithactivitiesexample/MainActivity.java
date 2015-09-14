package com.nimura.tabwithactivitiesexample;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboFragmentActivity {

    @InjectView(android.R.id.tabhost)
    private FragmentTabHost mTabHost;

    static {RoboGuice.setUseAnnotationDatabases(false);}

    private final TabHost.TabContentFactory mTabContentFactory = new TabHost.TabContentFactory() {
        @Override
        public View createTabContent(String tag) {
            TextView txtView = new TextView(MainActivity.this);
            txtView.setText("The tab content made manually");
            return txtView;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec tabSpec;

        tabSpec = mTabHost.newTabSpec("tab1");
        View v = getLayoutInflater().inflate(R.layout.tab_indicator, null);
        ImageView imageView = (ImageView) v.findViewById(android.R.id.icon);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.tab_indicator_icon_selector));
        tabSpec.setIndicator(v);
        tabSpec.setContent(mTabContentFactory);
        mTabHost.addTab(tabSpec, TabFragment.class, null);

        tabSpec = mTabHost.newTabSpec("tab2");
        v = getLayoutInflater().inflate(R.layout.tab_indicator, null);
        imageView = (ImageView) v.findViewById(android.R.id.icon);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.tab_indicator_icon_selector));
        tabSpec.setIndicator(v);
        tabSpec.setContent(mTabContentFactory);
        mTabHost.addTab(tabSpec, TabFragment.class, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
