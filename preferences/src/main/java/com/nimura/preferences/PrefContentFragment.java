package com.nimura.preferences;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Limi on 29.06.2015.
 */
public class PrefContentFragment extends Fragment {
    private TextView loginText;
    private TextView emailText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pref_content, container, false);

        loginText = (TextView) view.findViewById(R.id.loginText);
        emailText = (TextView) view.findViewById(R.id.emailText);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        loginText.setText(prefs.getString("login", "не установлено"));
        emailText.setText(prefs.getString("email", "не установлено"));
    }
}
