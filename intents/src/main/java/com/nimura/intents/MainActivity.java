package com.nimura.intents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_FILE = 2;

    private Button btnImplicitIntent;
    private Button btnShowChooser;
    private Button btnAddCalendarEvent;
    private Button btnCapturePicture;
    private Button btnOpenImageFile;
    private Button btnOpenGeoLocation;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnImplicitIntent = (Button) findViewById(R.id.btnImplicitIntent);
        btnImplicitIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendImplicitIntent();
            }
        });

        btnShowChooser = (Button) findViewById(R.id.btnShowChooser);
        btnShowChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowChooser();
            }
        });

        btnAddCalendarEvent = (Button) findViewById(R.id.btnAddCalendarEvent);
        btnAddCalendarEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCalendarEvent();
            }
        });

        btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCapturePicture();
            }
        });

        btnOpenImageFile = (Button) findViewById(R.id.btnOpenImageFile);
        btnOpenImageFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenImageFile();
            }
        });

        btnOpenGeoLocation = (Button) findViewById(R.id.btnOpenGeoLocation);
        btnOpenGeoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenGeoLocation();
            }
        });

        imgView = (ImageView) findViewById(R.id.imageView);
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

    private void onSendImplicitIntent(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "some text");
        sendIntent.setType(HTTP.PLAIN_TEXT_TYPE);

        if(sendIntent.resolveActivity(getPackageManager()) != null){
            startActivity(sendIntent);
        }
    }

    private void onShowChooser(){
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        //Creates chooser dialog explicitly
        Intent chooser = Intent.createChooser(sendIntent, "Share with");
        if(sendIntent.resolveActivity(getPackageManager()) != null){
            startActivity(chooser);
        }
    }

    private void onAddCalendarEvent(){
        Calendar beginTime = new GregorianCalendar();
        beginTime.set(2015, 06, 28);
        Calendar endTime = new GregorianCalendar();
        endTime.set(2015, 06, 29);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "Meet with Vitani")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Dreamland")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    private void onCapturePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void onOpenImageFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_FILE);
        }
    }

    private void onOpenGeoLocation(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:47.6,-122.3?z=11"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imageBitmap);
        }else if(requestCode == REQUEST_IMAGE_FILE && resultCode == RESULT_OK){
            try {
                Uri fullPhotoUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri);
                imgView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
