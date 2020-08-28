package com.boco.app.core.utils;

import java.math.BigDecimal;

/**
 * Created by void on 2017/12/7.
 */

public class MapUtil {
    /**
     * 计算两点之间真实距离
     * @return km
     */
    public static double getDistance(double lng1_, double lat1_, double log2_, double lat2_) {
        // 维度
        double lat1 = (Math.PI / 180) * lat1_;
        double lat2 = (Math.PI / 180) * lat2_;

        // 经度
        double lon1 = (Math.PI / 180) * lng1_;
        double lon2 = (Math.PI / 180) * log2_;

        // 地球半径
        double R = 6371;

        // 两点间距离 km，如果想要米的话，结果*1000就可以了

        double dis = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
        BigDecimal b   =   new   BigDecimal(dis);
        return b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public static String getDistanceKmOrEmpty(String prefix,Double lng1_, Double lat1_, Double lng2_, Double lat2_){
        if(lng1_==null)
            return "";
        if(lat1_==null)
            return "";
        if(lng2_==null)
            return "";
        if(lat2_==null)
            return "";

        double distance = getDistance(lng1_, lat1_, lng2_, lat2_);
        return prefix+distance+"km";
    }
}
