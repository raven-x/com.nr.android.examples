package com.nimura.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Limi on 29.06.2015.
 */
public class MyPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.mypreferences);
    }
}
