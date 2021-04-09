package com.purnendu.PocketNews;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.monstertechno.adblocker.AdBlockerWebView;

public class NewsApi extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_api);
       WebView webview_newsapi=findViewById(R.id.webview_newsapi);
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            WebSettings webSettings = webview_newsapi.getSettings();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                webSettings.setForceDark(WebSettings.FORCE_DARK_ON);
            }
        }
        String url=getIntent().getStringExtra("NewsPublisherInformationDetails");
        if(url!=null) {
            webview_newsapi.setWebViewClient(new news.Browser_home());
            new AdBlockerWebView.init(this).initializeWebView(webview_newsapi);
            webview_newsapi.loadUrl(url);
            webview_newsapi.getSettings().setJavaScriptEnabled(true);
        }
        else{
            Toast.makeText(this, "Something Wrong happened", Toast.LENGTH_SHORT).show();
        }
    }
    }
