package com.boco.app.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.widget.NestedScrollView
import com.boco.app.core.R
import com.boco.app.core.utils.PXUtil

/**
 * Created by void on 2017/11/9.
 */
class ScrollViewWidthBlankHeader(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
    : NestedScrollView(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    companion object {
        //是否显示背景
        val showBG = false
    }
    private var showBg = false
    var padding = 0

    init {
        if (showBg) {
            post({
                var param = layoutParams as RelativeLayout.LayoutParams
                var marg = PXUtil.getPx(context, resources.getDimension(R.dimen.title_bar_height)).toInt()
                param.topMargin = marg
                layoutParams = param
                val childAt = getChildAt(0) as LinearLayout
                padding = childAt.paddingTop
            })
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (showBg) {
            if (ev.y < padding) return false
        }
        return super.onTouchEvent(ev)
    }
}