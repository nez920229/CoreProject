package com.boco.app.core.utils

import android.content.Context

/**
 * Created by walle on 2017/9/12.
 */
class StatusBarUtil {
    companion object {
            fun getStatusBarHeight(context: Context): Int {
                var result = 0
                val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
                if (resId > 0) {
                    result = context.resources.getDimensionPixelOffset(resId)
                }
                return result
            }

    }
}