package com.boco.app.core.network;

/**
 * Created by walle on 2017/9/11.
 */

public class BaseRemote<T> {
    public int code;
    public String msg;
    public T content;

    @Override
    public String toString() {
        return "BaseRemote{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", content=" + content +
                '}';
    }
}
