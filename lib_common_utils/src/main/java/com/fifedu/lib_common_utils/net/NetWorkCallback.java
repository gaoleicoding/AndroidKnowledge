package com.fifedu.lib_common_utils.net;

/**
 * Created by bff007 on 2017/11/13.
 */
public abstract class NetWorkCallback<T> {
    public abstract void onSuccess(T result);

    public abstract void onFailed(T error);

    public void onLoading(long total, long current) {

    }
}
