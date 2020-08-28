package com.boco.app.core.widget.timeselector

import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import com.boco.app.core.R
import com.boco.app.core.widget.dialog.BaseBottomDialogFragment
import kotlinx.android.synthetic.main.time_selector.*
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Created by walle on 2017/10/16.
 */
class TimeSelectorFragment : BaseBottomDialogFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        v.isSelected = true
        when (v.id) {
            R.id.yearBtn -> {
                monthBtn.isSelected = false
                dayBtn.isSelected = false
                monthPanel.visibility = View.GONE
                dayPanel.visibility = View.GONE
                changeTimeType(1)
            }
            R.id.monthBtn -> {
                yearBtn.isSelected = false
                dayBtn.isSelected = false
                monthPanel.visibility = View.VISIBLE
                dayPanel.visibility = View.GONE
                monthPicker.value=Calendar.getInstance().get(Calendar.MONTH)+1
                Log.e("selector","month=$month")
                changeTimeType(2)
            }
            R.id.dayBtn -> {
                yearBtn.isSelected = false
                monthBtn.isSelected = false
                monthPanel.visibility = View.VISIBLE
                dayPanel.visibility = View.VISIBLE
                monthPicker.value=Calendar.getInstance().get(Calendar.MONTH)+1
                dayPicker.value=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                changeTimeType(3)
            }
            else -> {
            }
        }
    }

    private var typeChanged = false

    private var tempType: Int=-1

    private fun changeTimeType(type: Int) {
        tempType = type
    }

    override fun getLayoutId(): Int = R.layout.time_selector
    private var onTimeSelectedListener: OnTimeSelecedListner? = null

    companion object {
        private val YEAR: String = "year"
        private val MONTH: String = "month"
        private val DAY: String = "day"
        private val TYPE: String = "type" //1 2 3 --> 年 月 日
        /**
         * 年月日选择器
         */
        fun newInstances(year: Int, month: Int, day: Int): TimeSelectorFragment {
            var fragment = TimeSelectorFragment()
            val arguments = Bundle()
            arguments.putInt(YEAR, year)
            arguments.putInt(MONTH, month)
            arguments.putInt(DAY, day)
            arguments.putInt(TYPE, 3)
            fragment.arguments = arguments
            return fragment
        }

        /**
         * 年月选择器
         */
        fun newInstances(year: Int, month: Int): TimeSelectorFragment {
            var fragment = TimeSelectorFragment()
            val arguments = Bundle()
            arguments.putInt(YEAR, year)
            arguments.putInt(MONTH, month)
            arguments.putInt(DAY, 1)
            arguments.putInt(TYPE, 2)
            fragment.arguments = arguments
            return fragment
        }

        /**
         * 年选择器
         */
        fun newInstances(year: Int): TimeSelectorFragment {
            var fragment = TimeSelectorFragment()
            val arguments = Bundle()
            arguments.putInt(YEAR, year)
            arguments.putInt(MONTH, 1)
            arguments.putInt(DAY, 1)
            arguments.putInt(TYPE, 1)
            fragment.arguments = arguments
            return fragment
        }
    }

    fun setOnTimeSelectedListener(listener: OnTimeSelecedListner): TimeSelectorFragment {
        onTimeSelectedListener = listener
        return this
    }

    private var year: Int = -1
    private var month: Int = -1
    private var day: Int = -1
    private var type: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (year == -1)
            year = arguments?.getInt(YEAR, Calendar.getInstance().get(Calendar.YEAR))!!
        if (month == -1)
            month = arguments?.getInt(MONTH, 1)!!
        if (day == -1)
            day = arguments?.getInt(DAY, Calendar.getInstance().get(Calendar.DAY_OF_MONTH))!!
        if (type == -1)
            type = arguments?.getInt(TYPE, 1)!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBtn()

        yearPicker.minValue = 1983
        yearPicker.maxValue = Calendar.getInstance().get(Calendar.YEAR)
        yearPicker.wrapSelectorWheel = false
        yearPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        yearPicker.value = year
        changeTimeType(type)
        if (type == 2) {
            monthPanel.visibility = View.VISIBLE
        }
        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = month
        monthPicker.wrapSelectorWheel = true
        monthPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        monthPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            if (newVal == 12 && oldVal == 1) {
                yearPicker.value = yearPicker.value - 1
            } else if (newVal == 1 && oldVal == 12) {
                yearPicker.value = yearPicker.value + 1
            }
            if (dayPanel.visibility == View.VISIBLE) {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, yearPicker.value)
                calendar.set(Calendar.MONTH, newVal - 1)
                dayPicker.displayedValues = null
                dayPicker.minValue = 1
                dayPicker.maxValue = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            }
        }

        if (type == 3) {
            monthPanel.visibility = View.VISIBLE
            dayPanel.visibility = View.VISIBLE
        }
        dayPicker.minValue = 1
        dayPicker.maxValue = 30
        dayPicker.value = day
        dayPicker.wrapSelectorWheel = true
        dayPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        dayPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            if (newVal == picker.maxValue && oldVal == picker.minValue) {
                monthPicker.value = monthPicker.value - 1
                if (monthPicker.value == 12) {
                    yearPicker.value -= 1
                }
            } else if (newVal == picker.minValue && oldVal == picker.maxValue) {
                monthPicker.value = monthPicker.value + 1
                if (monthPicker.value == 1)
                    yearPicker.value += 1
            }
        }

        cancel.setOnClickListener { dismiss() }
        confirm.setOnClickListener {
            dismiss()
            if (timeChanged())
                onTimeSelectedListener?.onTimeSelected(type, yearPicker.value, monthPicker.value, dayPicker.value)
        }

        setNumberPickerDividerColor(yearPicker)
        setNumberPickerDividerColor(monthPicker)
        setNumberPickerDividerColor(dayPicker)
    }

    /**
     *  判断时间是否发生了改变
     */
    private fun timeChanged(): Boolean {
        if (tempType!=type) {
            type = tempType
            year = yearPicker.value
            month = monthPicker.value
            day = dayPicker.value
            return true
        }
        val timeChanged = !when (type) {
            1 -> (year == yearPicker.value)
            2 -> (year == yearPicker.value) && (month == monthPicker.value)
            3 -> (year == yearPicker.value) && (month == monthPicker.value) && (day == dayPicker.value)
            else -> true
        }
        year = yearPicker.value
        month = monthPicker.value
        day = dayPicker.value
        return timeChanged
    }

    private fun initBtn() {
        yearBtn.setOnClickListener(this)
        monthBtn.setOnClickListener(this)
        dayBtn.setOnClickListener(this)
        when (type) {
            1 -> {
                yearBtn.performClick()
            }
            2 -> {
                monthBtn.performClick()
            }
            3 -> {
                dayBtn.performClick()
            }
            else -> {
            }
        }
    }

    interface OnTimeSelecedListner {
        /**
         * type:1 2 3 --> 年 月 日
         */
        fun onTimeSelected(type: Int, year: Int, month: Int, day: Int)
    }

    private fun setNumberPickerDividerColor(numberPicker: NumberPicker) {
        val picker = numberPicker
        val pickerFields = NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDivider") {
                pf.isAccessible = true
                try {
                    //设置分割线的颜色值
                    pf.set(picker, ColorDrawable(this.resources.getColor(R.color.title_back_blue)))
                    // pf.set(picker, new Div)
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: Resources.NotFoundException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }

        }
    }

}