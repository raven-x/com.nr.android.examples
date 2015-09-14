package com.nimura.retrosample;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {

    static {RoboGuice.setUseAnnotationDatabases(false);}

    @InjectView(R.id.btnGo)
    private Button mButtonGo;
    @InjectView(R.id.txtTxt)
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mButtonGo.setOnClickListener(v -> v.setBackgroundColor(0xff00ff00));

        List<Integer> lst = Stream.ofRange(0, 20)
                .collect(Collectors.toList());
        Stream.of(lst)
                .forEach(x -> Log.i("retrosample", "" + x));
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
