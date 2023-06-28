package com.fifedu.lib_common_utils.net;

import android.text.TextUtils;

import com.fifedu.lib_common_utils.AppUtil;
import com.fifedu.lib_common_utils.FileUtils;
import com.fifedu.lib_common_utils.okhttp.OkHttpUtils;
import com.fifedu.lib_common_utils.okhttp.callback.FileCallBack;
import com.fifedu.lib_common_utils.okhttp.callback.StringCallback;
import com.fifedu.lib_common_utils.okhttp.log.LoggerInterceptor;
import com.fifedu.lib_common_utils.okhttp.request.RequestCall;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;


/**
 * 用于网络请求
 */
public class NetWorkUtils {
    private final static String TAG = "NetWorkUtils";
    public static final int METHOD_POST = 0;
    public static final int METHOD_GET = 1;
    private final OkHttpClient okHttpClient;
    private Call call;
    private LoggerInterceptor loggerInterceptor;

    // 虽然此类有单例，但为了兼容以前版本，构造函数仍使用public
    public NetWorkUtils() {
        this(20, 0);
    }

    public NetWorkUtils(int seconds, int retryCount) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(seconds, TimeUnit.SECONDS)
                .readTimeout(seconds, TimeUnit.SECONDS);
        if (AppUtil.isDebugMode()) {
            loggerInterceptor = new LoggerInterceptor(TAG, true);
            builder.addInterceptor(loggerInterceptor);
        }
        okHttpClient = builder.build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static NetWorkUtils instance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private final static NetWorkUtils instance = new NetWorkUtils();
    }


    /**
     * 发送请求
     *
     * @param method          请求方式
     * @param mParams         参数列表
     * @param url             请求链接
     * @param netWorkCallback 请求回调
     */
    public void send(int method, LinkedHashMap<String, String> mParams, String url, final NetWorkCallback<String> netWorkCallback) {

        if (method == METHOD_GET) {
            okhttpGet(mParams, url, netWorkCallback);
        } else {
            okhttpPost(mParams, url, netWorkCallback);
        }

    }

    public void okhttpGet(LinkedHashMap<String, String> mParams, String url, final NetWorkCallback<String> netWorkCallback) {
        if (!TextUtils.isEmpty(url) && url.contains("?")) {
            //用于处理特殊的url
            mParams = getParams(url);
            url = url.split("\\?")[0];
        }
        RequestCall requestCall = OkHttpUtils
                .get()
                .url(url)
                .params(mParams)
                .build();
        call = requestCall.getCall();
        requestCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                netWorkCallback.onFailed(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                netWorkCallback.onSuccess(response);
            }

        });
    }

    public void okhttpPost(LinkedHashMap<String, String> mParams, String url, final NetWorkCallback<String> netWorkCallback) {
        RequestCall requestCall = OkHttpUtils
                .post()
                .url(url)
                .params(mParams)
                .build();
        call = requestCall.getCall();
        requestCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                netWorkCallback.onFailed(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                netWorkCallback.onSuccess(response);
            }

        });
    }

    public void download(String url, String path, boolean autoResume, final NetWorkCallback<File> netWorkCallback) {
        String fileName = FileUtils.getFileNameWithSuffix(path);
        RequestCall requestCall = OkHttpUtils
                .get()
                .url(url)
                .build();
        call = requestCall.getCall();
        requestCall.execute(new FileCallBack(path, fileName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                netWorkCallback.onFailed(new File(""));
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                netWorkCallback.onLoading(total, (long) (total * progress));
            }

            @Override
            public void onResponse(File response, int id) {
                netWorkCallback.onSuccess(response);
            }

        });
    }

    public void upload(File file, String key, LinkedHashMap<String, String> mParams, String url, final NetWorkCallback<File> netWorkCallback) {
        if (file == null) return;
        String path = file.getAbsolutePath();
        String fileName = FileUtils.getFileNameWithSuffix(path);
        HttpUrl.Builder urlBuiler = HttpUrl.parse(url)
                .newBuilder();
        for (Map.Entry<String, String> entry : mParams.entrySet()) {
            urlBuiler.addQueryParameter(entry.getKey(), entry.getValue());
        }
        RequestCall requestCall = OkHttpUtils.post()
                .addFile(key, fileName, file)
                .url(urlBuiler.build().toString())
                .build();
        call = requestCall.getCall();
        requestCall.execute(new FileCallBack(path, fileName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                netWorkCallback.onFailed(new File(""));
            }

            @Override
            public void onResponse(File response, int id) {
                netWorkCallback.onSuccess(response);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                netWorkCallback.onLoading(total, (long) (total * progress));
            }
        });
    }

    // 上传文件增加带有进度的方法
    public void uploadPro(File file, String key, LinkedHashMap<String, String> mParams, String url, final NetWorkCallback<File> netWorkCallback) {
        if (file == null) return;
        String path = file.getAbsolutePath();
        String fileName = FileUtils.getFileNameWithSuffix(path);
        File parentFile = new File(path).getParentFile();
        if (parentFile != null)
            path = parentFile.getAbsolutePath();
        HttpUrl.Builder urlBuiler = HttpUrl.parse(url)
                .newBuilder();
        for (Map.Entry<String, String> entry : mParams.entrySet()) {
            urlBuiler.addQueryParameter(entry.getKey(), entry.getValue());
        }
        RequestCall requestCall = OkHttpUtils.post()
                .addFile(key, fileName, file)
                .url(urlBuiler.build().toString())
                .build();
        call = requestCall.getCall();
        requestCall.execute(new FileCallBack(path, fileName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                netWorkCallback.onFailed(new File(""));
            }

            @Override
            public void onResponse(File response, int id) {
                netWorkCallback.onSuccess(response);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                netWorkCallback.onLoading(total, (long) (total * progress));
            }
        });
    }

    /**
     * 判断是否正处于网络加载过程中
     */
    public boolean isLoading() {
        return call != null && !call.isCanceled();
    }


    /**
     * 取消本次请求
     */
    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

    //用于获取get方法的参数值（类似于/kyxl/account/isValidCodeCorrect?username=%1$s&mobile=%2$s&validCode=%3$s）
    private LinkedHashMap<String, String> getParams(String url) {
        LinkedHashMap<String, String> mParams = new LinkedHashMap<>();
        if (!TextUtils.isEmpty(url)) {
            String[] strs = url.split("\\?");
            if (strs.length > 1) {
                String tempParams = strs[1];
                if (!TextUtils.isEmpty(tempParams)) {
                    String[] params = tempParams.split("&");
                    for (String currentParam : params) {
                        String[] realParam = currentParam.split("=");
                        mParams.put(realParam[0], realParam.length > 1 ? realParam[1] : "");
                    }
                }
            }
        }
        return mParams;
    }

}
