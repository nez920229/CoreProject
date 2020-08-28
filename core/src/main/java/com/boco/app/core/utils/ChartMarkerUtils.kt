package com.boco.app.core.utils

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.ActionBar
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import com.boco.app.core.R

/**
 * Created by NEZ on 2017/10/11.
 */
class ChartMarkerUtils{
    var text:TextView?=null
    var view:View?=null

    fun initPopUpWindow(context:Context):PopupWindow{
        view= LayoutInflater.from(context).inflate(R.layout.popupwindow_view1,null)
        view!!.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val mPopupWindow = PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)


        mPopupWindow.isOutsideTouchable = true
        mPopupWindow.setBackgroundDrawable(BitmapDrawable())
        mPopupWindow.isFocusable = false
        mPopupWindow.animationStyle = R.style.popwin_anim_style
        mPopupWindow.setOnDismissListener {
        }
        text=view!!.findViewById(R.id.text) as TextView

        return mPopupWindow
    }
}