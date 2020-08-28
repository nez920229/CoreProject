package com.boco.app.core.network.observer

import android.arch.lifecycle.LifecycleObserver
import android.util.Log
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by walle on 2017/5/18.
 */
open class SimpleObserver<T>(private val mvpView: MvpView?) : Observer<T>, LifecycleObserver {
    //使用带参数的构造函数时，Disposable将在页面退出时自动取消
    //使用无参的构造函数时，Disposable需要自己处理
    constructor():this(null)
    val TAG = "SimpleObserver"

    override fun onSubscribe(d: Disposable) {
        if (mvpView != null)
            //添加Disposable
            DisposablePool.addDisposable(mvpView,d)
        Log.d(TAG, "onSubscribe isDisposed=" + d.isDisposed)
    }

    override fun onError(e: Throwable) {
        Log.d(TAG, "onError=" + e.localizedMessage)
    }

    override fun onComplete() {
        Log.d(TAG, "onComplete")
    }

    override fun onNext(t: T) {
        Log.d(TAG, "onNext=" + t)
    }

}