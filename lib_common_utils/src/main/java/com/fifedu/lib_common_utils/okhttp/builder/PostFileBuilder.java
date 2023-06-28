package com.fifedu.lib_common_utils.okhttp.builder;

import com.fifedu.lib_common_utils.okhttp.request.PostFileRequest;
import com.fifedu.lib_common_utils.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by gaolei on 2023/5/23.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(url, tag, params, headers, file, mediaType, id).build();
    }


}
