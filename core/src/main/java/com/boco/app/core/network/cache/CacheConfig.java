package com.boco.app.core.network.cache;

import java.util.concurrent.TimeUnit;

/**
 * Created by walle on 2017/12/26.
 */

public class CacheConfig {

    /**
     * 缓存时长 单位：秒
     */
    private int cacheSeconds;
    /**
     * Response的缓存策略
     */
    private ResponseCacheStrategy strategy;

    /**
     * 网络不可用时获取缓存
     */
    private boolean cacheIfNoNetwork;
    private TimeUnit timeUnit;

    private CacheConfig(Builder builder) {
        cacheSeconds = builder.cacheSeconds;
        strategy = builder.strategy;
        cacheSeconds = builder.cacheSeconds;
        cacheIfNoNetwork = builder.cacheIfNoNetwork;
        timeUnit = builder.timeUnit;
    }

    public int getCacheSeconds() {
        return cacheSeconds;
    }
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
    public ResponseCacheStrategy getStrategy() {
        return strategy;
    }

    public boolean isCacheIfNoNetwork() {
        return cacheIfNoNetwork;
    }

    public static class Builder {

        /**
         * 缓存时长 单位：秒 默认90s
         */
        private int cacheSeconds = 90;
        /**
         * Response的缓存策略, 默认 DefaultResponseCacheStrategy
         */
        private ResponseCacheStrategy strategy = null;

        /**
         * 网络不可用时获取缓存 默认为 true
         */
        private boolean cacheIfNoNetwork = true;
        private TimeUnit timeUnit = TimeUnit.SECONDS;

        /**
         * 网络不可用时获取缓存
         *
         * @param cache
         * @return
         */
        public Builder cacheIfNoNetwork(boolean cache) {
            cacheIfNoNetwork = cache;
            return this;
        }



        /**
         * Response的缓存策略
         * @param strategy
         * @return
         */
        public Builder responseCacheStrategy(ResponseCacheStrategy strategy) {
            if (strategy == null) {
                throw  new NullPointerException("strategy can't be null");
            }
            this.strategy = strategy;
            return this;
        }

        /**
         * 缓存时长
         *
         * @param time     时长
         * @param timeUnit 时间单位
         * @return
         */
        public Builder cacheTime(int time, TimeUnit timeUnit) {
            cacheSeconds = time;
            this.timeUnit = timeUnit;
            return this;
        }

        public CacheConfig build() {
            return new CacheConfig(this);
        }
    }
}
