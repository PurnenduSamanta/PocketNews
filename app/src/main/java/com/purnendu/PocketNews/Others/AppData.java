package com.purnendu.PocketNews.Others;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.purnendu.PocketNews.Activities.AlertDialog;
import com.purnendu.PocketNews.Model.NewsModel;
import com.purnendu.PocketNews.Retrofit.Api;
import com.purnendu.PocketNews.Retrofit.Client;
import com.purnendu.PocketNews.SqliteDatabase.NewsDbHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppData {
    private ArrayList<NewsModel.articles> newsList;
    private NewsDbHelper newsDbHelper;
    private int count = 0;
    private final Api api;

    public AppData() {
        newsList = new ArrayList<>();
        api = Client.getRetrofitInstance().create(Api.class);

    }

    public void fetch(String category, String countryCode, String apiKey, final RecyclerView recycler, final Context context, final String tableName) {
        newsDbHelper = new NewsDbHelper(context);

        Call<NewsModel> call = api.getCategorisedNews(countryCode, category, apiKey);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(@NonNull Call<NewsModel> call, @NonNull Response<NewsModel> response) {


                if (!response.isSuccessful())
                    return;


                if (response.body() == null)
                    return;


                String firstNewsTitle = response.body().getData().get(0).getTitle();
                if (firstNewsTitle == null)
                    return;
                if (firstNewsTitle.equals("null"))
                    return;
                if (firstNewsTitle.equals(""))
                    return;
                if (newsDbHelper.getAllNews(tableName).size() != 0) {
                    String lastNews = newsDbHelper.getLastNews(tableName);
                    if (lastNews.equals(firstNewsTitle)) {
                        count = 1;
                    } else {
                        newsList = Operations.AddOperation(response.body().getData());
                        count = 0;
                    }
                } else {
                    newsList = Operations.AddOperation(response.body().getData());
                    count = 0;
                }
                if (count == 0) {
                    for (int i = 0; i < newsList.size(); i++) {
                        NewsModel.articles news = newsList.get(i);
                        newsDbHelper.insertNews(tableName, news.getTitle(), news.getDescription(), news.getNewsUrl(), news.getImageUrl(), news.getDate());
                    }
                } else {
                    if (newsDbHelper.getAllNews(tableName).size() == 0)
                        return;
                }
                newsList.clear();
                newsList = Operations.ShowDataFromDatabase(tableName, context);
                Operations.SetAdapter(context, recycler, newsList);
            }

            @Override
            public void onFailure(@NonNull Call<NewsModel> call, @NonNull Throwable t) {

                if (newsDbHelper.getAllNews(tableName).size() != 0) {
                    newsList.clear();
                    newsList = Operations.ShowDataFromDatabase(tableName, context);
                    Operations.SetAdapter(context, recycler, newsList);
                } else {
                    String error = t.getMessage();
                    if (error == null)
                        return;
                    AlertDialog alertDialog;
                    if (error.equals("Unable to resolve host \"newsapi.org\": No address associated with hostname"))
                        alertDialog = new AlertDialog(context, "No Internet Connection");
                    else
                        alertDialog = new AlertDialog(context, error);
                }
            }
        });

    }

}
