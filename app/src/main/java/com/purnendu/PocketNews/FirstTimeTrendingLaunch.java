package com.purnendu.PocketNews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.purnendu.PocketNews.SqliteDatabase.NewsDbHelper;
import com.trncic.library.DottedProgressBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class FirstTimeTrendingLaunch extends AppCompatActivity {

    private final ArrayList<String> news_header;
    private final ArrayList<String> news_description;
    private final ArrayList<String> news_poster;
    private final ArrayList<String> date;
    private final ArrayList<String> news_url;
    private  NewsDbHelper newsDbHelper;
    private final Context context;

    FirstTimeTrendingLaunch(Context context) {
        news_header = new ArrayList<>();
        news_description = new ArrayList<>();
        news_poster = new ArrayList<>();
        date = new ArrayList<>();
        news_url = new ArrayList<>();
        this.context=context;
        newsDbHelper=new NewsDbHelper(context);
    }

    public void UpToDateDatabase() {
        final DottedProgressBar dottedProgressBar=(DottedProgressBar)((Activity)context).findViewById(R.id.dotProgress);
        dottedProgressBar.setVisibility(View.VISIBLE);
        dottedProgressBar.startProgress();
        final String tableName="trending";
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("country", null);
        String url;
        if (str != null)
        {
            url = "https://newsapi.org/v2/top-headlines?country=" + str + "&apiKey=56641f7a6ad548e58cb9bf96c03e9174";
        } else {
            url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=56641f7a6ad548e58cb9bf96c03e9174";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {




            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parent = new JSONObject(response);
                    JSONArray articles = parent.getJSONArray("articles");
                    JSONObject jsonObject=articles.getJSONObject(0);
                    if((!jsonObject.getString("title").equals("null"))&&(!jsonObject.getString("title").equals("")))
                    {
                        if(newsDbHelper.getAllNews(tableName).size()!=0)
                        {
                            ClearArrayList();
                            String lastNews=newsDbHelper.getLastNews(tableName);
                            if(!(lastNews.equals(jsonObject.getString("title"))))
                            {
                                AddOperation(articles);
                            }
                            else
                            {
                                Intent i=new Intent(context,MainActivity.class);
                                context.startActivity(i);
                            }
                        }
                        else
                        {
                            AddOperation(articles);
                        }
                        for(int i=0;i<news_header.size();i++)
                        {
                            if(!(newsDbHelper.insertNews(tableName, news_header.get(i), news_description.get(i), news_url.get(i), news_poster.get(i), date.get(i))))
                            {
                                break;
                            }
                        }
                        dottedProgressBar.stopProgress();
                        //Lunching MainActivity
                        Intent i=new Intent(context,MainActivity.class);
                        context.startActivity(i);
                    }
                } catch (JSONException e) {
                    dottedProgressBar.stopProgress();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.exit(0);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dottedProgressBar.stopProgress();
                //Lunching MainActivity
                Intent i=new Intent(context,MainActivity.class);
                context.startActivity(i);
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
        requestQueue.add(stringRequest);
    }
    public  void LoadFromDatabase(String url, final RecyclerView recycler, final String tableName)
    {
        if(newsDbHelper.getAllNews(tableName).size()!=0)
        {
            ShowDataFromDatabase(tableName);
            SetAdapter(context,recycler,news_header,news_description,news_poster,date,news_url);
        }
        else if(newsDbHelper.getAllNews(tableName).size()==0)
        {
            ClearArrayList();
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        newsDbHelper=new NewsDbHelper(context);
                        JSONObject parent = new JSONObject(response);
                        JSONArray articles = parent.getJSONArray("articles");
                        JSONObject jsonObject=articles.getJSONObject(0);
                        if((!jsonObject.getString("title").equals("null"))&&(!jsonObject.getString("title").equals("")))
                        {
                            AddOperation(articles);
                        }
                        for(int i=0;i<news_header.size();i++)
                        {
                            if(!(newsDbHelper.insertNews(tableName, news_header.get(i), news_description.get(i), news_url.get(i), news_poster.get(i), date.get(i))))
                            {
                                break;
                            }
                        }
                        ShowDataFromDatabase(tableName);
                        SetAdapter(context,recycler,news_header,news_description,news_poster,date,news_url);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                        String message="Error";
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            message="Connection time out";
                        } else if (error instanceof AuthFailureError) {
                            message="Auth Error";
                        } else if (error instanceof ServerError) {
                            message="Server Error";
                        } else if (error instanceof NetworkError) {
                            message="Network Error";
                        } else if (error instanceof ParseError) {
                            message="Parsing Error";
                        }
                        AlertDialog alertDialog=new AlertDialog(context,message);
                }
            })
            {
                @Override
                public Map<String, String> getHeaders(){
                    Map<String, String> headers = new HashMap<>();
                    headers.put("User-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
                    return headers;
                }
            }
                    ;
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
    }

    public void AddOperation(JSONArray articles) throws JSONException {
        for (int i = articles.length()-1; i >=0; i--)
        {
            JSONObject currentObject = articles.getJSONObject(i);
            if((!currentObject.getString("title").equals("null"))&&(!currentObject.getString("title").equals(""))) {
                news_header.add(currentObject.getString("title"));
            }
            else
            {
                news_header.add("Title not available");
            }
            if((!currentObject.getString("description").equals("null"))&&(!currentObject.getString("description").equals(""))) {
                news_description.add(currentObject.getString("description"));
            }
            else
            {
                news_description.add(" ");
            }
            if((!currentObject.getString("urlToImage").equals("null"))&&(!currentObject.getString("urlToImage").equals(""))) {
                news_poster.add(currentObject.getString("urlToImage"));
            }
            else
            {
                news_poster.add("noimage");
            }
            if((!currentObject.getString("publishedAt").equals("null"))&&(!currentObject.getString("publishedAt").equals(""))) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss'Z'");
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                java.util.Date dateObj = null;
                try
                {
                    dateObj = format.parse(currentObject.getString("publishedAt"));
                }
                catch ( ParseException ignored)
                { }
                @SuppressLint("SimpleDateFormat") SimpleDateFormat postFormat = new SimpleDateFormat("dd MMMM HH:mm");
                if(dateObj!=null)
                {
                    String newDateStr = postFormat.format(dateObj);
                    date.add(newDateStr);
                }
                else
                {
                    date.add(currentObject.getString("publishedAt"));
                }
            }
            else
            {
                date.add(" ");
            }
            if((!currentObject.getString("url").equals("null"))&&(!currentObject.getString("url").equals(""))) {
                news_url.add(currentObject.getString("url"));
            }
            else
            {
                news_url.add(null);
            }
        }

    }
    public void SetAdapter(Context context,RecyclerView recyclerView,ArrayList<String> header,ArrayList<String>desc,ArrayList<String>poster,ArrayList<String>date,ArrayList<String>newsUrl)
    {
        CustomAdapter customAdapter = new CustomAdapter(context,header,desc,poster, date,newsUrl);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapter);

    }
    public void ShowDataFromDatabase(String tableName)
    {
        ArrayList<NewsModel> data;
        data = newsDbHelper.getAllNews(tableName);
        ClearArrayList();
        for (int i = newsDbHelper.getAllNews(tableName).size()-1; i >=0; i--) {
            news_header.add(data.get(i).getTitle());
            news_description.add(data.get(i).getDescription());
            news_poster.add(data.get(i).getPosterUrl());
            date.add(data.get(i).getDate());
            news_url.add(data.get(i).getNewsUrl());
        }

    }
    public void ClearArrayList()
    {
        news_header.clear();
        news_description.clear();
        news_poster.clear();
        date.clear();
        news_url.clear();
    }

}
