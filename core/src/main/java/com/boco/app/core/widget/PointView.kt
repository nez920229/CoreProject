package com.boco.app.core.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.boco.app.core.R

/**
 * Created by NEZ on 2017/10/27.
 */
class PointView(context: Context, attrs: AttributeSet?, defStyle: Int)
    : View(context, attrs, defStyle) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var mRoundPaint: Paint
    private var mPointPaint: Paint
    private var mRoundRadius: Float
    private var mPointRadius: Float
    private var mRoundColor: Int
    private var mPointColor: Int

    init {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PointsView)
        mRoundPaint = Paint()
        mRoundPaint.isAntiAlias = true
        mRoundRadius = attributes.getDimension(R.styleable.PointsView_roundRadius, 10F)
        mRoundColor = attributes.getColor(R.styleable.PointsView_roundColor, Color.RED)
        mRoundPaint.color = mRoundColor
        mRoundPaint.style = Paint.Style.FILL


        mPointPaint = Paint()

        mPointPaint.isAntiAlias = true
        mPointRadius = attributes.getDimension(R.styleable.PointsView_pointRadius, 7F)
        mPointColor = attributes.getColor(R.styleable.PointsView_pointColor, Color.WHITE)
        mPointPaint.color = mPointColor
        mPointPaint.style = Paint.Style.FILL

        attributes.recycle()
    }

    private var centerX: Float = 0F
    private var centerY: Float = 0F
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w.toFloat().div(2)
        centerY = h.toFloat().div(2)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(centerX, centerY, mRoundRadius, mRoundPaint)
        canvas?.drawCircle(centerX, centerY, mPointRadius, mPointPaint)
    }

    fun setRoundColor(color:Int){
        mRoundPaint.color=color

    }
}