package com.nimura.webbrowser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;

/**
 * Created by Limi on 16.06.2015.
 */
public class WebView extends WebViewFragment {

    private final String HTTP_PREFIX = "http:\\\\";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = super.onCreateView(inflater, container, savedInstanceState);
        getWebView().getSettings().setJavaScriptEnabled(true);
        getWebView().getSettings().setSupportZoom(true);
        getWebView().getSettings().setBuiltInZoomControls(true);
        return result;
    }

    public void refresh(){
        getWebView().reload();
    }

    public void go(String url){
        if(!url.startsWith("http:")){
            url = HTTP_PREFIX + url;
        }
        getWebView().loadUrl(url);
    }
}
