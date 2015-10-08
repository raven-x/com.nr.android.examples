package com.example.vkirillov.propertyanimation;

import android.graphics.PointF;
import android.view.View;

/**
 * Created by vkirillov on 08.10.2015.
 */
public class MyAnimatableView {
    private final View mView;
    private PointF mCurrentPoint;

    public MyAnimatableView(View mView) {
        this.mView = mView;
        this.mCurrentPoint = new PointF(mView.getX(), mView.getY());
    }

    public PointF getCurrentPoint() {
        return mCurrentPoint;
    }

    public void setCurrentPoint(PointF currentPoint) {
        mCurrentPoint = currentPoint;
        mView.setX(mCurrentPoint.x);
        mView.setY(mCurrentPoint.y);
    }
}
