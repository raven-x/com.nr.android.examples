package com.example.vkirillov.dbconnection;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread safe database connection manager.
 * It uses only one connection at a time, tracks the number of clients
 * using the connection and can close it when no one use it
 */
public final class DatabaseManager {
    private final AtomicInteger mConnectionCounter = new AtomicInteger();
    private static DatabaseManager instance;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDatabaseHelper;

    /**
     * Private inner constructor
     * @param databaseHelper database helper instance
     */
    private DatabaseManager(SQLiteOpenHelper databaseHelper){
        mDatabaseHelper = databaseHelper;
    }

    /**
     * Initialize manager instance
     * @param databaseHelper database helper instance to be initialized with
     */
    public static synchronized void initializeInstance(SQLiteOpenHelper databaseHelper){
        if(instance == null){
            instance = new DatabaseManager(databaseHelper);
        }
    }

    /**
     * Returns single manager instance, previously initialized with databaseHelper
     * @return database manager instance
     */
    public static synchronized DatabaseManager getInstance(){
        if(instance == null){
            throw new IllegalStateException("You should call initializeInstance(...) method first");
        }
        return instance;
    }

    /**
     * Returns the database connection.
     * Manager increases inner counter, so client should call closeConnection() method
     * if client doesn't use the connection anymore
     * @return database object helper instance
     */
    public synchronized SQLiteDatabase getConnection(){
        if(mConnectionCounter.incrementAndGet() == 1){
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * This method is used then client stops using the connection
     * and wished to close it. Manager decreases the inner counter,
     * whenever it goes to zero, database connection is closed
     */
    public synchronized void closeConnection(){
        if(mConnectionCounter.decrementAndGet() == 0){
            mDatabase.close();
        }
    }
}
