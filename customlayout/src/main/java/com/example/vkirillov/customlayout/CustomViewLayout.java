package com.example.vkirillov.customlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Custom layout
 */
public class CustomViewLayout extends ViewGroup {

    private ImageView mImageView;
    private TextView mTitle;
    private TextView mMessage;

    public CustomViewLayout(Context context) {
        super(context);
        initializeViews(context);
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public CustomViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public CustomViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomViewLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context);
    }

    private void initializeViews(Context context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_layout, this, true);
        mImageView = (ImageView) findViewById(R.id.photo);
        mTitle = (TextView) findViewById(R.id.title);
        mMessage = (TextView) findViewById(R.id.message);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //TODO
        //1. Setup constraints - initial sum of paddings
        int widthConstraint = getPaddingLeft() + getPaddingRight();
        int heightConstraint = getPaddingTop() + getPaddingBottom();
        //Required view width and height
        int width = 0;
        int height = 0;

        //2. Measure photo, which has constant size
        measureChildWithMargins(mImageView,
                widthMeasureSpec,
                widthConstraint,
                heightMeasureSpec,
                heightConstraint);

        //3. Update the constrains with measured values
        widthConstraint += mImageView.getMeasuredWidth();
        width += mImageView.getMeasuredWidth();
        height = Math.max(height, mImageView.getMeasuredHeight());

        //4. Vertical measure spec
        int verticalWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec) - widthConstraint,
                MeasureSpec.getMode(widthMeasureSpec));
        int verticalHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec) - heightConstraint,
                MeasureSpec.getMode(heightMeasureSpec));

        //Title
        measureChildWithMargins(
                mTitle,
                verticalWidthMeasureSpec,
                0,
                verticalHeightMeasureSpec,
                0);

        //Message
        measureChildWithMargins(
                mMessage,
                verticalWidthMeasureSpec,
                0,
                verticalHeightMeasureSpec,
                0);

        //Update size
        width += Math.max(mTitle.getMeasuredWidth(), mMessage.getMeasuredWidth());
        height = Math.max(mTitle.getMeasuredHeight() + mMessage.getMeasuredHeight(), height);

        setMeasuredDimension(
                resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int leftEdge = l + paddingLeft;

        if(mImageView.getVisibility() != GONE){
            MarginLayoutParams lp = (MarginLayoutParams) mImageView.getLayoutParams();
            int left = l + paddingLeft + lp.leftMargin;
            int top = t + paddingTop + lp.topMargin;
            int right = left + mImageView.getMeasuredWidth();
            int bottom = top + mImageView.getMeasuredHeight();
            mImageView.layout(left, top, right, bottom);
            leftEdge = right + lp.rightMargin;
        }

        if(mTitle.getVisibility() != GONE){
            MarginLayoutParams lp = (MarginLayoutParams) mTitle.getLayoutParams();
            int left = leftEdge + lp.leftMargin;
            int top = t + paddingTop + lp.topMargin;
            int right = left + mTitle.getMeasuredWidth();
            int bottom = top + mTitle.getMeasuredHeight();
            mTitle.layout(left, top ,right, bottom);
        }
    }

    @Override
    protected void measureChildWithMargins(
            View child,
            int parentWidthMeasureSpec,
            int widthUsed,
            int parentHeightMeasureSpec,
            int heightUsed) {

//        if(child.getLayoutParams() instanceof MarginLayoutParams){
//            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
//
//            int childWidthMeasureSpec = getChildMeasureSpec(
//                    parentWidthMeasureSpec,
//                    widthUsed + lp.leftMargin + lp.rightMargin,
//                    lp.width);
//
//            int childHeightMeasureSpec = getChildMeasureSpec(
//                    parentHeightMeasureSpec,
//                    heightUsed + lp.topMargin + lp.bottomMargin,
//                    lp.height);
//
//            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
//        }else{
            LayoutParams lp = child.getLayoutParams();
            int childWidthMeasureSpec = getChildMeasureSpec(
                    parentWidthMeasureSpec,
                    widthUsed,
                    lp.width);

            int childHeightMeasureSpec = getChildMeasureSpec(
                    parentHeightMeasureSpec,
                    heightUsed,
                    lp.height);

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
//        }


    }
}
