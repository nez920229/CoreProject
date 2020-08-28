package com.boco.app.core.utils

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * Created by walle on 2017/9/13.
 */
object ActivityUtil {

    fun startActivity(context: Context, clazz: Class<out FragmentActivity>) {
        val intent = Intent(context, clazz)
        context.startActivity(intent)
    }

    fun startActivityInNewTask(context: Context, clazz: Class<out FragmentActivity>) {
        val intent = Intent(context, clazz)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    fun startActivityForResult(fragment: Fragment, activity: Class<out FragmentActivity>, requestCode: Int) {
        val intent = Intent()
        intent.setClass(fragment.activity, activity)
        fragment.startActivityForResult(intent, requestCode)
    }

    private fun getRootFragment(_fragment: Fragment): Fragment {
        var fragment = _fragment
        var parent = fragment.parentFragment
        while (parent != null) {
            fragment = parent
            parent = fragment.parentFragment
        }
        return fragment
    }

    fun startActivity(context: Context,intent: Intent) {
        context.startActivity(intent)
    }
}