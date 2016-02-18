package com.example.vkirillov.asyncqueryhandlerexample;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorTreeAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleCursorTreeAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String[] CONTACTS_PROJECTION = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
    };

    private static final String[] PHONE_NUMBER_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    private static final int GROUP_ID_COLUMN_INDEX = 0;

    private static final int TOKEN_GROUP = 0;
    private static final int TOKEN_CHILD = 1;

    private QueryHandler mQueryHandler;
    private CursorTreeAdapter mAdapter;

    private ExpandableListView contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsList = (ExpandableListView) findViewById(R.id.contacts_list);

        mAdapter = new MyListAdapter(this,
                android.R.layout.simple_expandable_list_item_1,//List layout for groups
                android.R.layout.simple_expandable_list_item_2,//List layout for items
                new String[] {ContactsContract.Contacts.DISPLAY_NAME},//Columns for group
                new int[] {android.R.id.text1},//Views to display requested data for groups
                new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER},//Columns for items
                new int[] {android.R.id.text1});//Views to display requested data for items

        contactsList.setAdapter(mAdapter);

        mQueryHandler = new QueryHandler(this, mAdapter);

        //Query for people
        mQueryHandler.startQuery(TOKEN_GROUP,
                null,
                ContactsContract.Contacts.CONTENT_URI,
                CONTACTS_PROJECTION,
                ContactsContract.Contacts.HAS_PHONE_NUMBER, //SELECTION
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"); //SORT
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQueryHandler.cancelOperation(TOKEN_GROUP);
        mQueryHandler.cancelOperation(TOKEN_CHILD);
        mAdapter.changeCursor(null);
        mAdapter = null;
    }

    /**
     * Asynchronous query handler
     */
    private static final class QueryHandler extends AsyncQueryHandler{
        private CursorTreeAdapter mAdapter;

        public QueryHandler(Context context, CursorTreeAdapter adapter) {
            super(context.getContentResolver());
            this.mAdapter = adapter;
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            //Received result
            switch(token){
                case TOKEN_GROUP:
                    mAdapter.setGroupCursor(cursor);
                    break;
                case TOKEN_CHILD:
                    int groupPosition = (int) cookie;
                    mAdapter.setChildrenCursor(groupPosition, cursor);
                    break;
            }
        }
    }

    /**
     * ListView adapter which splits the content into groups
     */
    private final class MyListAdapter extends SimpleCursorTreeAdapter{

        public MyListAdapter(Context context, int groupLayout, int childLayout,
                             String[] groupFrom, int[] groupTo,
                             String[] childFrom, int[] childTo) {
            super(context, null, groupLayout, groupFrom, groupTo, childLayout,
                    childFrom, childTo);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // Given the group, we return a cursor for all the children
            // within that group
            // Return a cursor that points to this contact's phone numbers
            Uri.Builder builder = ContactsContract.Contacts.CONTENT_URI.buildUpon();
            ContentUris.appendId(builder, groupCursor.getLong(GROUP_ID_COLUMN_INDEX));
            builder.appendEncodedPath(ContactsContract.Contacts.Data.CONTENT_DIRECTORY);
            Uri phoneNumberUri = builder.build();

            mQueryHandler.startQuery(TOKEN_CHILD,
                    groupCursor.getPosition(),
                    phoneNumberUri,
                    PHONE_NUMBER_PROJECTION,
                    ContactsContract.CommonDataKinds.Phone.MIMETYPE + "=?",
                    new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE},
                    null);
            return null;
        }
    }
}
