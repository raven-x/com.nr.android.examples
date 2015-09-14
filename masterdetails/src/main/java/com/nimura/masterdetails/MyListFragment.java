package com.nimura.masterdetails;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Limi on 15.06.2015.
 */
public class MyListFragment extends Fragment {

    private IFragmentInteractionListener fragmentInteractionListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mylist, container, false);

        Button button = (Button)view.findViewById(R.id.updateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetail();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            fragmentInteractionListener = (IFragmentInteractionListener) activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateDetail(){
        String newTime = String.valueOf(System.currentTimeMillis());
        fragmentInteractionListener.onFragmentInteraction(newTime);
    }
}
