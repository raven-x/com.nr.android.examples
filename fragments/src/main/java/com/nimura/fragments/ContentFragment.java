package com.nimura.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Limi on 14.06.2015.
 */
public class ContentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        Button updateButton = (Button)view.findViewById(R.id.updateButton);
        final TextView updateBox = (TextView)view.findViewById(R.id.textBox);

        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String newTime = String.valueOf(System.currentTimeMillis());
                updateBox.setText(newTime);
            }

        });

        return view;
    }
}
