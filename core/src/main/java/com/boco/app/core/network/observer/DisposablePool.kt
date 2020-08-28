package com.boco.app.core.network.observer

import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner

import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList

/**
 * 存放Disposable。
 */
object DisposablePool : GenericLifecycleObserver {

    val TAG = "DisposablePool"

    private var cache = LinkedHashMap<String, ArrayList<Disposable>>()


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event != Lifecycle.Event.ON_DESTROY) return
        if (source !is MvpFragment<*, *>) return
        val key = getKey(source)
        val disposables = cache[key]
        disposables?.forEach { if (!it.isDisposed) it.dispose() }
        cache.remove(key)
    }

    /**
     * 添加Disposable：结合com.boco.app.core.network.observer.SimpleObserver使用
     * 会在SimpleObserver中自动添加
     */
    fun addDisposable(fragment: MvpView, disposable: Disposable) {
        val key = getKey(fragment)
        var disposables = cache[key]
        if (disposables == null) {
            disposables = ArrayList()
            cache.put(key, disposables)
        }
        disposables.add(disposable)
    }

    private fun getKey(mvpView: MvpView): String {
        return mvpView.toString()
    }

    fun disposeAll() {
        cache.forEach {
            it.value.forEach { it.dispose() }
            it.value.clear()
        }
        cache.clear()
    }

}