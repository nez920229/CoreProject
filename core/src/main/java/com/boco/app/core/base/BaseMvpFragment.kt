package com.boco.app.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boco.app.core.widget.timeselector.TimeUpdateListener
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by walle on 2017/9/13.
 */
abstract class BaseMvpFragment<V: MvpView,P: MvpPresenter<V>> : MvpFragment<V, P>(),TimeUpdateListener  {
    open val TAG:String=this::javaClass.name



    override fun onStart() {
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnTouchListener({ _, _ -> true })
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflatedView = inflater.inflate(getLayoutRes(), container, false)
        onViewInflated(inflater,inflatedView)
        return inflatedView
    }


    abstract fun getLayoutRes(): Int

    open fun onViewInflated(inflater:LayoutInflater,inflatedView: View){

    }

    override fun onTimeUpdate(time: String, timeType: String) {

    }


}