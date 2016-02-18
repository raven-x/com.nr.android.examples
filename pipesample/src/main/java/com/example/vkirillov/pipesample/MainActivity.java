package com.example.vkirillov.pipesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Demonstrates using of standard java pipe connection between threads
 */
public class MainActivity extends AppCompatActivity {
    private Thread workerThread;
    private PipedReader reader;
    private PipedWriter writer;
    private EditText editText;
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create pipe
        reader = new PipedReader();
        writer = new PipedWriter();

        //Connect reader to writer
        try {
            writer.connect(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        editText = (EditText) findViewById(R.id.txtEdit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    writer.write(s.subSequence(start, start + count).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Pass pipe reader to worker thread
        workerThread = new Thread(new PipedRunnable(isRunning, reader));
        //Start worker thread
        workerThread.start();
    }

    private class PipedRunnable implements Runnable{
        private final AtomicBoolean isRunning;
        private final PipedReader reader;

        public PipedRunnable(AtomicBoolean isRunning, PipedReader reader){
            this.isRunning = isRunning;
            this.reader = reader;
        }

        @Override
        public void run() {
            while(isRunning.get()){
                try{
                    int i;
                    while((i = reader.read()) != -1){
                        Log.d("piperesult", "char = " + (char)i);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning.set(false);
        //Close streams upon exit
        try {
            reader.close();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
