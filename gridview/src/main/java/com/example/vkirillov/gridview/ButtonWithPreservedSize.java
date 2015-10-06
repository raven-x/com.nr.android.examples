package com.example.vkirillov.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by vkirillov on 02.10.2015.
 */
public class ButtonWithPreservedSize extends Button {

    public ButtonWithPreservedSize(Context context) {
        super(context);
    }

    public ButtonWithPreservedSize(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonWithPreservedSize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, w, oldw, oldh);
    }
}
