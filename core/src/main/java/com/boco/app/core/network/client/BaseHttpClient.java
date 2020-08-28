package com.boco.app.core.network.client;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by walle on 2017/7/4.
 */

public class BaseHttpClient {
    protected Retrofit retrofit;

    public BaseHttpClient(){
        retrofit =RetrofitClient.getInstance().getRetrofit();
    }
    protected <T> T createService(final Class<T> service){
        return retrofit.create(service);
    }
    protected  Observable setScheduler(Observable observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
