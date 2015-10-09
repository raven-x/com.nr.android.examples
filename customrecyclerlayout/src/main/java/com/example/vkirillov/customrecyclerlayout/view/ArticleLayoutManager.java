package com.example.vkirillov.customrecyclerlayout.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vkirillov on 09.10.2015.
 */
public class ArticleLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //Obtain view from given position
        View view = recycler.getViewForPosition(0);
        //Add child view
        addView(view);
        //Measure view
        measureChildWithMargins(view, 0, 0);
        //Lay out the children view
        layoutDecorated(view, 0, 0, getWidth(), getHeight());
    }

    private void measureViewWithDecorationAndMargins(View child, int widthSpec, int heightSpec){
        Rect decorRect = new Rect();
        /*
         * Calculates the item decor insets applied to the given child and updates the provided
         * Rect instance with the inset values.
         *     The Rect's left is set to the total width of left decorations.
         *     The Rect's top is set to the total height of top decorations.
         *     The Rect's right is set to the total width of right decorations.
         *     The Rect's bottom is set to total height of bottom decorations.
         */
        calculateItemDecorationsForChild(child, decorRect);
        /**
         * Get view layout params
         */
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();

    }
}
