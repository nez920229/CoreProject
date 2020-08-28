package com.boco.app.core.network.observer

import android.content.Intent
import android.support.annotation.CallSuper
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.boco.app.core.CoreApplication
import com.boco.app.core.network.BaseRemote
import com.hannesdorfmann.mosby3.mvp.MvpView


/**
 * Created by walle on 2017/5/19.
 */
open class SmartObserver<T>(mvpView: MvpView?) : SimpleObserver<BaseRemote<T>>(mvpView) {
    constructor():this(null)
    @Deprecated("onNext(code: Int, content: T)")
    override fun onNext(result: BaseRemote<T>) {
        super.onNext(result)
        dispatch(result)
    }

    private fun dispatch(result: BaseRemote<T>) {
        when {
            result.code  in 300 downTo 199 -> {
                if(result.content==null){
                    onNullContent(result)
                }else{
                    onNext(result.code,result.content)
                }
            }
            else -> onResponseError(result)
        }
    }

    /**
     * code在[200,299],且内容不为null
     */
     open fun onNext(code: Int, content: T) {
        Log.i(TAG,"onNext result=$code--content=$content")
    }

    /**
     * code在[200,299],内容为空
     */
     open fun onNullContent(result: BaseRemote<T>) {
        Log.i(TAG,"onNullContent result=$result")
    }

    @CallSuper
    open fun onResponseError(result: BaseRemote<T>) {
        Log.i(TAG,"onResponseError result=$result")
        if(result.code==401) {
            val intent = Intent("UnauthorizedReceiver")
            LocalBroadcastManager.getInstance(CoreApplication.getApp()).sendBroadcast(intent)
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
    }
}