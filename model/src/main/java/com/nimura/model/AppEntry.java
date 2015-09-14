package com.nimura.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Nimura on 11.06.2015.
 */
public class AppEntry {
    private final String label;
    private final String packageName;
    private final String activityName;
    private final Drawable icon;

    public AppEntry(Drawable icon, String label, String packageName, String activityName) {
        this.icon = icon;
        this.label = label;
        this.packageName = packageName;
        this.activityName = activityName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getLabel() {
        return label;
    }
}