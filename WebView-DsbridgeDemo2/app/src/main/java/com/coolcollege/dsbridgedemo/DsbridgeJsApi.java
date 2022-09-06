package com.coolcollege.dsbridgedemo;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import wendu.dsbridge.CompletionHandler;

public class DsbridgeJsApi {
    public Context mContext;
    public DsbridgeJsApi(Context mContext){
        this. mContext = mContext;
    }


    @JavascriptInterface
    public void nativeEvent(Object msg, CompletionHandler<String> handler){
        Log.e("-----",""+ msg);
        Toast.makeText(mContext, String.valueOf(msg), Toast.LENGTH_LONG).show();
        handler.complete(msg+" [ asyn call]");
    }
}
