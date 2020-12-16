package com.purnendu.PocketNews;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monstertechno.adblocker.AdBlockerWebView;
import com.monstertechno.adblocker.util.AdBlocker;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class news extends AppCompatActivity {
    protected WebView webview;
    private String url;
    protected String headline;
    protected int count;
    protected      SharedPreferences sharedpreferences,sharedpreferences1;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        webview=findViewById(R.id.webview);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            WebSettings webSettings = webview.getSettings();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                webSettings.setForceDark(WebSettings.FORCE_DARK_ON);
            }
        }
        url=getIntent().getStringExtra("url");
        if(url!=null) {
            webview.setWebViewClient(new Browser_home());
            new AdBlockerWebView.init(this).initializeWebView(webview);
            webview.loadUrl(url);
            webview.getSettings().setJavaScriptEnabled(true);
        }
        else{
            Toast.makeText(this, "Something Wrong Happend", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId()==R.id.share1)
        {
            if(url!=null) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, url);
                intent.setType("text/plain/");
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        }
        else if(item.getItemId()==R.id.bookmark) {
               headline = getIntent().getStringExtra("headline");
            if (headline != null) {
                ArrayList<String>header,newsurl;
                Gson gson = new Gson();
                sharedpreferences = getSharedPreferences("BOOKMARKS", MODE_PRIVATE);
                sharedpreferences1 = getSharedPreferences("COUNT", MODE_PRIVATE);
                String json1 = sharedpreferences.getString("title", null);
                String json2 = sharedpreferences.getString("url", null);
                 if(json1!=null && json2!=null) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                     count = sharedpreferences1.getInt("count", -1);
                     count++;
                     Type type = new TypeToken<ArrayList<String>>() {
                     }.getType();
                     header = gson.fromJson(json1, type);
                     newsurl = gson.fromJson(json2, type);
                     if (count > 9) {
                         count = 0;
                     }
                     System.out.println(header.size());
                     if (header.size() > 9) {
                         header.remove(count);
                         newsurl.remove(count);
                     }
                     switch (count) {
                         case (0):
                             header.add(0, headline);
                             newsurl.add(0, url);
                             break;
                         case (1):
                             header.add(1, headline);
                             newsurl.add(1, url);
                             break;
                         case (2):
                             header.add(2, headline);
                             newsurl.add(2, url);
                             break;
                         case (3):
                             header.add(3, headline);
                             newsurl.add(3, url);
                             break;
                         case (4):
                             header.add(4, headline);
                             newsurl.add(4, url);
                             break;
                         case (5):
                             header.add(5, headline);
                             newsurl.add(5, url);
                             break;
                         case (6):
                             header.add(6, headline);
                             newsurl.add(6, url);
                             break;
                         case (7):
                             header.add(7, headline);
                             newsurl.add(7, url);
                             break;
                         case (8):
                             header.add(8, headline);
                             newsurl.add(8, url);
                             break;
                         case (9):
                             header.add(9, headline);
                             newsurl.add(9, url);
                             break;
                     }
                     int t = 0;
                     for (int i = 0; i < header.size(); i++) {
                         for (int j = i + 1; j < header.size(); j++) {
                             if (header.get(i).equals(header.get(j))) {
                                 t++;
                             }
                         }
                     }
                     if (t == 0) {
                         String json3 = gson.toJson(header);
                         String json4 = gson.toJson(newsurl);
                         editor.putString("title", json3);
                         editor.putString("url", json4);
                         editor.apply();
                         editor1.putInt("count", count);
                         editor1.apply();
                         Toast.makeText(this, "Successfully added to Bookmark", Toast.LENGTH_SHORT).show();
                     }
                     else
                     {
                         Toast.makeText(this, "Already added", Toast.LENGTH_SHORT).show();
                     }
                 }
                 else{
                     header=new ArrayList<>();
                     newsurl=new ArrayList<>();
                     header.add(0, headline);
                     newsurl.add(0, url);
                     String json3 = gson.toJson(header);
                     String json4 = gson.toJson(newsurl);
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("title", json3);
                     editor.putString("url", json4);
                     editor.apply();
                     count=0;
                     SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                     editor1.putInt("count", count);
                     editor1.apply();
                     Toast.makeText(this, "Successfully added to Bookmark", Toast.LENGTH_SHORT).show();
                 }
            }
            else
            {
                Toast.makeText(this, "Already in Bookmarks", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
        }

            return true;

    }
    private static class Browser_home extends WebViewClient {

        Browser_home() {}

        @SuppressWarnings("deprecation")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            return AdBlockerWebView.blockAds(view,url) ? AdBlocker.createEmptyResource() :
                    super.shouldInterceptRequest(view, url);

        }

    }
}