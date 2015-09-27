package com.example.vkirillov.asyncprogressdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nr.asyncprogressdialog.AbstractTaskWithProgressDialog;
import com.nr.asyncprogressdialog.RetainedTaskFragment;

public class MainActivity extends AppCompatActivity {
    private Button mBtnActionSpinnable;
    private Button mBtnActionProgress;
    private RetainedTaskFragment mRetainedTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnActionSpinnable = (Button) findViewById(R.id.runTask);
        mBtnActionSpinnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbstractTaskWithProgressDialog sat = new AbstractTaskWithProgressDialog<Object, Object>(
                        mRetainedTaskFragment, "SampleTask", "Running", "SampleTask", false) {

                    @Override
                    protected Object doInBackground(Object[] params) {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object integer) {
                        super.onPostExecute(integer);
                        mBtnActionSpinnable.setEnabled(true);
                        mBtnActionProgress.setEnabled(true);
                    }

                    @Override
                    protected void onCancelled(Object o) {
                        super.onCancelled(o);
                        mBtnActionSpinnable.setEnabled(true);
                        mBtnActionProgress.setEnabled(true);
                    }

                };
                mBtnActionSpinnable.setEnabled(false);
                mBtnActionProgress.setEnabled(false);
                sat.execute();

            }
        });
        mBtnActionProgress = (Button) findViewById(R.id.runTaskWithProgress);
        mBtnActionProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbstractTaskWithProgressDialog sat = new AbstractTaskWithProgressDialog<Object, Object>(
                        mRetainedTaskFragment, "SampleTask", "Progress...", "SampleTaskWithProgress", true, 100) {
                    @Override
                    protected Object doInBackground(Object[] params) {
                        for(int i=0;i<=100;i+=10){
                            if(isCancelled()){
                                return null;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            publishProgress(i+10);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object integer) {
                        super.onPostExecute(integer);
                        mBtnActionSpinnable.setEnabled(true);
                        mBtnActionProgress.setEnabled(true);
                    }

                    @Override
                    protected void onCancelled(Object o) {
                        super.onCancelled(o);
                        mBtnActionSpinnable.setEnabled(true);
                        mBtnActionProgress.setEnabled(true);
                    }

                };
                mBtnActionSpinnable.setEnabled(false);
                mBtnActionProgress.setEnabled(false);
                sat.execute();
            };
        });

        mRetainedTaskFragment = RetainedTaskFragment.establishRetainedMonitoredFragment(MainActivity.this);
    }
}
