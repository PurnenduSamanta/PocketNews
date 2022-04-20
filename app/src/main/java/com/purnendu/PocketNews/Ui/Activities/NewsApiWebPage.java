package com.purnendu.PocketNews.Ui.Activities;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Utility;

public class NewsApiWebPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_api);
        WebView webview=findViewById(R.id.webview_newsapi);

        if(!Utility.Companion.checkConnection(this))
        {
            Toast.makeText(NewsApiWebPage.this, "Connection not available", Toast.LENGTH_SHORT).show();
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
