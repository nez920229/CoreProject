package com.boco.app.core.network.cache;

import android.content.Context;

import com.boco.app.core.network.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * walle
 * don't modify
 */
final public class LocalCacheInterceptor implements Interceptor {

    private Context context;
    private CacheConfig cacheConfig;

    public LocalCacheInterceptor(Context context, final CacheConfig cacheConfig) {
        this.context = context;
        this.cacheConfig = cacheConfig;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (cacheConfig.isCacheIfNoNetwork() && !NetworkUtils.isNetworkUp(context)) {
            //网络不可用，读取缓存
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(Integer.MAX_VALUE, TimeUnit.SECONDS)
                    .maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder().cacheControl(cacheControl).build();
        }
        return chain.proceed(request);
    }
}