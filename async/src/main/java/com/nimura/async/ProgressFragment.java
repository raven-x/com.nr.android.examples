package com.nimura.async;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Limi on 27.06.2015.
 */
public class ProgressFragment extends Fragment {
    private Button btnLaunch;
    private TextView statusView;
    private ProgressBar indicatorBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_fragment, container, false);
        btnLaunch = (Button) view.findViewById(R.id.btnLaunch);
        statusView = (TextView) view.findViewById(R.id.status);
        indicatorBar = (ProgressBar) view.findViewById(R.id.indicator);

        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLaunch.setEnabled(false);
                Task task = new Task();
                task.execute();

            }
        });

        return view;
    }

    private class Task extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            for(int i=0;i<100;i++){
                publishProgress(i);
                SystemClock.sleep(400);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int v = values[0] + 1;
            indicatorBar.setProgress(v);
            statusView.setText(String.format("Status: %d", v));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            btnLaunch.setEnabled(true);
            Toast.makeText(getActivity(), "Task complete", Toast.LENGTH_SHORT).show();
        }
    }
}
