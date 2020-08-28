package com.boco.app.core.widget.itemdecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class LinearLayoutDivider extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int mDividerHeight = 2;//分割线高度，默认为1px
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * 默认分割线：高度为2px，颜色为灰色
     * @param context
     */
    public LinearLayoutDivider(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }


    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = ((RecyclerView.LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        if(itemPosition!=(parent.getAdapter().getItemCount()-1)){
            outRect.set(mDividerHeight,mDividerHeight,mDividerHeight,0);
        }else{
            outRect.set(mDividerHeight,mDividerHeight,mDividerHeight,mDividerHeight);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getTop();
            int bottom = top + mDividerHeight;
            int left = child.getLeft()-params.leftMargin;
            int right = child.getRight()+params.rightMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
            if (i == childSize - 1) {
                top = child.getBottom();
                bottom = top + mDividerHeight;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }

        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            int left = child.getLeft();
            int right = left + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
            left = child.getRight();
            right = left + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }
}