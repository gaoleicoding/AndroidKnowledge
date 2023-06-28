package com.fifedu.lib_common_utils.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

/**
 * Created by gaolei on 2023/5/23.
 */
public abstract class BitmapCallback extends Callback<Bitmap> {
    @Override
    public Bitmap parseNetworkResponse(Response response, int id) throws Exception {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}
