package com.nimura.androidnewtabs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * This class controls tabs creation, their titles and associated content
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int mPageCount;
    private Context mContext;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context, int pageCount) {
        super(fm);
        this.mContext = context;
        this.mPageCount = pageCount;
    }

    @Override
    public int getCount() {
        return mPageCount;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page #" + position;
    }

    /**
     * In case we need a custom tab icon layout
     * just add a tab icon creation method for convenience
     * @param position
     * @return
     */
    public View getTabView(int position){
        View v = LayoutInflater.from(mContext).inflate(R.layout.tab_indicator, null);

        TextView tv = (TextView) v.findViewById(R.id.tt);
        tv.setText("TAB ##" + position);

        return v;
    }
}
