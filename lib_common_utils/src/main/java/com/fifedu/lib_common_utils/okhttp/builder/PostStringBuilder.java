package com.fifedu.lib_common_utils.okhttp.builder;

import com.fifedu.lib_common_utils.okhttp.request.PostStringRequest;
import com.fifedu.lib_common_utils.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by gaolei on 2023/5/23.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringRequest(url, tag, params, headers, content, mediaType, id).build();
    }


}
