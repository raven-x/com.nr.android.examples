package com.nimura.asyncapploader;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * Created by Limi on 26.07.2015.
 */
public class AppEntry {
    private final AppListLoader mLoader;
    private final ApplicationInfo mInfo;
    private final File mApkFile;
    private String mLabel;
    private Drawable mIcon;
    private boolean mMounted;

    /**
     * Constructor
     * @param mLoader
     * @param mInfo
     */
    public AppEntry(AppListLoader mLoader, ApplicationInfo mInfo) {
        this.mLoader = mLoader;
        this.mInfo = mInfo;
        this.mApkFile = new File(mInfo.sourceDir);
    }

    public ApplicationInfo getInfo() {
        return mInfo;
    }

    public String getLabel() {
        return mLabel;
    }

    public Drawable loadIcon(){
        if(mIcon == null){
            if(mApkFile.exists()){
                //mIcon = mInfo.loadIcon(mLoader)
            }
        }
        return null;
    }
}
