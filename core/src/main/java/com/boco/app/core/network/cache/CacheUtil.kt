package com.boco.app.core.network.cache

import java.util.*

/**
 * Created by walle on 2017/7/3.
 */
@Deprecated("使用<code>com.boco.app.core.network.cache.Control</code>")
class CacheUtil {
    companion object {

        /**
         * 向服务器重新请求
         * no-cache：用在request时表示不使用缓存
         *           用在response时表示在使用缓存前需要与服务器确认
         */
        fun noCache(): HashMap<String, String> = hashMapOf(Pair("Cache-Control", "no-cache"))

        /**
         * 取max-age<=60的缓存
         */
        fun fromCache(): HashMap<String, String> = hashMapOf(Pair("Cache-Control", "max-age=60"))

        /**
         * 只读取缓存，不使用网络
         * only-if-cached：表明客户端只接受已缓存的响应，并且不要向原始服务器检查是否有更新的拷贝
         */
        fun onlyCache(): HashMap<String, String> = hashMapOf(Pair("Cache-Control", "only-if-cached"))

        /**
         * 允许读取maxAge秒内的缓存
         */
        fun fromCache(maxAge: Int): HashMap<String, String> = hashMapOf(Pair("Cache-Control", "max-age=" + maxAge))

        /**
         * 允许读取过期时间必须小于maxStale值的缓存
         */
        fun staleCache(maxStale: Int): HashMap<String, String> = hashMapOf(Pair("Cache-Control", "max-stale=" + maxStale))

        /**
         * no-store：缓存不应存储有关客户端请求或服务器响应的任何内容。
         * no-cache：用在request时表示不使用缓存
         * 使用此方法时，NetCacheInterceptor会修改 response的Cache-Control=no-store 禁止OkHttp存储Response
         * 优先级高于 ResponseCacheStrategy
         */
        fun noStore() {
            hashMapOf(Pair("Cache-Control", "no-store,no-cache"))
        }

    }
}