package com.boco.app.core.network.token;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by walle on 2017/9/11.
 */

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(TokenHolder.TOKEN!=null && !"".equals(TokenHolder.TOKEN)){
//            Log.e("role","token="+TokenHolder.TOKEN);
            Request authorised = request.newBuilder()
                    .header("auth_token", TokenHolder.TOKEN)
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .build();
            return chain.proceed(authorised);
        }
        return chain.proceed(request);
    }
}
