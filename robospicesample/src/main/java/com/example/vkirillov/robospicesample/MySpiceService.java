package com.example.vkirillov.robospicesample;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.string.InFileStringObjectPersister;

/**
 * Spice service which cashes the result in the file system
 */
public class MySpiceService extends SpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        InFileStringObjectPersister persister = new InFileStringObjectPersister(application);
        cacheManager.addPersister(persister);
        return cacheManager;
    }

    @Override
    public int getThreadCount() {
        return Runtime.getRuntime().availableProcessors();
    }
}
