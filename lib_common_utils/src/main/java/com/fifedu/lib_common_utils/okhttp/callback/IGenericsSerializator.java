package com.fifedu.lib_common_utils.okhttp.callback;

/**
 * Created by gaolei on 2023/5/23.
 */
public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
