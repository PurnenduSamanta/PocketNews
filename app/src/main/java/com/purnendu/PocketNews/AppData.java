package com.purnendu.PocketNews;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class AppData extends AppCompatActivity {
    public ArrayList<String> news_header, news_description, news_poster, date, news_url;

    AppData() {
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
                        CustomAdapter customAdapter = new CustomAdapter(context, news_header, news_description, news_poster, date, news_url);
                        recycler.setLayoutManager(new LinearLayoutManager(context));
                         recycler.setAdapter(customAdapter);
                    } else {
                        Toast.makeText(context, "No results found", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Something Wrong Happened", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder abuilder = new AlertDialog.Builder(context);
                abuilder.setTitle("Error");
                abuilder.setCancelable(false);
                abuilder.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    }
                });
                abuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        AppData.this.finish();
                        System.exit(0);
                    }
                });
                AlertDialog dialog = abuilder.create();
                dialog.show();


            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
