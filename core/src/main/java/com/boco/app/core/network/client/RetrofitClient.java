package com.boco.app.core.network.client;

import android.content.Context;

import com.boco.app.core.network.Constants;
import com.boco.app.core.network.NullOnEmptyConverterFactory;
import com.boco.app.core.network.cache.CacheConfig;
import com.boco.app.core.network.cache.LocalCacheInterceptor;
import com.boco.app.core.network.cache.LogInterceptor;
import com.boco.app.core.network.cache.NetCacheInterceptor;
import com.boco.app.core.network.cache.ResponseCacheStrategy;
import com.boco.app.core.network.token.TokenInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okio.BufferedSource;
import okio.ByteString;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by walle on 2017/7/4.
 */

public class RetrofitClient {

    private static Retrofit retrofit;

    private RetrofitClient() {
    }

    public void init(Context context,String baseUrl){
        File cacheDir = new File(context.getCacheDir(), "responses");
        Cache cache = new Cache(cacheDir, 1024 * 1024 * 10);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        CacheConfig.Builder cacheBuilder = new CacheConfig.Builder();
        final byte[] bytes = "200".getBytes(); //只缓存code=200的response
        CacheConfig cacheConfig = cacheBuilder.responseCacheStrategy(new ResponseCacheStrategy() {
            @Override
            public boolean strategy(BufferedSource source) throws IOException {
                //判断响应的内容，确定是否缓存该响应
                //只缓存code=200的response
                long indexOf = source.indexOf(ByteString.of(bytes, 0, bytes.length));
                return indexOf < 10 && indexOf > 0;
            }
        }).cacheTime(120, TimeUnit.SECONDS).build();

        builder.addInterceptor(new LocalCacheInterceptor(context, cacheConfig))
                .addNetworkInterceptor(new NetCacheInterceptor(cacheConfig))
                .addInterceptor(new TokenInterceptor())
                .addInterceptor(new LogInterceptor())
                .readTimeout(6, TimeUnit.SECONDS)
                .connectTimeout(6, TimeUnit.SECONDS)
                .cache(cache);

        OkHttpClient okClient = builder.build();
        Retrofit.Builder reBuilder = new Retrofit.Builder();
        reBuilder.addConverterFactory(new NullOnEmptyConverterFactory());
        retrofit = reBuilder.baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Call call = okClient.newCall(null);
        call.cancel();
    }

    public void init(Context context) {
        init(context,Constants.URL);
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            throw new NullPointerException("retrofit is null");
        }
        return retrofit;
    }

    public static RetrofitClient getInstance() {
        return RetrofitClient.Holder.INSTANCE;
    }

    private static class Holder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

}
