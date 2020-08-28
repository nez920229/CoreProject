package com.boco.app.core.widget.itemdecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class LinearLayoutInnerDivider extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int mDividerHeight = 1;//分割线高度，默认为1px
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private int orientation;
    /**
     * 默认分割线：高度为1px，颜色为灰色
     * @param context
     */
    public LinearLayoutInnerDivider(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }


    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0,0,0,mDividerHeight);
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize-1; i++) {

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int left = child.getLeft()-params.leftMargin;
            int right = child.getRight()+params.rightMargin;
            int top = child.getBottom();
            int bottom = top + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);


        }
    }

}