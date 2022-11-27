package com.example.knowledge.view.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


public class WebViewActivity extends AppCompatActivity {
    WebView mWebview;
    TextView mtitle;
    ProgressBar mWebViewPb;
    String localURL = "file:///android_asset/test.html";
    String baidu = "http://www.baidu.com/";
    String noteUrl = "https://notecs.fifedu.com/note-center-static/v100/app/index.html#/noteData/detailPage?id=e9d4c728bb7347f791cc92b51a0844c2&userId=e79d688684964b35a9ab96abe4d5f155&schoolId=2000000026000000001&jtzy=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyZWFsTmFtZSI6IuadjueOuiIsInNjaG9vbElkIjoiMjAwMDAwMDAyNjAwMDAwMDAwMSIsImlzcyI6ImZpZmFjIiwiaWQiOiIyODExMDAwMjI2MDAwNTc3MTY1IiwiZXhwIjoxNjY5MDIzNzIxLCJ1c2VybmFtZSI6ImJmc3VsaXhpIiwibWVtYmVySWQiOiJlNzlkNjg4Njg0OTY0YjM1YTlhYjk2YWJlNGQ1ZjE1NSJ9.LjEt-L1C6kGzH-tsseShJGiwraCBUyDl0sFdaqytQlU%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebview = findViewById(R.id.webView1);
        mWebViewPb = findViewById(R.id.pb_webview);
        mtitle = findViewById(R.id.title);

        initSetting(mWebview);

//        String webViewAgent=mWebview.getSettings().getUserAgentString();
//        String webViewAgent2=new WebView(getApplicationContext()).getSettings().getUserAgentString();
//        String agent=System.getProperty("http.agent");
//        Log.d("WebViewActivity","webViewAgent:"+webViewAgent);
//        Log.d("WebViewActivity","webViewAgent2:"+webViewAgent2);
//        Log.d("WebViewActivity","agent:"+agent);
        initChromeClient();
        initWebViewClient();
        mWebview.loadUrl(noteUrl);
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
                view.loadUrl(url);
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
                System.out.println("标题在这里");
                mtitle.setText(title);
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mWebViewPb.setProgress(newProgress);
                if (newProgress > 98) {
                    mWebViewPb.setVisibility(View.GONE);
                }
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
