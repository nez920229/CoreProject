package com.boco.app.core.widget.wcviewpager;

/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015 Raanan Nevet
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class WrapContentViewPager extends ViewPager {

    private static final String TAG = WrapContentViewPager.class.getSimpleName();
    private int height = 0;
    private int widthMeasuredSpec;

    private boolean isSlide = false;

    public void setSlide(boolean slide) {
        this.isSlide = slide;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSlide;
    }

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


            public int state;

            @Override
            public void onPageScrolled(int position, float offset, int positionOffsetPixels) {
                height = 0;
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected:" + position+"---state="+state);
                if (state == SCROLL_STATE_SETTLING) {
                    height = 0; // measure the selected page in-case it's a change without scrolling
                    requestLayout();
                    invalidate();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                this.state = state;
            }
        });
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (!(adapter instanceof ObjectAtPositionInterface)) {
            throw new IllegalArgumentException("WrapContentViewPage requires that PagerAdapter will implement ObjectAtPositionInterface");
        }
        height = 0; // so we measure the new content in onMeasure
        super.setAdapter(adapter);
    }

    /**
     * Allows to redraw the view size to wrap the content of the bigger child.
     *
     * @param widthMeasureSpec  with measured
     * @param heightMeasureSpec height measured
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        widthMeasuredSpec = widthMeasureSpec;
        int mode = MeasureSpec.getMode(heightMeasureSpec);

        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {


            // make sure that we have an height (not sure if this is necessary because it seems that onPageScrolled is called right after
            int position = getCurrentItem();
            View child = getViewAtPosition(position);
            if (child != null) {
                height = measureViewHeight(child);
            }
            Log.d(TAG, "onMeasure height:" + height + " decor:");


            int totalHeight = height + getPaddingBottom() + getPaddingTop();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
            Log.d(TAG, "onMeasure total height:" + totalHeight);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private int measureViewHeight(View view) {
        view.measure(getChildMeasureSpec(widthMeasuredSpec, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        return view.getMeasuredHeight();
    }

    protected View getViewAtPosition(int position) {
        if (getAdapter() != null) {
            Object objectAtPosition = ((ObjectAtPositionInterface) getAdapter()).getObjectAtPosition(position);
            if (objectAtPosition != null) {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    if (child != null && getAdapter().isViewFromObject(child, objectAtPosition)) {
                        return child;
                    }
                }
            }
        }
        return null;
    }


}