package com.fifedu.lib_common_utils.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by gaolei on 2023/5/23.
 */
public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        return response.body().string();
    }
}
