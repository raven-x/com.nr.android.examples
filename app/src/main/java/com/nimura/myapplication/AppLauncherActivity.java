package com.nimura.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nimura.model.AppEntry;
import com.nimura.model.AppEntryTools;

import java.util.List;


public class AppLauncherActivity extends Activity {

    private GridView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launcher);

        view = (GridView) findViewById(R.id.main_view);
        final List<AppEntry> model = AppEntryTools.getAppEntries(this);
        view.setAdapter(new LauncherListAdapter(this, model.toArray(new AppEntry[0])));
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchApp(position, model);
            }
        });
    }

    private void launchApp(int position, List<AppEntry> model) {
        AppEntry entry = model.get(position);
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName component = new ComponentName(entry.getPackageName(), entry.getActivityName());
            intent.setComponent(component);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (Exception e){
            Log.e(
                    getResources().getString(R.string.app_name),
                    String.format(getResources().getString(R.string.error_app_launch),
                            entry.getPackageName(), entry.getActivityName()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_launcher, menu);
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
