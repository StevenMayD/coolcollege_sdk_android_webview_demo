package com.coolcollege.dsbridgedemo;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.coolcollege.aar.bean.NativeEventParams;
import com.coolcollege.aar.callback.KXYCallback;
import com.coolcollege.aar.module.APIModule;
import com.coolcollege.aar.selector.MediaSelector;
import com.coolcollege.aar.utils.ToastUtil;
import com.google.gson.Gson;

import wendu.dsbridge.CompletionHandler;
import wendu.dsbridge.DWebView;
import wendu.dsbridge.OnReturnValue;


public class MainActivity extends Activity {

    public DWebView webView;
    private String acToken = "af0e91b07e9a4887a9f3d895fc80c732";
    private String entId = "1067985194709028888";

    private Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Utils.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            super.onActivityResumed(activity);
            Log.e("onActiveChange", "foreground-");
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            super.onActivityPaused(activity);
            Log.e("onActiveChange", "background-");
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_d_webview);

        webView = findViewById(R.id.webview);

        settingsWebView();

        DWebView.setWebContentsDebuggingEnabled(false); // 关闭webview调试模式（否则找不到交互方法会弹框提醒）

//        webView.addJavascriptObject(new DsbridgeJsApi(MainActivity.this),"local");
//        webView.addJavascriptObject(new DsbridgeJsApi(MainActivity.this),"navigation");
//        webView.addJavascriptObject(new DsbridgeJsApi(MainActivity.this),"");

        webView.addJavascriptObject(this,"local");
        webView.addJavascriptObject(this,"navigation");
        webView.addJavascriptObject(this,"");
        webView.addJavascriptObject(this,"util"); // scan交互的命名空间

//        webView.loadUrl("https://sdn.coolcollege.cn/assets/h5-photo-camera/index.html");
        webView.loadUrl("https://app.coolcollege.cn?token=zKpCwDQMivdtzA6VDdCWy0bdhwd7R0/HjTM63bzx3cBjyUwbws0l51sNrcFZwIkb"); // 线上企业

        // 重写WebViewClient（否则webview的访问意图对象会被拒绝）
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    @JavascriptInterface
    public void nativeEvent(Object msg, CompletionHandler<String> handler){
        Log.e("msg",""+ msg);
        NativeEventParams params = new Gson().fromJson(msg.toString(), NativeEventParams.class);
        APIModule.getAPIModule(this).moduleManage(params, acToken, entId, 123, new KXYCallback() {
            @Override
            public void onOKCallback(Object o) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(new Gson().toJson(o));
                    }
                });
            }

            @Override
            public void onErrorCallback(Object o) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(new Gson().toJson(o));
                    }
                });
            }
        });
    }

    // 交互方法添加 @JavascriptInterface
    @JavascriptInterface
    public void scan(Object msg, CompletionHandler<String> handler){
        Log.e("msg",""+ msg);
        NativeEventParams params = new NativeEventParams();
        params.methodName = "scan";
        params.methodData = "{}";
        APIModule.getAPIModule(this).moduleManage(params, acToken, entId, 123, new KXYCallback() {
            @Override
            public void onOKCallback(Object o) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(new Gson().toJson(o));
                    }
                });
            }

            @Override
            public void onErrorCallback(Object o) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(new Gson().toJson(o));
                    }
                });
            }
        });
    }

    private void settingsWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }

        webView.setBackgroundColor(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            settings.setMediaPlaybackRequiresUserGesture(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setUserAgentString("Android_App");
        settings.setAllowFileAccess(false);
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCacheDir);
        settings.setUseWideViewPort(true);
//        super.setWebChromeClient(mWebChromeClient);
//        addInternalJavascriptObject();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            webView.addJavascriptInterface(null, "_dsbridge");
        } else {
            // add bridge tag in lower android version
            settings.setUserAgentString(settings.getUserAgentString() + " _dsbridge");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        String text = new Gson().toJson(data.getParcelableExtra(MediaSelector.RESULT_DATA) != null ? data.getParcelableExtra(MediaSelector.RESULT_DATA) : data.getParcelableArrayListExtra(MediaSelector.RESULT_DATA));
        text = (text != null && !("null".equals(text)))?text:data.getStringExtra(MediaSelector.RESULT_DATA);
        ToastUtil.showToast(text);
    }
}
