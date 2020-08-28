package com.boco.app.core

import android.view.View

/**
 * Created by walle on 2017/10/7.
 */
interface OnItemClickListener<T> {
    fun onItemClick(obj:T,postion:Int,view: View)
}