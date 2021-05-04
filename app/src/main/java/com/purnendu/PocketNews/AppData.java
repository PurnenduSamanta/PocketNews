package com.purnendu.PocketNews;

import android.content.Context;
import android.widget.Toast;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppData  {
    private final ArrayList<String> news_header;
    private final ArrayList<String> news_description;
    private final ArrayList<String> news_poster;
    private final ArrayList<String> date;
    private final ArrayList<String> news_url;
    private  NewsDbHelper newsDbHelper;
    private int count=0;

    AppData() {
        news_header = new ArrayList<>();
        news_description = new ArrayList<>();
        news_poster = new ArrayList<>();
        date = new ArrayList<>();
        news_url = new ArrayList<>();
    }
    public void fetch(String url, final RecyclerView recycler, final Context context, final String tableName) {
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
                            if(newsDbHelper.getAllNews(tableName).size()!=0)
                            {
                                ArrayList<NewsModel>data;
                                data=newsDbHelper.getAllNews(tableName);
                                if((data.get(data.size()-1).getTitle()).equals(jsonObject.getString("title")))
                                {
                                    count=1;
                                }
                                else
                                {
                                    AddOperation(articles);
                                    count=0;
                                }
                            }
                            else
                            {
                                AddOperation(articles);
                                count=0;
                            }
                        }
                        if(count==0) {
                            for(int i=0;i<news_header.size();i++)
                            {
                                if(!(newsDbHelper.insertNews(tableName, news_header.get(i), news_description.get(i), news_url.get(i), news_poster.get(i), date.get(i))))
                                {
                                    break;
                                }
                            }
                            ShowDataFromDatabase(context,tableName);
                            SetAdapter(context,recycler,news_header,news_description,news_poster,date,news_url);
                        }
                        else if(count==1)
                        {
                            if(newsDbHelper.getAllNews(tableName).size()!=0)
                            {
                                ShowDataFromDatabase(context,tableName);
                                SetAdapter(context,recycler,news_header,news_description,news_poster,date,news_url);
                            }
                        }
                } catch (JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                newsDbHelper=new NewsDbHelper(context);
                if(newsDbHelper.getAllNews(tableName).size()!=0)
                {
                    ShowDataFromDatabase(context,tableName);
                    SetAdapter(context,recycler,news_header,news_description,news_poster,date,news_url);
                }
                else {
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
                date.add(currentObject.getString("publishedAt"));
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
    public void ShowDataFromDatabase(Context context,String tableName)
    {
        newsDbHelper=new NewsDbHelper(context);
        ArrayList<NewsModel> data;
        data = newsDbHelper.getAllNews(tableName);
        news_header.clear();
        news_description.clear();
        news_poster.clear();
        date.clear();
        news_url.clear();
        for (int i = newsDbHelper.getAllNews(tableName).size()-1; i >=0; i--) {
            news_header.add(data.get(i).getTitle());
            news_description.add(data.get(i).getDescription());
            news_poster.add(data.get(i).getPosterUrl());
            date.add(data.get(i).getDate());
            news_url.add(data.get(i).getNewsUrl());
        }

    }

}
