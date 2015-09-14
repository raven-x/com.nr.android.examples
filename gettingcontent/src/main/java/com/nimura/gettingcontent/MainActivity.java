package com.nimura.gettingcontent;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Projection is a specific set of columns to retrieve from database
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
    };
    private static final Uri mContactsUri = ContactsContract.Contacts.CONTENT_URI;
    private static final int CONTENT_LOADER_ID = 0;

    private TextView txtResult;
    private Button btnGetResult;

    private final MyLoaderCallback mlc = new MyLoaderCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = (TextView) findViewById(R.id.contentResult);
        btnGetResult = (Button) findViewById(R.id.getResult);
        btnGetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String nameToFind = "Vitani";
//                String whereClause = String.format("%s=?", PROJECTION[1]);
//                String[] args = {nameToFind};
//                String orderBy = String.format("%s ASC", PROJECTION[1]);
//
//                Cursor cursor = getContentResolver().query(mContactsUri, PROJECTION, whereClause, args, orderBy);
//                try {
//                    if (cursor == null) {
//                        Toast.makeText(MainActivity.this, "Cannot recieve cursor object", Toast.LENGTH_LONG).show();
//                    } else if (cursor.getCount() < 1) {
//                        Toast.makeText(MainActivity.this, "Cursor is empty", Toast.LENGTH_LONG).show();
//                    }
//                    if (cursor.moveToFirst()) {
//                        int column_index = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
//                        txtResult.setText(cursor.getString(column_index));
//                    }
//                }finally {
//                    if(cursor != null && !cursor.isClosed()){
//                        cursor.close();
//                    }
//                }
                getLoaderManager().restartLoader(CONTENT_LOADER_ID, null, mlc);
            }
        });

        getLoaderManager().initLoader(CONTENT_LOADER_ID, null, mlc);
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

    /**
     * Class for async content loading from content provider
     */
    private class MyLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            //argument value to find
            String nameToFind = "Vitani";
            //SQL query WHERE clause with ? as placeholders
            String whereClause = String.format("%s=?", PROJECTION[1]);
            //Array of the arguments to find (arguments are inserted to placeholders)
            String[] selArgs = {nameToFind};
            //SQL query ORDER BY clause
            String orderBy = String.format("%s ASC", PROJECTION[1]);
            //Build a new cursor loader to start asynchronous data loading
            return new CursorLoader(MainActivity.this, mContactsUri, PROJECTION, whereClause, selArgs, orderBy);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if (cursor == null) {
                Toast.makeText(MainActivity.this, "Cannot receive cursor object", Toast.LENGTH_LONG).show();
            } else if (cursor.getCount() < 1) {
                Toast.makeText(MainActivity.this, "Cursor is empty", Toast.LENGTH_LONG).show();
            }
            if (cursor.moveToFirst()) {
                //If query completed successfully, get the necessary column
                int column_index = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
                txtResult.setText(cursor.getString(column_index));
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            
        }
    }
}
