package com.purnendu.PocketNews.Others;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.purnendu.PocketNews.Adapters.CustomAdapter;
import com.purnendu.PocketNews.Model.NewsModel;
import com.purnendu.PocketNews.SqliteDatabase.NewsDbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class Operations {


    public static ArrayList<NewsModel.articles> AddOperation(ArrayList<NewsModel.articles> articles) {

        ArrayList<NewsModel.articles> newsList=new ArrayList<>();
        for (int i = articles.size() - 1; i >= 0; i--) {

            String title = articles.get(i).getTitle();
            String desc = articles.get(i).getDescription();
            String imageUrl = articles.get(i).getImageUrl();
            String publicationDate = articles.get(i).getDate();
            String newsUrl = articles.get(i).getNewsUrl();

            if (title == null)
                continue;

            if (title.isEmpty())
                continue;

            if (title.equals("null"))
                continue;

            if (desc == null)
                continue;

            if (desc.isEmpty())
                continue;

            if (desc.equals("null"))
                continue;

            if (imageUrl == null)
                continue;

            if (imageUrl.isEmpty())
                continue;

            if (imageUrl.equals("null"))
                continue;

            if (publicationDate == null)
                continue;

            if (publicationDate.isEmpty())
                continue;

            if (publicationDate.equals("null"))
                continue;

            if (newsUrl == null)
                continue;

            if (newsUrl.isEmpty())
                continue;

            if (newsUrl.equals("null"))
                continue;


            NewsModel.articles news = articles.get(i);

            String date = getProperDateInFormat(news.getDate());
            if (date == null)
                continue;

            newsList.add(new NewsModel.articles(news.getTitle(), news.getDescription(), news.getImageUrl(), news.getNewsUrl(), date));
        }
        return newsList;

    }

    private static String getProperDateInFormat(String unformattedDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        java.util.Date dateObj = null;
        try {
            dateObj = format.parse(unformattedDate);
        } catch (ParseException ignored) {
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat postFormat = new SimpleDateFormat("dd MMMM HH:mm");
        if (dateObj == null) {
            return null;
        }
        return postFormat.format(dateObj);
    }

    public static void SetAdapter(Context context, RecyclerView recyclerView, ArrayList<NewsModel.articles> newsList) {
        CustomAdapter customAdapter = new CustomAdapter(context, newsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapter);

    }

    public static ArrayList<NewsModel.articles> ShowDataFromDatabase(String tableName, Context context) {
        NewsDbHelper newsDbHelper = new NewsDbHelper(context);
        ArrayList<NewsModel.articles> newsList=new ArrayList<>();
        ArrayList<NewsModel.articles> data;
        data = newsDbHelper.getAllNews(tableName);
        for (int i = newsDbHelper.getAllNews(tableName).size() - 1; i >= 0; i--) {
            NewsModel.articles news = data.get(i);
            newsList.add(new NewsModel.articles(news.getTitle(), news.getDescription(), news.getImageUrl(), news.getNewsUrl(), news.getDate()));
        }
        return newsList;

    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }




}
