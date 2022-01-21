package com.purnendu.PocketNews.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purnendu.PocketNews.Adapters.BookmarkAdapter;
import com.purnendu.PocketNews.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Bookmarks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        RecyclerView bookmark_recycler = findViewById(R.id.bookmark_recycler);
        SharedPreferences sharedpreferences = getSharedPreferences("BOOKMARKS", MODE_PRIVATE);
        Gson gson = new Gson();
        String json1 = sharedpreferences.getString("title", null);
        String json2 = sharedpreferences.getString("url", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> header = gson.fromJson(json1, type);
        ArrayList<String> url = gson.fromJson(json2, type);
        if (header != null && url != null) {
            BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(this, header, url);
            bookmark_recycler.setAdapter(bookmarkAdapter);
            bookmark_recycler.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Toast.makeText(this, "No Bookmarks ", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Bookmarks.this, MainActivity.class);
        startActivity(intent);
    }
}