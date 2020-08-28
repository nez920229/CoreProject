package com.boco.app.core.utils

import android.content.Context

/**
 * Created by void on 2017/11/9.
 */
object PXUtil {
    /**
     * 返回适配的当前屏幕的PX
     */
    private var widthPixels: Int=0
     fun getPx(context:Context,px: Float): Float {
         if(widthPixels==0){
             widthPixels = context.resources.displayMetrics.widthPixels
         }
//        Log.d(javaClass.simpleName, "px=$px--cu=$widthPixels--res=${widthPixels / 1080F * px}")
        return widthPixels / 1080F * px
    }
}