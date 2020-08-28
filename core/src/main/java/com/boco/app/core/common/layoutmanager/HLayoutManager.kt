package com.boco.app.core.common.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

/**
 * 横向等宽的LayoutManager
 * Created by walle on 2017/10/12.
 */
class HLayoutManager(context: Context,spanCount:Int): GridLayoutManager(context,spanCount,GridLayoutManager.VERTICAL,false) {
    init {

    }
}