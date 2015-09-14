package com.nimura.dialogs;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {

    @InjectView(R.id.btnShowSimpleDialog)
    private Button mBtnShowMsgDlg;

    @InjectView(R.id.btnShowListDialog)
    private Button mBtnShowListDialog;

    @InjectView(R.id.btnShowPersistentList)
    private Button mBtnShowPersistentListDialog;

    @InjectView(R.id.btnShowCustomLayout)
    private Button mBtnShowCustomLayout;

    @InjectView(R.id.btnShowEmbedded)
    private Button mBtnShowEmbedded;

    static {RoboGuice.setUseAnnotationDatabases(false);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBtnShowMsgDlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleDialogFragment().show(getFragmentManager(), "fire");
            }
        });
        mBtnShowListDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListDialogFragment().show(getFragmentManager(), "list");
            }
        });
        mBtnShowPersistentListDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PersistentListDialogFragment().show(getFragmentManager(), "ps");
            }
        });
        mBtnShowCustomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomLayoutDialogFragment().show(getFragmentManager(), "cstm");
            }
        });
        mBtnShowEmbedded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                CustomEmbeddableDialogFragment cedf = new CustomEmbeddableDialogFragment();

                FragmentTransaction ft = fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.add(android.R.id.content, cedf)
                        .addToBackStack(null)
                        .commit();
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
