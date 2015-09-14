package com.nimura.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nimura on 11.06.2015.
 */
public class AppEntryTools {
    private AppEntryTools(){}

    public static List<AppEntry> getAppEntries(Context context){
        List<AppEntry> result = new LinkedList<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(mainIntent, 0);
        for(ResolveInfo ri : apps){
            Drawable drawable = ri.loadIcon(context.getPackageManager());
            String label = ri.loadLabel(context.getPackageManager()).toString();
            result.add(new AppEntry(drawable, label, ri.activityInfo.packageName, ri.activityInfo.name));
        }

        return result;
    }
}
