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

/**
 * Simple, but totally custom layout which has the following format:
 *    _____
 *   |ima |  Title
 *   |_ge_|  Message
 */
public class CustomViewLayout extends ViewGroup {

    private ImageView mImageView;
    private TextView mTitle;
    private TextView mMessage;

    public CustomViewLayout(Context context) {
        super(context);
        initializeViews(context);
    }

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
        inflater.inflate(R.layout.full_custom_layout, this, true);

        mImageView = (ImageView) findViewById(R.id.photo);
        mTitle = (TextView) findViewById(R.id.title);
        mMessage = (TextView) findViewById(R.id.message);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1. Setup constraints - initial sum of paddings,
        //extra space that has been used by the parent
        int widthConstraint = getPaddingLeft() + getPaddingRight();
        int heightConstraint = getPaddingTop() + getPaddingBottom();
        //Result width and height which will be passed as view dimensions
        int width = 0;
        int height = 0;

        //2. Measure photo, which has constant size
        measureChildWithMargins(mImageView,
                widthMeasureSpec,
                widthConstraint, //extra space that has been used by the parent horizontally
                heightMeasureSpec,
                heightConstraint); //extra space that has been used by the parent vertically

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

        //Must be called
        setMeasuredDimension(
                resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int leftEdge = l + paddingLeft;
        int topEdge = 0;

        if(mImageView.getVisibility() != GONE){
            int leftMargin = 0, rightMargin = 0, topMargin = 0;
            if(mImageView.getLayoutParams() instanceof MarginLayoutParams){
                MarginLayoutParams lp = (MarginLayoutParams) mImageView.getLayoutParams();
                leftMargin = lp.leftMargin;
                topMargin = lp.topMargin;
                rightMargin = lp.rightMargin;
            }
            int left = l + paddingLeft + leftMargin;
            int top = t + paddingTop + topMargin;
            int right = left + mImageView.getMeasuredWidth();
            int bottom = top + mImageView.getMeasuredHeight();
            mImageView.layout(left, top, right, bottom);
            leftEdge = right + rightMargin;
        }

        if(mTitle.getVisibility() != GONE){
            int leftMargin = 0, topMargin = 0;
            if(mImageView.getLayoutParams() instanceof MarginLayoutParams){
                MarginLayoutParams lp = (MarginLayoutParams) mImageView.getLayoutParams();
                leftMargin = lp.leftMargin;
                topMargin = lp.topMargin;
            }
            int left = leftEdge + leftMargin;
            int top = t + paddingTop + topMargin;
            int right = left + mTitle.getMeasuredWidth();
            int bottom = top + mTitle.getMeasuredHeight();
            topEdge = bottom;
            mTitle.layout(left, top ,right, bottom);
        }

        if(mMessage.getVisibility() != GONE){
            int leftMargin = 0, topMargin = 0;
            if(mImageView.getLayoutParams() instanceof MarginLayoutParams){
                MarginLayoutParams lp = (MarginLayoutParams) mImageView.getLayoutParams();
                leftMargin = lp.leftMargin;
                topMargin = lp.topMargin;
            }
            int left = leftEdge + leftMargin;
            int top = topEdge + topMargin;
            int right = left + mMessage.getMeasuredWidth();
            int bottom = top + mMessage.getMeasuredHeight();
            mMessage.layout(left, top, right, bottom);
        }
    }

    @Override
    protected void measureChildWithMargins(
            View child,
            int parentWidthMeasureSpec,
            int widthUsed,
            int parentHeightMeasureSpec,
            int heightUsed) {

        int leftMargin = 0, rightMargin = 0, topMargin = 0, bottomMargin = 0;

        if(child.getLayoutParams() instanceof MarginLayoutParams){
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            leftMargin = lp.leftMargin;
            rightMargin = lp.rightMargin;
            topMargin = lp.topMargin;
            bottomMargin = lp.bottomMargin;
        }

        LayoutParams lp = child.getLayoutParams();
        int childWidthMeasureSpec = getChildMeasureSpec(
                parentWidthMeasureSpec,
                widthUsed + leftMargin + rightMargin,
                lp.width);

        int childHeightMeasureSpec = getChildMeasureSpec(
                parentHeightMeasureSpec,
                heightUsed + topMargin + bottomMargin,
                lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }
}
