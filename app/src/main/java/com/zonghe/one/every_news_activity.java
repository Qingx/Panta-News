package com.zonghe.one;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zonghe.one.JSONNewsEntityClass.Contentlist;

import java.io.Serializable;

public class every_news_activity extends AppCompatActivity implements View.OnClickListener {
    private WebView mWebView;
    private ImageButton everynews_back;
    private String html;
    private String source;
    private String pubDate;
    private String title;
    private Contentlist mContentlist;
    private TextView mTitleTextView;
    private TextView mSourceTextView;
    private TextView mDateTextView;
    private Button sharebtn;
    private static final String TAG ="啊啊啊啊啊" ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_news);
        findViews();
        //获得传递的数据
        Intent intent = getIntent();
        mContentlist= (Contentlist) intent.getSerializableExtra("sendContentlist");
        html=mContentlist.getHtml();
        source = mContentlist.getSource();
        pubDate =mContentlist.getPubDate();
        title = mContentlist.getTitle();

        //数据绑定xml
        mTitleTextView.setText(title);
        mSourceTextView.setText(source);
        mDateTextView.setText(pubDate);


        WebSettings ws=mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadData(html,"text/html","UTF-8");
        mWebView.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner");//这个是给图片设置点击监听的，如果你项目需要webview中图片，点击查看大图功能，可以这
        everynews_back=(ImageButton)findViewById(R.id.everynews_back);
        everynews_back.setOnClickListener(this);

        sharebtn=(Button)findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share();
            }
        });

    }

    private void findViews() {
        mWebView = findViewById(R.id.newsDetailWebView);
        mTitleTextView=findViewById(R.id.everynews_title);
        mSourceTextView =findViewById(R.id.everynews_author);
        mDateTextView = findViewById(R.id.everynews_time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.everynews_back:
                finish();
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();//重置webview中img标签的图片大小
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    private void imgReset() {
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }
    public static class JavaScriptInterface {

        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img) {
            Log.i("TAG", "响应点击事件!");
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, BigImageActivity.class);//BigImageActivity查看大图的类，自己定义就好
            context.startActivity(intent);
        }
    }

    private void Share(){

        Log.d(TAG, "Share:LINk "+mContentlist.getLink());
        Intent shareIntent=new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"震惊\n"+mContentlist.getTitle()+"\n"+mContentlist.getLink());
        shareIntent=Intent.createChooser(shareIntent,"分享至");
        startActivity(shareIntent);
    }

}
