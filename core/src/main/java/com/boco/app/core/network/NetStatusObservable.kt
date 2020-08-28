package com.boco.app.core.network

import java.util.*

/**
 * Created by void on 2018/1/9.
 */
object NetStatusObservable : Observable() {
    /**
     * 每次网络变化都会notify
     */
    override fun hasChanged() = true
}