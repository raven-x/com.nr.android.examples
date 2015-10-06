package com.example.vkirillov.asyncprogressdialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.nr.asyncprogressdialog.AbstractTaskWithProgressBar;
import com.nr.asyncprogressdialog.AbstractTaskWithProgressDialog;
import com.nr.asyncprogressdialog.RetainedTaskFragment;

public class MainActivity extends AppCompatActivity {
    private Button mBtnActionSpinnable;
    private Button mBtnActionProgress;
    private Button mBtnActionBar;
    private ProgressBar mProgressBar;
    private RetainedTaskFragment mRetainedTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setProgress(0);
        mProgressBar.setVisibility(View.GONE);
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

        mBtnActionBar = (Button) findViewById(R.id.runTaskWithBar);
        mBtnActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                new AbstractTaskWithProgressBar<Object, Object>(mRetainedTaskFragment,
                        R.id.progressBar, false, 10){

                    @Override
                    protected Object doInBackground(Object... params) {
                        for(int i=0;i<=10;i++){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            publishProgress(i + 1);
                        }
                        return null;
                    }

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        super.onProgressUpdate(values);
                        mProgressBar.setProgress(values[0]);
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        mProgressBar.setVisibility(View.GONE);
                    }

                }.execute();
            }
        });

        mRetainedTaskFragment = RetainedTaskFragment.establishRetainedMonitoredFragment(MainActivity.this);
    }
}
