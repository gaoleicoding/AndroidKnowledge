package com.fifedu.lib_common_utils.okhttp.builder;

import java.util.Map;

/**
 * Created by gaolei on 2023/5/23.
 */
public interface HasParamsable {
    OkHttpRequestBuilder params(Map<String, String> params);

    OkHttpRequestBuilder addParams(String key, String val);
}
