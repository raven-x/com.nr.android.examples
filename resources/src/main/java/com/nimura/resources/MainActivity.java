package com.nimura.resources;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;


public class MainActivity extends ActionBarActivity {
    private Button btnOpenXml;
    private Button btnOpenRaw;
    private TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenXml = (Button) findViewById(R.id.btnGetXml);
        btnOpenRaw = (Button) findViewById(R.id.btnGetRaw);
        txtContent = (TextView) findViewById(R.id.txtContent);
        btnOpenXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    txtContent.setText(onOpenXml());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnOpenRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    txtContent.setText(onOpenRaw());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private String onOpenXml() throws IOException, XmlPullParserException {
        Resources res = getResources();
        XmlResourceParser xrp = res.getXml(R.xml.sample);
        xrp.next();
        int eventType = xrp.getEventType();
        StringBuilder result = new StringBuilder();
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    result.append("Start document: ").append(xrp.getName()).append("\r\n");
                    break;
                case XmlPullParser.START_TAG:
                    result.append("Start tag: ").append(xrp.getName()).append("\r\n");
                    if("Rubric".equals(xrp.getName())){
                        result.append(String.format("\tAttribute: %s = %s\r\n",
                                xrp.getAttributeName(0), xrp.getAttributeValue(0)));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    result.append("End tag: ").append(xrp.getName()).append("\r\n");
                    break;
                case XmlPullParser.TEXT:
                    result.append("Text: ").append(xrp.getText()).append("\r\n");
                    break;
            }
            eventType = xrp.next();
        }
        result.append("End tag: " + xrp.getName());
        return result.toString();
    }

    private String onOpenRaw() throws IOException {
        StringBuilder result = new StringBuilder();
        Resources res = getResources();
        //raw file is identified by filename, not by id
        try(InputStream is = res.openRawResource(R.raw.rawtext);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr)) {
            String str;
            while((str = br.readLine()) != null){
                result.append(str);
            }
            return result.toString();
        }
    }
}
