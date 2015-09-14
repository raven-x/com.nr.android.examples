package com.nimura.asyncapploader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads apps list in background
 *
 *
 */
public class AppListLoader extends AsyncTaskLoader<List<AppEntry>> {

    private final PackageManager mPm;

    private List<AppEntry> mApps;

    public AppListLoader(Context context) {
        super(context);
        mPm = getContext().getPackageManager();
    }

    @Override
    public List<AppEntry> loadInBackground() {
        List<ApplicationInfo> apps = getApplicationInfos();

        final Context context = getContext();

        List<AppEntry> result = new ArrayList<>(apps.size());
        for(int i=0;i<apps.size();i++){
            AppEntry entry = new AppEntry(this, apps.get(i));
            //entry.loadLabel(context);
            result.add(entry);
        }

        return result;
    }

    private List<ApplicationInfo> getApplicationInfos() {
        List<ApplicationInfo> apps = mPm.getInstalledApplications(
                PackageManager.GET_DISABLED_COMPONENTS |
                PackageManager.GET_UNINSTALLED_PACKAGES
        );
        if(apps == null){
            apps = new ArrayList<>();
        }
        return apps;
    }
}
