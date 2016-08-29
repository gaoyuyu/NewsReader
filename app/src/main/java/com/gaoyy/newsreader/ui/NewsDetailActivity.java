package com.gaoyy.newsreader.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gaoyy.newsreader.R;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class NewsDetailActivity extends AppCompatActivity
{
    private Toolbar newsDetailToolbar;
    private WebView newsDetailWebview;
    private ProgressWheel newsDetailProgresswheel;

    private void assignViews()
    {
        newsDetailToolbar = (Toolbar) findViewById(R.id.news_detail_toolbar);
        newsDetailWebview = (WebView) findViewById(R.id.news_detail_webview);
        newsDetailProgresswheel = (ProgressWheel) findViewById(R.id.news_detail_progresswheel);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        assignViews();
        configViews();
    }

    private void configViews()
    {
        setStatusBar();
        String newsUrl = getIntent().getStringExtra("url");
        newsDetailToolbar.setTitle("");
        setSupportActionBar(newsDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setWebView(newsUrl);
    }

    private void setWebView(String newsUrl)
    {
        newsDetailWebview.getSettings().setJavaScriptEnabled(true);
        newsDetailWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        newsDetailWebview.loadUrl(newsUrl);

        newsDetailWebview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                view.setVisibility(View.GONE);
                newsDetailProgresswheel.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                view.setVisibility(View.VISIBLE);
                newsDetailProgresswheel.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });

    }

    /**
     * 设置状态栏
     */
    private void setStatusBar()
    {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);
        tintManager.setStatusBarTintEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (newsDetailWebview.canGoBack())
            {
                newsDetailWebview.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
