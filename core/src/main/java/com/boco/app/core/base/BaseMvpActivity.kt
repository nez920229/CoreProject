package com.boco.app.core.base

import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by walle on 2017/9/13.
 */
abstract class BaseMvpActivity<V: MvpView,P: MvpPresenter<V>>: MvpActivity<V, P>() {

}