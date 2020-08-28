package com.boco.app.core.common.mpchart

import android.graphics.Color
import android.util.Log
import java.util.*

/**
 * Created by void on 2017/11/3.
 */
object ColorHelper {
    private var colors = ArrayList<Int>(13)

    init {
        colors.add(Color.parseColor("#fca864"))
        colors.add(Color.parseColor("#5394f5"))
        colors.add(Color.parseColor("#fd7c54"))
        colors.add(Color.parseColor("#38bffe"))
        colors.add(Color.parseColor("#fe5f53"))
        colors.add(Color.parseColor("#2bdfbb"))
        colors.add(Color.parseColor("#ff8c9a"))
        colors.add(Color.parseColor("#f19ec2"))
        colors.add(Color.parseColor("#a2da65"))
        colors.add(Color.parseColor("#d391cc"))
        colors.add(Color.parseColor("#ddd21b"))
        colors.add(Color.parseColor("#949cfc"))
        colors.add(Color.parseColor("#fed991"))
    }

    fun getColors(): ArrayList<Int> {
//        colors.shuffle()
        Log.e("Color","颜色1")
        return colors
    }

    fun getColors(count: Int): ArrayList<Int> {
//        colors.shuffle()
        Log.e("Color","颜色2")
        var result = ArrayList<Int>()
        if (count > colors.size) {
            result.addAll(colors)
            return result
        }
        colors.subList(0, count).forEach {
            result.add(it)
            System.out.print("$it--")
        }
        System.out.println()
        return result
    }
}