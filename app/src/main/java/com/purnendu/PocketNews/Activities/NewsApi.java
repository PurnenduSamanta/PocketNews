package com.purnendu.PocketNews.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.purnendu.PocketNews.Others.Operations;
import com.purnendu.PocketNews.R;

public class NewsApi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_api);
        WebView webview=findViewById(R.id.webview_newsapi);

        if(!Operations.isNetworkConnected(NewsApi.this))
        {
            Toast.makeText(NewsApi.this, "Connection not available", Toast.LENGTH_SHORT).show();
            return;
        }


        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            WebSettings webSettings = webview.getSettings();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                webSettings.setForceDark(WebSettings.FORCE_DARK_ON);
            }
        }
            try {
                webview.loadUrl("https://purnendusamanta.blogspot.com/p/news-publisher-information-details.html");
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
    }
    }
