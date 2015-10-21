package com.example.vkirillov.customlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Based on
 * https://github.com/TheHiddenDuck/draggable-panel-layout
 */
public class DraggablePanelLayout extends FrameLayout {
    private static final float PARALLAX_FACTOR = 0.2f;
    private float mParallaxFactor;
    private View mBottomPanel;
    private View mSlidingPanel;
    private int mBottomPanelPeekHeight = 64;
    private boolean mTouching;
    private float mTouchY;
    /**true, if sliding panel is mOpened*/
    private boolean mOpened;
    private VelocityTracker mVelocityTracker;

    public DraggablePanelLayout(Context context) {
        super(context);
        init();
    }

    public DraggablePanelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DraggablePanelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DraggablePanelLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mParallaxFactor = PARALLAX_FACTOR;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(getChildCount() != 2){
            throw new IllegalStateException("DraggedPanelLayout must have 2 children !!!");
        }

        mBottomPanel = getChildAt(0);
        mSlidingPanel = getChildAt(1);

        mBottomPanel.layout(left, top, right, bottom - mBottomPanelPeekHeight);

        if(!mOpened) {
            int panelMeasuredHeight = mSlidingPanel.getMeasuredHeight();
            mSlidingPanel.layout(left, bottom - mBottomPanelPeekHeight, right,
                    bottom - mBottomPanelPeekHeight + panelMeasuredHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                onActionDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onActionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                onActionUp(event);
                break;
        }
        return true;
    }

    private void onActionDown(MotionEvent event){
        getVelocityTracker();
        mVelocityTracker.addMovement(event);
        mTouchY = event.getY();
        mTouching = true;

        mBottomPanel.setVisibility(VISIBLE);
    }

    private void onActionMove(MotionEvent event) {
        if(mTouching){
            mVelocityTracker.addMovement(event);

            //Calculate translation and bound it
            float translation = event.getY() - mTouchY;
            translation = boundTranslation(translation);

            //Translate and parallax sliding panel
            mSlidingPanel.setTranslationY(translation);
            Log.d("dpl", "sliding panel translation: " + translation);

            //Translate and parallax bottom panel
            float bottomPanelTranslation;
            if(mOpened){
                bottomPanelTranslation = -(getMeasuredHeight() - mBottomPanelPeekHeight - translation) * mParallaxFactor;
            }else{
                bottomPanelTranslation = translation * mParallaxFactor;
            }
            mBottomPanel.setTranslationY(bottomPanelTranslation);
            Log.d("dpl", "bottom panel translation: " + bottomPanelTranslation);
        }
    }

    private void onActionUp(MotionEvent event) {
        mTouching = false;

        //Calculate gesture velocity to animate scrolling properly
        mVelocityTracker.addMovement(event);
        mVelocityTracker.computeCurrentVelocity(1);
        float velocityY = mVelocityTracker.getYVelocity();
        mVelocityTracker.recycle();
        mVelocityTracker = null;

        //Animate scrolling
        finishAnimateToFinalPosition(velocityY);
    }

    private void finishAnimateToFinalPosition(float velocityY){
        final boolean flinging = Math.abs(velocityY) > 0.5f;
        //User action - opening or closing the panel
        boolean opening;
        //Animation distance
        float distY;
        //Animation duration
        long duration;

        if(flinging){
            // Velocity is fast enough
            opening = velocityY < 0;
            distY = calculateDistance(opening);
            duration = Math.abs(Math.round(distY/velocityY));
        }else{
            // If user motion is slow or stopped we should check if half distance is
            // reached and based on that complete the motion
            boolean halfway = Math.abs(mSlidingPanel.getTranslationY()) >=
                    (getMeasuredHeight() - mBottomPanelPeekHeight)/2;
            opening = mOpened ? !halfway : halfway;
            distY = calculateDistance(opening);
            duration = Math.round(300.0f * Math.abs((double) mSlidingPanel.getTranslationY())
                    / (double) (getMeasuredHeight() - mBottomPanelPeekHeight));
        }
        animate(opening, distY, duration);
    }

    private float calculateDistance(boolean opening){
        //Was already mOpened
        if(mOpened){
            if(opening){
                return -mSlidingPanel.getTranslationY();
            }else {
                return getMeasuredHeight() - mBottomPanelPeekHeight - mSlidingPanel.getTranslationY();
            }
        }else{
            if(opening){
                return -(getMeasuredHeight() - mBottomPanelPeekHeight + mSlidingPanel.getTranslationY());
            }else {
                return -mSlidingPanel.getTranslationY();
            }
        }
    }

    private float boundTranslation(float translation){
        if(mOpened){
            //If sliding panel is mOpened, we can only scroll downward
            if(translation < 0){
                translation = 0;
            }
            //Limit translation with different between sliding panel height and bottom
            //panel peek height
            float translationBound = mSlidingPanel.getMeasuredHeight() - mBottomPanelPeekHeight;
            if(translation >=  translationBound){
                translation = translationBound;
            }
        }else{
            //If sliding panel is closed, we can only scroll upward
            if(translation > 0){
                translation = 0;
            }
            //Limit translation with different between sliding panel height and bottom
            //panel peek height
            float translationBound = -mSlidingPanel.getMeasuredHeight() + mBottomPanelPeekHeight;
            if(Math.abs(translation) >= Math.abs(translationBound)) {
                translation = translationBound;
            }
        }
        return translation;
    }

    private void animate(boolean opening, float distY, long duration){
        ObjectAnimator slidingPanelAnimator = ObjectAnimator.ofFloat(
                mSlidingPanel, View.TRANSLATION_Y, mSlidingPanel.getTranslationY(),
                mSlidingPanel.getTranslationY() + distY);
        ObjectAnimator bottomPanelAnimator = ObjectAnimator.ofFloat(
                mBottomPanel, View.TRANSLATION_Y, mBottomPanel.getTranslationY(),
                mBottomPanel.getTranslationY() + distY * mParallaxFactor);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(slidingPanelAnimator, bottomPanelAnimator);
        set.setDuration(duration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AL(opening));
        set.start();
    }

    private void getVelocityTracker(){
        if(mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void setOpenedState(boolean opened){
        this.mOpened = opened;
        mBottomPanel.setVisibility(opened ? GONE : VISIBLE);
    }

    private class AL extends AnimatorListenerAdapter{

        private boolean opening;

        public AL(boolean opening){
            this.opening = opening;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            mBottomPanel.setVisibility(VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            setOpenedState(opening);

            mBottomPanel.setTranslationY(0);
            mSlidingPanel.setTranslationY(0);

            requestLayout();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
