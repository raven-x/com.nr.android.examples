package com.example.vkirillov.propertyanimation;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by vkirillov on 08.10.2015.
 */
public class MyPointEvaluator implements TypeEvaluator<PointF> {

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return new PointF(
                startValue.x + fraction * (endValue.x - startValue.x),
                startValue.y + fraction * (endValue.y - startValue.y)
        );
    }
}
