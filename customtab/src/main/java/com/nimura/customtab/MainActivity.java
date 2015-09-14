package com.nimura.customtab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG1 = "tab1";
    private static final String TAG2 = "tab2";

    private final TabHost.TabContentFactory mTabContentFactory = new TabHost.TabContentFactory() {
        @Override
        public View createTabContent(String tag) {
            switch (tag){
                case TAG1:
                    return getLayoutInflater().inflate(R.layout.tab, null);
                case TAG2:
                    TextView txtView = new TextView(MainActivity.this);
                    txtView.setText("The tab content made manually");
                    return txtView;
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = null;

        //Tab which content is made using layout inflater
        tabSpec = tabHost.newTabSpec(TAG1);
        tabSpec.setContent(mTabContentFactory);
        tabSpec.setIndicator("Tab 1");
        tabHost.addTab(tabSpec);

        //Tab which content is made manually
        tabSpec = tabHost.newTabSpec(TAG2);
        tabSpec.setContent(mTabContentFactory);
        tabSpec.setIndicator("Tab 2");
        tabHost.addTab(tabSpec);
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
