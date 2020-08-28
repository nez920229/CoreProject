package com.boco.app.core.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.boco.app.core.R


/**
 * Created by walle on 2017/10/17.
 */
class CirclePanelView(context: Context, attrs: AttributeSet?, defStyle: Int)
    : View(context, attrs, defStyle) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var rectPaint: Paint //矩形

    private var textPaint: Paint

    private var numberPaint: Paint

    private var text: String

    private var number: String

    private var numberWidth: Float

    private var textWidth: Float

    private var pointPaint: Paint

    private var lineWidth: Int

    private var pointAngle: Double = 0.0
    private val TOTAL: Int = 300
    private var pointR: Float

    private var lineSpace: Float

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CirclePanelView)
        lineSpace = attributes.getDimension(R.styleable.CirclePanelView_lineSpace,20F)
        rectPaint = Paint()
        rectPaint.isAntiAlias = true
        rectPaint.strokeWidth = attributes.getDimension(R.styleable.CirclePanelView_rectWidth, 8F)
        rectPaint.color = Color.BLUE
        rectPaint.style = Paint.Style.STROKE

        numberPaint = Paint()
        numberPaint.color = Color.rgb(3, 142, 239)
        numberPaint.textSize = attributes.getDimension(R.styleable.CirclePanelView_numberSize, 18F)

        textPaint = Paint()
        textPaint.color = Color.rgb(3, 142, 239)
        textPaint.isAntiAlias = true
        textPaint.textSize = attributes.getDimension(R.styleable.CirclePanelView_textSize, 10F)

        text = ""

        numberWidth = numberPaint.measureText("优")
        textWidth = textPaint.measureText("无")

        pointPaint = Paint()
        pointPaint.color = Color.TRANSPARENT
        pointPaint.style = Paint.Style.FILL
        pointPaint.isAntiAlias = true

        lineWidth = attributes.getDimensionPixelOffset(R.styleable.CirclePanelView_rectLength, 10)

        this.number = ""

        pointR = context.resources.displayMetrics.density * 4
        attributes.recycle()
    }

    var startColor = Color.rgb(3, 131, 249)
    var middleColor = Color.rgb(203, 252, 3)
    var endColor = Color.rgb(252,68,54)


    fun setNumber(_number: Int?) {
        var number = _number
        if (number == null) {
            number = 0
            this.number = ""
            this.text =  "无"
            invalidate()
            return
        }
        this.number = number.toString()
        var percent = number.toDouble().div(TOTAL)
        this.text = when (number) {
            in 0..35 -> "优"
            in 36..75 -> "良"
            in 76..115 -> "轻度污染"
            in 116..150 -> "中度污染"
            in 151..250 -> "重度污染"
            else -> "严重污染"
        }
        numberWidth = numberPaint.measureText(this.number)
        textWidth = textPaint.measureText(text)

        pointAngle = calculateAngle(percent)
        when (pointAngle.toInt()) {
            in 0..150 -> {
                pointPaint.color = getColorRight(pointAngle.toInt())
            }
            in 210..360 -> {
                var offset = pointAngle - 210
                pointPaint.color = getColorLeft(offset.toInt())
            }
            else -> {
            }
        }
        invalidate()
    }


    private var centerX: Float = 0F
    private var centerY: Float = 0F

    /**
     * 外部圆的直径
     */
    private var circleD: Int = 0
    /**
     * 外部圆的半径
     */
    private var circleR: Float = 0F

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w.toFloat().div(2)
        centerY = h.toFloat().div(2)
        circleD = Math.min(w, h)
        circleR = circleD.toFloat().div(2)
    }

    override fun onDraw(canvas: Canvas) {
        drawLines(canvas)
        drawNumber(canvas)
        drawText(canvas)
        drawPoint(canvas)
    }

    private fun drawLines(canvas: Canvas) {
        for (angle in 0..360 step 8) {
            val innerX = calculateX(circleR - lineWidth, angle.toDouble())
            val innerY = calculateY(circleR - lineWidth, angle.toDouble())

            val outX = calculateX(circleR, angle.toDouble())
            val outY = calculateY(circleR, angle.toDouble())
            when (angle) {
                in 0..150 -> {
                    rectPaint.color = getColorRight(angle)
                    canvas.drawLine(innerX, innerY, outX, outY, rectPaint)
                }
                in 210..360 -> {

                    var offset = angle - 210
                    rectPaint.color = getColorLeft(offset)
                    canvas.drawLine(innerX, innerY, outX, outY, rectPaint)
                }
                else -> {
                }
            }
        }
    }

    private fun getColorRight(offset: Int): Int {
//        var startColor = Color.rgb(3,131,249)
//        var middleColor = Color.rgb(203,252,3)
//        var endColor  = Color.rgb(252,68,54)
        val percent = offset.toDouble().div(150)
        Log.d(javaClass.simpleName, "percent=$percent")
        var r = Color.red(middleColor) + percent * (Color.red(endColor) - Color.red(middleColor))
        var g = Color.green(middleColor) - percent * (Color.green(middleColor) - Color.green(endColor))
        var b =Color.blue(middleColor)+  percent * (Color.blue(endColor) - Color.blue(middleColor))
        Log.d(javaClass.simpleName,"color=$r, $g, $b")
        return Color.rgb(r.toInt(), g.toInt(), b.toInt())
    }

    private fun getColorLeft(offset: Int): Int {
        val percent = offset.toDouble().div(150)
        var r = Color.red(startColor) + percent * (Color.red(middleColor) - Color.red(startColor))
        var g =  Color.green(startColor) + percent * (Color.green(middleColor) - Color.green(startColor))
        var b = Color.blue(startColor) - percent * (Color.blue(startColor) - Color.blue(middleColor))
        return Color.rgb(r.toInt(), g.toInt(), b.toInt())
    }


    private fun drawPoint(canvas: Canvas) {
        val x = calculateX(circleR - lineWidth - 25, pointAngle)
        val y = calculateY(circleR - lineWidth - 25, pointAngle)
        canvas.drawCircle(x, y, pointR, pointPaint)
    }


    private fun drawText(canvas: Canvas) {
        canvas.drawText(text, centerX - textWidth / 2, centerY + textPaint.textSize / 2+lineSpace, textPaint)
    }

    private fun drawNumber(canvas: Canvas) {
        canvas.drawText(number, centerX - numberWidth / 2, centerY - numberPaint.textSize / 3, numberPaint)
    }


    /**
     * 根据半径和角度计算x坐标
     */
    private fun calculateX(r: Float, angle: Double): Float {

        return (centerX + r * Math.sin(angle * Math.PI.div(180))).toFloat()

    }

    /**
     * 根据半径和角度计算y坐标
     */
    private fun calculateY(r: Float, angle: Double): Float {
        return (centerY - r * Math.cos(angle * Math.PI.div(180))).toFloat()
    }

    /**
     * 计算圆点的角度
     */
    private fun calculateAngle(percent: Double): Double {
        var angle = percent * 300 + 210
        if (angle > 360)
            angle -= 360
        Log.d(javaClass.simpleName, "angle=$angle")
        return angle
    }
}