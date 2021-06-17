package com.purnendu.PocketNews;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import androidx.annotation.Nullable;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.purnendu.PocketNews.SqliteDatabase.NewsDbHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Services extends IntentService {

    public Services() {
        super("hello");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        boolean connected=isNetworkConnected();
        NewsDbHelper newsDbHelper=new NewsDbHelper(getApplicationContext());
        String tableName="trending";
        if((newsDbHelper.getAllNews(tableName).size()!=0) && (connected))
        {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
            String str = sharedPreferences.getString("country", null);
            String url;
            if (str != null)
            {
                url = "https://newsapi.org/v2/top-headlines?country=" + str + "&apiKey=56641f7a6ad548e58cb9bf96c03e9174";
            } else {
                url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=56641f7a6ad548e58cb9bf96c03e9174";
            }
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject parent = new JSONObject(response);
                        JSONArray articles = parent.getJSONArray("articles");
                        JSONObject jsonObject=articles.getJSONObject(0);

                            String title= jsonObject.getString("title");
                            String icon=jsonObject.getString("urlToImage");


                            if(((title.equals("null"))||(title.equals(""))))
                            {
                                title="Time to check your daily doze";
                            }
                            if(((icon.equals("null"))||(icon.equals(""))))
                            {
                                icon="NoImage";
                            }
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            NotificationHelper notificationHelper=new NotificationHelper(getApplicationContext(),title, icon,i,100);

                    } catch (JSONException ignored) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            })
            {
                @Override
                public Map<String, String> getHeaders(){
                    Map<String, String> headers = new HashMap<>();
                    headers.put("User-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
                    return headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
