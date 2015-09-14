package com.nimura.masterdetails;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Limi on 15.06.2015.
 */
public class DetailFragment extends Fragment {
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        textView = (TextView) view.findViewById(R.id.detailsText);
        return view;
    }

    public void setText(String text){
        textView.setText(text);
    }
}
