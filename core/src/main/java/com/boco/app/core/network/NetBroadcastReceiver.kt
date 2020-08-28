package com.boco.app.core.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by void on 2018/1/9.
 */
class NetBroadcastReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent?) {
        val networkUp = NetworkUtils.isNetworkUp(context)
        Log.i("NetBroadcastReceiver","网络状态发生改变："+networkUp)
        NetStatusObservable.notifyObservers(networkUp)
    }
}