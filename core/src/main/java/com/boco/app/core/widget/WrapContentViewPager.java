package com.boco.app.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by _SOLID
 * Date:2016/10/12
 * Time:18:00
 * Desc:copy from http://stackoverflow.com/questions/34484833/viewpager-wrap-content-not-working* Desc:copy from http://stackoverflow.com/questions/34484833/viewpager-wrap-content-not-working
 */

public class WrapContentViewPager extends ViewPager {

    private int mCurrentPagePosition = 0;

    public WrapContentViewPager(Context context) {
        super(context);
        init();
    }

    public WrapContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float offset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("onPageSelected", "onPageSelected=" + position);
                reMeasureCurrentPage(getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("onMeasure", "onMeasure mCurrentPagePosition=" + mCurrentPagePosition);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        View child = getChildAt(mCurrentPagePosition);
        child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int h = child.getMeasuredHeight();
        if (h > height) height = h;

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return i;
    }

    public void reMeasureCurrentPage(int position) {
        mCurrentPagePosition = position;
        requestLayout();
    }
}