package com.example.knowledge.component.webview;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.knowledge.R;
import com.example.knowledge.activity.BaseActivity;
import com.example.knowledge.android.LBS.LocationUtil;
import com.example.knowledge.bean.HybridEntity;
import com.example.knowledge.image.glide.GlideSvgUtil;
import com.fifedu.lib_common_utils.log.LogUtils;
import com.google.gson.Gson;


public class WebViewActivity extends BaseActivity {
    private final String TAG = "WebViewActivity";
    WebView mWebview;
    ImageView mSvgIV;
    String localURL = "file:///android_asset/index.html";
    String baiduURL = "http://www.baidu.com/";
    String wakeURL = "https://hecs.fifedu.com/iplat/app_link/learning.html?a=2";
    String noteURL = "https://notecs.fifedu.com/note-center-static/v100/app/index.html#/noteData/detailPage?id=e9d4c728bb7347f791cc92b51a0844c2&userId=e79d688684964b35a9ab96abe4d5f155&schoolId=2000000026000000001&jtzy=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyZWFsTmFtZSI6IuadjueOuiIsInNjaG9vbElkIjoiMjAwMDAwMDAyNjAwMDAwMDAwMSIsImlzcyI6ImZpZmFjIiwiaWQiOiIyODExMDAwMjI2MDAwNTc3MTY1IiwiZXhwIjoxNjY5MDIzNzIxLCJ1c2VybmFtZSI6ImJmc3VsaXhpIiwibWVtYmVySWQiOiJlNzlkNjg4Njg0OTY0YjM1YTlhYjk2YWJlNGQ1ZjE1NSJ9.LjEt-L1C6kGzH-tsseShJGiwraCBUyDl0sFdaqytQlU%3D%3D";
    String svgUrl = "https://test.fifedu.com/static/fiftest//upload/image/20220426/86fde1bb67924d339c2de7d17b55e7b5.svg";

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        mWebview = findViewById(R.id.webview);
        mSvgIV = findViewById(R.id.iv_svg);
    }

    public void onResume() {
        super.onResume();
        initSetting(mWebview);
//        initWebViewClient();
        initChromeClient();
        mWebview.loadUrl(localURL);
        mWebview.addJavascriptInterface(new CommonJSInterface(), "androidInjected");
    }

    @Override
    public void initData() {
        GlideSvgUtil.showSvgUrl(mSvgIV, svgUrl);
//        GlideSvgUtil.showSvgRes(mSvgIV,R.raw.svg_symbol);
    }

    private void initSetting(WebView webView) {
        WebSettings mWebSettings = webView.getSettings();

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        mWebSettings.setJavaScriptEnabled(true);//设置支持javaScript
        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        mWebSettings.setUserAgentString("User-Agent");
        mWebSettings.setLightTouchEnabled(true);//设置用鼠标激活被选项
        mWebSettings.setBuiltInZoomControls(true);//设置支持缩放
        mWebSettings.setDomStorageEnabled(true);//设置DOM缓存，当H5网页使用localStorage时，一定要设置
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//设置去缓存，防止加载的为上一次加载过的
        mWebSettings.setSupportZoom(true);//设置支持变焦
        webView.setHapticFeedbackEnabled(false);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);
        mWebSettings.setAllowUniversalAccessFromFileURLs(true);
        mWebSettings.setAllowFileAccessFromFileURLs(true);
        mWebSettings.setFixedFontFamily("cursive");
        mWebSettings.setDefaultTextEncodingName("UTF-8");
    }

    private void initWebViewClient() {
        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //设置不用系统浏览器打开,直接显示在当前Webview
//                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initChromeClient() {
        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {

            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                mTitleTv.setText(title);
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public class CommonJSInterface {
        @JavascriptInterface
        public void webCallNative(final String json) {
            LogUtils.d(TAG, "webCallNative: " + json);
            final Gson gson = new Gson();
            final HybridEntity h5CallAppData = gson.fromJson(json, HybridEntity.class);
            final String callNativeAction = h5CallAppData.getAppAction();
            final String callWebAction = h5CallAppData.getWebAction();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (LocationUtil.getUserCurrentLocation.equals(callNativeAction)) {
                        final LocationUtil jsCallAppUtil = new LocationUtil(WebViewActivity.this);
                        jsCallAppUtil.handleLocation(mWebview, callNativeAction, callWebAction);
                    }
                }
            });
        }
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }

}
