package com.boco.app.core.network.observer

import android.support.annotation.CallSuper
import android.util.Log
import com.boco.app.core.network.BaseRemote
import io.reactivex.disposables.Disposable

/**
 * Created by walle on 2017/5/19.
 */
open class OnErrorIfEmptyObserver<T> : SimpleObserver<BaseRemote<T>>() {
    override fun onSubscribe(d: Disposable) {
        super.onSubscribe(d)
    }
    @CallSuper
    override fun onNext(t: BaseRemote<T>) {
        super.onNext(t)
        if(isEmpty(t)){
            Log.d("isEmpty","isEmpty")
           throw Throwable("content is null")
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
    }

    override fun onComplete() {
        super.onComplete()
    }
    private fun isEmpty( result:BaseRemote<T>):Boolean{
        return true
    }

}