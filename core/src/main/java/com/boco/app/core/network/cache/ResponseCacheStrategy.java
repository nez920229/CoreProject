package com.boco.app.core.network.cache;

import java.io.IOException;

import okio.BufferedSource;

/**
 * Created by walle on 2017/12/26.
 */

public interface ResponseCacheStrategy {
    /**
     * 缓存策略
     * @return 是否缓存
     */
    boolean strategy(BufferedSource source) throws IOException;
}
