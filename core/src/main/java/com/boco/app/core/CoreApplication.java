package com.boco.app.core;

import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.boco.app.core.network.NetBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walle on 2017/10/8.
 */

public class CoreApplication extends Application {
    private static Application app;
    List<Activity> acticities = new ArrayList<>();




    public CoreApplication(Application application) {
        app = application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        app.registerReceiver(new NetBroadcastReceiver(),intentFilter);
//        RetrofitClient.getInstance().init(app);
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                acticities.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                acticities.remove(activity);
            }
        });
    }
    public static Application getApp(){
        return app;
    }
    public void  finishAllActivity(){
        for (Activity activity: acticities){
            activity.finish();
        }
    }
}
