package com.nimura.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageFragment extends Fragment {
    private static final String TAG = "pageLog";
    private int pageNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        pageNumber = getArguments() == null ? 1 : getArguments().getInt("num");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_page, container, false);
        TextView pageHeader = (TextView) result.findViewById(R.id.displayText);
        pageHeader.setText(String.format("Фрагмент %d", pageNumber + 1));
        return result;
    }

    public static PageFragment newInstance(int pageNumber){
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("num", pageNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static String getTitle(int position){
        return String.format("Страница № %d", position + 1);
    }
}
