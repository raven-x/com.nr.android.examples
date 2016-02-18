package com.example.vkirillov.robospicesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SimpleTextRequest;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final String REQUEST_ID = "request";
    /**
     * Spice service
     */
    private SpiceManager spiceManager = new SpiceManager(RetrofitDbSpiceService.class);
    private EditText txtResult;
    private Button btnRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = (EditText) findViewById(R.id.txtResult);
        btnRun = (Button) findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SampleRetrofitSpiceRequest request = new SampleRetrofitSpiceRequest();
                spiceManager.execute(request, REQUEST_ID, DurationInMillis.ONE_MINUTE,
                        new TextRequestListener(MainActivity.this, txtResult));
            }
        });
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    private static final class TextRequestListener implements RequestListener<OneTwoRetrofitJson>{

        private WeakReference<MainActivity> activityRef;
        private WeakReference<EditText> txtEditorRef;

        public TextRequestListener(MainActivity activity, EditText txtEd) {
            this.activityRef = new WeakReference<>(activity);
            this.txtEditorRef = new WeakReference<>(txtEd);
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if(activityRef.get() != null) {
                Toast.makeText(activityRef.get(), "failure", Toast.LENGTH_SHORT);
            }
        }

        @Override
        public void onRequestSuccess(OneTwoRetrofitJson s) {
            if(activityRef.get() != null) {
                Toast.makeText(activityRef.get(), "success !!!", Toast.LENGTH_SHORT);
            }
            if(txtEditorRef.get() != null){
                txtEditorRef.get().setText(s.one + ", " + s.two);
            }
        }
    }
}
