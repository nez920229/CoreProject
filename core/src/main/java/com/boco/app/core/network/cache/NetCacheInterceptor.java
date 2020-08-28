package com.boco.app.core.network.cache;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSource;
import okio.Okio;


/**
 * walle
 * don't modify
 * 默认缓存 max-age=90s
 */
final public class NetCacheInterceptor implements Interceptor {
    private final CacheConfig cacheConfig;
    private ResponseCacheStrategy strategy;

    public NetCacheInterceptor(CacheConfig config) {
        this.strategy = config.getStrategy();
        this.cacheConfig = config;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();

        Response response = chain.proceed(request);
        if (strategy == null) {
            return response;
        }
        // 由ResponseCacheStrategy判断是否缓存 Response
        BufferedSource source = response.body().source();
        if (strategy.strategy(source)) {
            CacheControl.Builder builder = new CacheControl.Builder();
            builder.maxAge(cacheConfig.getCacheSeconds(), cacheConfig.getTimeUnit());
            return response.newBuilder()
                    .header("Cache-Control", builder.build().toString())
                    .body(new RealResponseBody(response.headers(), Okio.buffer(source)))
                    .build();
        } else {
            CacheControl.Builder builder = new CacheControl.Builder();
            return response.newBuilder()
                    .addHeader("Cache-Control", builder.noStore().build().toString())
                    .body(new RealResponseBody(response.headers(), Okio.buffer(source)))
                    .build();
        }

    }
}