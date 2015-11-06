package com.example.vkirillov.animatedrecycler.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Sliding items from right
 */
public class SlidingRightItemAnimator extends RecyclerView.ItemAnimator {
    public static final String TRANSLATION_X = "translationX";
    public static final String ALPHA = "alpha";
    private final List<RecyclerView.ViewHolder> mAddAnimations = new ArrayList<>();
    private final int mItemDelay;

    public SlidingRightItemAnimator(int itemDelay) {
        mItemDelay = itemDelay;
    }

    @Override
    public boolean animateDisappearance(RecyclerView.ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateAppearance(RecyclerView.ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        viewHolder.itemView.setAlpha(0.0f);
        mAddAnimations.add(viewHolder);
        return true;
    }

    @Override
    public boolean animatePersistence(RecyclerView.ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public void runPendingAnimations() {
        if(!mAddAnimations.isEmpty()){
            for(final RecyclerView.ViewHolder holder : mAddAnimations){
                View view = holder.itemView;

                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator translationAnim = ObjectAnimator.ofFloat(view, TRANSLATION_X, view.getMeasuredWidth(), 0.0f);
                ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, ALPHA, view.getAlpha(), 1.0f);
                animatorSet.playTogether(translationAnim, alphaAnim);

                animatorSet.setStartDelay(mItemDelay * holder.getLayoutPosition());
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mAddAnimations.remove(holder);
                    }
                });
                animatorSet.start();
            }
        }
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {}

    @Override
    public void endAnimations() {}

    @Override
    public boolean isRunning() {
        return !mAddAnimations.isEmpty();
    }
}
