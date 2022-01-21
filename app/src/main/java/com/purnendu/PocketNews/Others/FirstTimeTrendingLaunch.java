package com.purnendu.PocketNews.Others;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.purnendu.PocketNews.Activities.AlertDialog;
import com.purnendu.PocketNews.Activities.MainActivity;
import com.purnendu.PocketNews.Model.NewsModel;
import com.purnendu.PocketNews.R;
import com.purnendu.PocketNews.Retrofit.API_KEYS;
import com.purnendu.PocketNews.Retrofit.Api;
import com.purnendu.PocketNews.Retrofit.Client;
import com.purnendu.PocketNews.SqliteDatabase.NewsDbHelper;
import com.trncic.library.DottedProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstTimeTrendingLaunch {


    private final NewsDbHelper newsDbHelper;
    private final Context context;
    private ArrayList<NewsModel.articles> newsList;
    private final Api api;
    private DottedProgressBar dottedProgressBar;

    public FirstTimeTrendingLaunch(Context context) {

        this.context = context;
        newsDbHelper = new NewsDbHelper(context);
        newsList = new ArrayList<>();
        api = Client.getRetrofitInstance().create(Api.class);
    }

    public void UpToDateDatabase() {

        setDottedProgressBar();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String countryCode = sharedPreferences.getString("country", "in");


        Call<NewsModel> call = api.getTrendingNews(countryCode, API_KEYS.firstKey);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(@NonNull Call<NewsModel> call, @NonNull retrofit2.Response<NewsModel> response) {

                if (!response.isSuccessful())
                    return;


                if (response.body() == null)
                    return;

                final String TABLE_NAME = "trending";
                if (newsDbHelper.getAllNews(TABLE_NAME).size() != 0) {
                    newsList.clear();
                    String lastNewsTitle = newsDbHelper.getLastNews(TABLE_NAME);
                    String firstNewsTitle = response.body().getData().get(0).getTitle();
                    if (firstNewsTitle == null)
                        return;
                    if (!(lastNewsTitle.equals(firstNewsTitle)))
                        newsList = Operations.AddOperation(response.body().getData());
                    else {
                        Intent i = new Intent(context, MainActivity.class);
                        context.startActivity(i);
                    }
                } else {
                    newsList = Operations.AddOperation(response.body().getData());
                }
                for (int i = 0; i < newsList.size(); i++) {
                    NewsModel.articles news = newsList.get(i);
                    newsDbHelper.insertNews(TABLE_NAME, news.getTitle(), news.getDescription(), news.getNewsUrl(), news.getImageUrl(), news.getDate());
                }
                dottedProgressBar.stopProgress();
                //Lunching MainActivity
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
            }

            @Override
            public void onFailure(@NonNull Call<NewsModel> call, @NonNull Throwable t) {
                dottedProgressBar.stopProgress();
                //Lunching MainActivity
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
            }
        });

    }

    private void setDottedProgressBar() {
        dottedProgressBar = ((Activity) context).findViewById(R.id.dotProgress);
        dottedProgressBar.setVisibility(View.VISIBLE);
        dottedProgressBar.startProgress();
    }

    public void LoadFromDatabase(String countryCode, final RecyclerView recycler, final String tableName) {
        if (newsDbHelper.getAllNews(tableName).size() != 0) {
            newsList.clear();
            newsList = Operations.ShowDataFromDatabase(tableName, context);
            Operations.SetAdapter(context, recycler, newsList);
        } else if (newsDbHelper.getAllNews(tableName).size() == 0) {
            newsList.clear();

            Call<NewsModel> call = api.getTrendingNews(countryCode, API_KEYS.firstKey);
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

                    if ((!firstNewsTitle.equals("null")) && (!firstNewsTitle.equals("")))
                        newsList = Operations.AddOperation(response.body().getData());
                    for (int i = 0; i < newsList.size(); i++) {
                        NewsModel.articles news = newsList.get(i);
                        newsDbHelper.insertNews(tableName, news.getTitle(), news.getDescription(), news.getNewsUrl(), news.getImageUrl(), news.getDate());
                    }
                    newsList.clear();
                    newsList = Operations.ShowDataFromDatabase(tableName, context);
                    Operations.SetAdapter(context, recycler, newsList);

                }

                @Override
                public void onFailure(@NonNull Call<NewsModel> call, @NonNull Throwable t) {
                    String error = t.getMessage();
                    if (error == null)
                        return;
                    AlertDialog alertDialog;
                    if (error.equals("Unable to resolve host \"newsapi.org\": No address associated with hostname"))
                        alertDialog = new AlertDialog(context, "No Internet Connection");
                    else
                        alertDialog = new AlertDialog(context, error);

                }
            });

        }
    }

}
