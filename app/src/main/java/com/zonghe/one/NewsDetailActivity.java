package com.zonghe.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsDetailActivity extends AppCompatActivity {
    private String mURL;
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        mWebView = findViewById(R.id.newsDetailWebView);
         mURL =getIntent().getStringExtra("NewsURL");

        WebSettings ws=mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mURL);

    }
}
