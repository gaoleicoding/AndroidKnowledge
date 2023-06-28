package com.fifedu.lib_common_utils.okhttp.builder;

import com.fifedu.lib_common_utils.okhttp.OkHttpUtils;
import com.fifedu.lib_common_utils.okhttp.request.OtherRequest;
import com.fifedu.lib_common_utils.okhttp.request.RequestCall;

/**
 * Created by gaolei on 2023/5/23.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
