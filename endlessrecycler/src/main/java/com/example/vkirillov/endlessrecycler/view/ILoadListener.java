package com.example.vkirillov.endlessrecycler.view;

/**
 * Created by vkirillov on 22.10.2015.
 */
public interface ILoadListener {
    /**
     * Recycler adapter request a new portion of data
     * @param currentPage current page
     */
    void onLoad(int currentPage);
}
