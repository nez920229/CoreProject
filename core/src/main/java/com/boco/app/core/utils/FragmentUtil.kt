package com.boco.app.core.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by walle on 2017/9/29.
 */
object FragmentUtil {
    fun add(manager: FragmentManager,viewId:Int,fragment:Fragment){
        manager.beginTransaction().add(viewId,fragment).commit()
    }
    fun addToBackStack(manager: FragmentManager,viewId:Int,fragment:Fragment){
        manager.beginTransaction().addToBackStack(null).add(viewId,fragment).commit()
    }
    fun addToBackStack(manager: FragmentManager,fragment:Fragment,tag:String?){
        manager.beginTransaction().addToBackStack(null).add(fragment,tag).commit()
    }
}