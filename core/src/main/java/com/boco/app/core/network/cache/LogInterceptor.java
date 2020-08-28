package com.boco.app.core.network.cache;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by walle on 2016/12/26.
 */

public class LogInterceptor implements Interceptor {
    public static String TAG="LogInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Logger.getLogger(TAG).info(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        Response response = chain.proceed(request);
        Logger.getLogger("NetCacheInterceptor-").info(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (response.receivedResponseAtMillis() - response.sentRequestAtMillis()) / 1e6d, response.headers()));
        return response;
    }
}
