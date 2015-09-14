package com.nimura.webbrowser;

import android.app.Activity;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WebActivity extends Activity {
    private EditText txtAddress;
    private Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        txtAddress = (EditText) findViewById(R.id.txt_address);
        btnGo = (Button) findViewById(R.id.btnGo);

        if(savedInstanceState == null){
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new WebView(), "web")
                    .commit();
        }

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webView = (WebView) getFragmentManager().findFragmentByTag("web");
                if(webView != null) {
                    String url = txtAddress.getText().toString();
                    if (!url.isEmpty()) {
                        webView.go(url);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        WebView webView = (WebView) getFragmentManager().findFragmentByTag("web");
        if(webView != null) {
            switch (id) {
                case R.id.refresh:
                    webView.refresh();
                    break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
