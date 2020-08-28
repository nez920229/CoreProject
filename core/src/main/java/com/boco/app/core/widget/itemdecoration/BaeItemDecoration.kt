package com.boco.app.core.widget.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by walle on 2017/9/22.
 */
open class BaeItemDecoration : RecyclerView.ItemDecoration() {

    enum class DecorationStyle{
        SURROUND,//绘制所有的边
        INSIDE//不绘制四周
    }

    private val mDividerHeight = 2//分割线高度，默认为1px
    //获取分割线尺寸
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val spanCount = getSpanCount(parent)
        val childCount = parent.adapter!!.itemCount
        when {
            isLastRaw(parent, itemPosition, spanCount, childCount)
                // 如果是最后一行，则不需要绘制底部
            -> outRect.set(0, 0, mDividerHeight, mDividerHeight)
            isLastColum(parent, itemPosition, spanCount, childCount)
                // 如果是最后一列，则不需要绘制右边
            -> outRect.set(0, 0, 0, mDividerHeight)
            else -> outRect.set(0, 0, mDividerHeight, mDividerHeight)
        }
    }



    open fun isLastColum(parent: RecyclerView, pos: Int, spanCount: Int,
                            childCount: Int): Boolean {
        if(spanCount==1)
            return true
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)
                return true
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)
                    return true
            } else {
                if (pos+1 == childCount)//最后一个
                    return true
            }
        }
        return false
    }

    open fun isLastRaw(parent: RecyclerView, pos: Int, spanCount: Int,
                          childCount: Int): Boolean {
        var raw=1
        if(childCount<=spanCount)
            raw = 1
        else if(childCount%spanCount==0){
            raw = childCount/spanCount
        } else {
            raw = childCount/spanCount+1
        }
        if(spanCount==1&&pos==childCount-1)
            return true
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if(pos>spanCount*(raw-1))
                return true
            var remain  = spanCount-childCount%spanCount

        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                    .orientation
        }
        return false
    }

    open fun getSpanCount(parent: RecyclerView): Int {
        // 列数
        var spanCount = 1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager
                    .spanCount
        }
        return spanCount
    }
}