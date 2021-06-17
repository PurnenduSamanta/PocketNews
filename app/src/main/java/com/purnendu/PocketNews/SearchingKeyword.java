package com.purnendu.PocketNews;

import android.annotation.SuppressLint;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class SearchingKeyword  {

    private final ArrayList<String> news_header;
    private final ArrayList<String> news_description;
    private final ArrayList<String> news_poster;
    private final ArrayList<String> date;
    private final ArrayList<String> news_url;

   SearchingKeyword() {
        news_header = new ArrayList<>();
        news_description = new ArrayList<>();
        news_poster = new ArrayList<>();
        date = new ArrayList<>();
        news_url = new ArrayList<>();
    }

    public void fetch(String url, final RecyclerView recycler, final Context context) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parent = new JSONObject(response);
                    JSONArray articles = parent.getJSONArray("articles");
                    if (articles.length() != 0) {
                        for (int i = 0; i < articles.length(); i++)
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
                        CustomAdapter customAdapter = new CustomAdapter(context, news_header, news_description, news_poster, date, news_url);
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
                        recycler.setLayoutManager(layoutManager);
                        recycler.setAdapter(customAdapter);
                    } else {
                        String message="No results found";
                        AlertDialog alertDialog=new AlertDialog(context,message);
                    }
                } catch (JSONException e) {
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
