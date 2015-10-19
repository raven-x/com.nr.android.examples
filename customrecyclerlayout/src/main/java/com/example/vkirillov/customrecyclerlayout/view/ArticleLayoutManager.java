package com.example.vkirillov.customrecyclerlayout.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vkirillov on 09.10.2015.
 */
public class ArticleLayoutManager extends RecyclerView.LayoutManager {

    private final float VIEW_HEIGHT_PERCENT = 0.75f;

    private final SparseArray<View> viewCache = new SparseArray<>();

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        fill(recycler);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int delta = scrollVerticallyInternal(dy);
        offsetChildrenVertical(-delta);
        fill(recycler);
        return delta;
    }

    private void fill(RecyclerView.Recycler recycler){
        View viewAnchor = getAnchorView();
        viewCache.clear();

        //Place views into cache
        for(int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            int pos = getPosition(view);
            viewCache.append(pos, view);
        }

        //Remove views from layout
        for(int i=0;i<viewCache.size();i++){
            detachView(viewCache.valueAt(i));
        }

        fillUp(viewAnchor, recycler);
        fillDown(viewAnchor, recycler);

        //Recycle detached views
        for(int i=0;i<viewCache.size();i++){
            recycler.recycleView(viewCache.valueAt(i));
        }

        updateViewScale();
    }

    private void fillUp(View anchorView, RecyclerView.Recycler recycler){
        int anchorPos = 0;
        int anchorTop = 0;
        if(anchorView != null){
            anchorPos = getPosition(anchorView);
            anchorTop = getDecoratedTop(anchorView);
        }

        boolean fillUp = true;
        int pos = anchorPos - 1;
        int viewBottom = anchorTop;
        int viewHeight = (int)(getHeight() * VIEW_HEIGHT_PERCENT);
        int widthSpec = View.MeasureSpec.makeMeasureSpec(getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(getHeight(), View.MeasureSpec.EXACTLY);
        while(fillUp && pos >= 0){
            //Get view from cache
            View view = viewCache.get(pos);
            if(view == null){
                //Obtain view from given position
                view = recycler.getViewForPosition(pos);
                //Add child view
                addView(view, 0);
                //Measure view, using decorators and margins
                measureViewWithDecorationAndMargins(view, widthSpec, heightSpec);
                int decoratedMeasureWidth = getDecoratedMeasuredWidth(view);
                //Lay out the children view
                layoutDecorated(view, 0, viewBottom - viewHeight, decoratedMeasureWidth, viewBottom);
            }else {
                //View in the cache - attach it back
                attachView(view);
                viewCache.remove(pos);
            }
            viewBottom = getDecoratedTop(view);
            fillUp = viewBottom > 0;
            pos--;
        }
    }

    private void fillDown(View anchorView, RecyclerView.Recycler recycler) {
        int anchorPos = 0;
        int anchorTop = 0;
        if(anchorView != null){
            anchorPos = getPosition(anchorView);
            anchorTop = getDecoratedTop(anchorView);
        }

        boolean fillDown = true;
        int pos = anchorPos;
        int height = getHeight();
        int viewTop = anchorTop;
        int itemCount = getItemCount();
        int viewHeight = (int)(getHeight() * VIEW_HEIGHT_PERCENT);
        int widthSpec = View.MeasureSpec.makeMeasureSpec(getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(getHeight(), View.MeasureSpec.EXACTLY);

        while(fillDown && pos < itemCount){
            //Get view from cache
            View view = viewCache.get(pos);
            if(view == null){
                view = recycler.getViewForPosition(pos);
                //Add child view
                addView(view);
                //Measure view, using decorators and margins
                measureViewWithDecorationAndMargins(view, widthSpec, heightSpec);
                int decoratedMeasureWidth = getDecoratedMeasuredWidth(view);
                //Lay out the children view
                layoutDecorated(view, 0, viewTop, decoratedMeasureWidth, viewTop + viewHeight);
            }else{
                attachView(view);
                viewCache.remove(pos);
            }

            //Update view top Y coordinate
            viewTop = getDecoratedBottom(view);
            fillDown = viewTop <= height;
            pos++;
        }
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
        widthSpec = updateSpecWithExtra(widthSpec, lp.leftMargin + decorRect.left,
                lp.rightMargin + decorRect.right);
        heightSpec = updateSpecWithExtra(heightSpec, lp.topMargin + decorRect.top,
                lp.bottomMargin + decorRect.bottom);
        child.measure(widthSpec, heightSpec);
    }

    private int updateSpecWithExtra(int spec, int startInset, int endInset){
        if(startInset == 0 && endInset == 0){
            return spec;
        }
        int mode = View.MeasureSpec.getMode(spec);
        if(mode == View.MeasureSpec.AT_MOST || mode == View.MeasureSpec.EXACTLY){
            return View.MeasureSpec.makeMeasureSpec(
                    View.MeasureSpec.getSize(spec) - startInset - endInset, mode);
        }
        return spec;
    }

    /**
     * Returns view that has maximum visible square
     * @return
     */
    private View getAnchorView(){
        int childCount = getChildCount();
        Map<Integer, View> viewsOnScreen = getViewsOnScreen(childCount);

        if(viewsOnScreen.isEmpty()){
            return null;
        }

        return getViewWithMaxSquare(viewsOnScreen);
    }

    private Map<Integer, View> getViewsOnScreen(int childCount) {
        Map<Integer, View> viewsOnScreen = new HashMap<>();
        //Recycler rect
        Rect mainRect = new Rect(0, 0, getWidth(), getHeight());
        for(int i=0;i<childCount;i++){
            View view = getChildAt(i);
            int top = getDecoratedTop(view);
            int left = getDecoratedLeft(view);
            int right = getDecoratedRight(view);
            int bottom = getDecoratedBottom(view);
            Rect viewRect = new Rect(left, top, right, bottom);
            boolean intersect = viewRect.intersect(mainRect);
            /*If view intersects recycler rect it means that
              the current child view is visible
            */
            if(intersect){
                //Calculate rect square and add it to the map
                int square = viewRect.width()*viewRect.height();
                viewsOnScreen.put(square, view);
            }
        }
        return viewsOnScreen;
    }

    private View getViewWithMaxSquare(Map<Integer, View> viewsOnScreen) {
        Integer maxSquare = null;
        for(Integer square : viewsOnScreen.keySet()){
            if(maxSquare == null || square > maxSquare){
                maxSquare = square;
            }
        }
        return viewsOnScreen.get(maxSquare);
    }

    private int scrollVerticallyInternal(int dy){
        int childCount = getChildCount();
        int itemCount = getItemCount();
        if(childCount == 0){
            return 0;
        }

        View topView = getChildAt(0);
        View bottomView = getChildAt(childCount - 1);

        //All views are placed inside the screen
        int viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView);
        if(viewSpan <= getHeight()){
            return 0;
        }

        int delta = 0;
        if(dy < 0){
            View firstView = getChildAt(0);
            int firstAdapterPost = getPosition(firstView);
            if(firstAdapterPost > 0){
                //First view isn't the first view in adapter
                delta = dy;
            }else{
                //First view is the first view in adapter
                int viewTop = getDecoratedTop(firstView);
                delta = Math.max(viewTop, dy);
            }
        }else if (dy > 0){
            View lastView = getChildAt(childCount - 1);
            int lastAdapterPos = getPosition(lastView);
            if(lastAdapterPos < itemCount - 1){
                delta = dy;
            }else{
                int viewBottom = getDecoratedBottom(lastView);
                int parentBottom = getHeight();
                delta = Math.min(viewBottom - parentBottom, dy);
            }
        }

        return delta;
    }

    private void updateViewScale(){
        int childCount = getChildCount();
        int height = getHeight();
        int thresholdPx = (int)(height * 0.66f);
        for(int i=0;i<childCount;i++){
            float scale = 1f;
            View view = getChildAt(i);
            int viewTop = getDecoratedTop(view);
            if(viewTop >= thresholdPx){
                int delta = viewTop - thresholdPx;
                scale = (float)(height - delta)/(float)height;
                scale = Math.max(scale, 0);
            }
            view.setAlpha(scale);
            view.setPivotX(view.getHeight()/2);
            view.setPivotY(view.getHeight() / -2);
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
    }
}
