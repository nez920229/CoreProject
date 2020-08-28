package com.boco.app.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class SPUtils {

    private final SharedPreferences sp;

    public SPUtils(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static  SPUtils newInstance(Context context,String name) {
       return new SPUtils(context,name);
    }

      public void putLong(String key,Long value){
          sp.edit().putLong(key,value).commit();
          Log.e("SP","key="+key+"--value="+value+"--"+sp.getLong(key,0));
      }
      public void clear(){
          sp.edit().clear().apply();
      }

    public Map<String,?>getAll(){
        return sp.getAll();
    }
    public SharedPreferences getSp() {
        return sp;
    }
}