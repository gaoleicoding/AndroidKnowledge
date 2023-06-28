package com.fifedu.lib_common_utils.okhttp.utils;

/**
 * Created by gaolei on 2023/5/23.
 */
public class Exceptions {
    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }


}
