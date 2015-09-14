package com.nimura.androidnewtabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Fragment to be displayed as a tab
 */
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private static final String LOGID = "newtab";

    private int mPage;

    public static PageFragment newInstance(int page){
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.myfragment, container, false);

        TextView mTextView = (TextView) view.findViewById(R.id.text);
        mTextView.setText("Fragment #" + mPage);

        Button mButtonShowSnack = (Button) view.findViewById(R.id.btn_show_snack);
        mButtonShowSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar
                        .make(view, "Hello", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(LOGID, "OK pressed");
                            }
                        })
                        .show();
            }
        });

        final TextInputLayout til = (TextInputLayout) view.findViewById(R.id.edit_text_email_layout);
        til.setErrorEnabled(true);

        EditText editText = (EditText) view.findViewById(R.id.edit_text_email);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til.setError(null);
                if(s.length() == 0){
                    til.setError("Email field cannot be empty");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
}
