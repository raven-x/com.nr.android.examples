package com.nimura.tabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {

    static {RoboGuice.setUseAnnotationDatabases(false);}

    @InjectView(R.id.tabHost)
    private TabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTabHost.setup();

        TabHost.TabSpec tabSpec = null;

        //Creates a new tab
        tabSpec = mTabHost.newTabSpec("tag1");
        //Text
        tabSpec.setIndicator("Вкладка 1");
        //Content - from FrameLayout with id
        tabSpec.setContent(R.id.tab1);
        mTabHost.addTab(tabSpec);

        //!!! IMPORTANT: in holo theme only icon or text can be
        //Text and picture dependent on tab state
        tabSpec = mTabHost.newTabSpec("tag2");
        tabSpec.setIndicator(null, getResources().getDrawable(R.drawable.tab_icon_selector));
        tabSpec.setContent(R.id.tab2);
        mTabHost.addTab(tabSpec);

        tabSpec = mTabHost.newTabSpec("tag3");
        //Create tab header from layout file
        View v = getLayoutInflater().inflate(R.layout.tab_header, null);
        ImageView imgView = (ImageView) v.findViewById(android.R.id.icon);
        imgView.setImageDrawable(getResources().getDrawable(R.drawable.tab_icon_selector));
        tabSpec.setIndicator(v);
        tabSpec.setContent(R.id.tab3);
        mTabHost.addTab(tabSpec);

        //Second tab is chosen by default
        mTabHost.setCurrentTabByTag("tag2");

        //Tab switching handler
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(getBaseContext(), "tabId = " + tabId, Toast.LENGTH_LONG).show();
            }
        });
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
