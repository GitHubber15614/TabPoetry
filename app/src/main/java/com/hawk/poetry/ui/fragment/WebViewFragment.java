package com.hawk.poetry.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hawk.poetry.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends BaseFragment {
    private static final String TAG = "WebViewFragment";
    private ProgressBar fwew_progress;
    private WebView fwew_webview;
    private String webUrl="http://githubber15614.github.io/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mListener.setHomeBackEnable(true);
        mListener.setHeadTitle("我的博客");
        mListener.showHeadRight("刷新");

        View layoutView = inflater.inflate(R.layout.fragment_webview, null);
        fwew_progress = (ProgressBar) layoutView.findViewById(R.id.fwew_progress);
        fwew_webview = (WebView) layoutView.findViewById(R.id.fwew_webview);

        initWebView();
        loadWebUrl(webUrl);

        return layoutView;
    }

    private void initWebView() {
        fwew_webview.getSettings().setJavaScriptEnabled(true);
        fwew_webview.getSettings().setSupportZoom(false);
        fwew_webview.setWebChromeClient(new MyWebViewChromeClient());
        fwew_webview.setWebViewClient(new MyWebViewClient());
        fwew_webview.getSettings().setBuiltInZoomControls(false);
    }

    public void loadWebUrl(String url) {
        Log.i(TAG, "loadWebUrl(),url" + url);
        fwew_progress.setVisibility(View.VISIBLE);
        fwew_progress.setProgress(10);
        fwew_webview.loadUrl(url);
    }

    @Override
    public void clickRightBtn() {
        loadWebUrl(webUrl);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webUrl = url;
            loadWebUrl(url);
            return true;
        }
    }

    private class MyWebViewChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress >= 90) {
                fwew_progress.setProgress(newProgress);
            } else {
                fwew_progress.setProgress(newProgress + 10);
            }
            if (newProgress == 100) {
                //isFirstLoad = false;
                fwew_progress.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
