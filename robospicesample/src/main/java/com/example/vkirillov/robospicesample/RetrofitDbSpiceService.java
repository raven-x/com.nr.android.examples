package com.example.vkirillov.robospicesample;

import android.app.Application;

import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.ormlite.InDatabaseObjectPersisterFactory;
import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vkirillov on 16.12.2015.
 */
public class RetrofitDbSpiceService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "http://echo.jsontest.com";
    private static final String RDSS_DB = "rdss.db";

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();

        //Init database cache
        List<Class<?>> classCollection = new ArrayList<>();
        classCollection.add(OneTwoRetrofitEntity.class);

        RoboSpiceDatabaseHelper databaseHelper = new RoboSpiceDatabaseHelper( application, RDSS_DB, 1 );
        InDatabaseObjectPersisterFactory inDatabaseObjectPersisterFactory =
                new InDatabaseObjectPersisterFactory( application, databaseHelper, classCollection );
        cacheManager.addPersister( inDatabaseObjectPersisterFactory );
        return cacheManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Add retrofit interface
        addRetrofitInterface(IOneTwoRetrofitInterface.class);
    }

    @Override
    protected String getServerUrl() {
        //Return request base URL
        return BASE_URL;
    }
}
