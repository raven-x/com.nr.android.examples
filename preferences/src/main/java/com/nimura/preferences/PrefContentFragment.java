package com.nimura.preferences;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import java.lang.ref.SoftReference;

/**
 * Created by Limi on 29.06.2015.
 */
public class PrefContentFragment extends Fragment {
    private TextView loginText;
    private TextView emailText;
    private Button btnSetPref;
    private SharedPreferencesThread spt;
    private Handler mUiHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pref_content, container, false);


        loginText = (TextView) view.findViewById(R.id.loginText);
        emailText = (TextView) view.findViewById(R.id.emailText);
        btnSetPref = (Button) view.findViewById(R.id.btnSetPref);
        btnSetPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSetPref.setEnabled(false);
                spt.setFavouriteLioness("Nala");
            }
        });
        mUiHandler = new UiHandler();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        loginText.setText(prefs.getString("login", "не установлено"));
        emailText.setText(prefs.getString("email", "не установлено"));
        //Get value from the list
        Log.d("prefs", prefs.getString("lioness_preference", "Kiara"));
    }

    public void setSharedPreferencesThread(SharedPreferencesThread spt){
        this.spt = spt;
    }

    public Handler getUiHandler() {
        return mUiHandler;
    }

    private final class UiHandler extends Handler{

        public UiHandler(){
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            btnSetPref.setEnabled(true);
        }
    }
}
