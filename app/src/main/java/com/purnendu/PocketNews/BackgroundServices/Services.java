package com.purnendu.PocketNews.BackgroundServices;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.purnendu.PocketNews.Activities.MainActivity;
import com.purnendu.PocketNews.Model.NewsModel;
import com.purnendu.PocketNews.Notifications.NotificationHelper;
import com.purnendu.PocketNews.Others.Operations;
import com.purnendu.PocketNews.Retrofit.API_KEYS;
import com.purnendu.PocketNews.Retrofit.Api;
import com.purnendu.PocketNews.Retrofit.Client;
import com.purnendu.PocketNews.SqliteDatabase.NewsDbHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Services extends IntentService {

    public Services() {
        super("hello");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        boolean connected = Operations.isNetworkConnected(getApplicationContext());
        NewsDbHelper newsDbHelper = new NewsDbHelper(getApplicationContext());
        String tableName = "trending";
        if ((newsDbHelper.getAllNews(tableName).size() != 0) && (connected)) {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String countryCode = sharedPreferences.getString("country", "in");

            Api api = Client.getRetrofitInstance().create(Api.class);
            Call<NewsModel> call = api.getTrendingNews(countryCode, API_KEYS.firstKey);
            call.enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(@NonNull Call<NewsModel> call, @NonNull Response<NewsModel> response) {

                    if (!response.isSuccessful())
                        return;

                    if (response.body() == null)
                        return;

                    NewsModel.articles article = response.body().getData().get(0);
                    String title = article.getTitle();
                    String icon = article.getImageUrl();

                    if (title == null)
                        return;

                    if (icon == null)
                        return;

                    if (((article.getTitle().equals("null")) || (article.getTitle().equals("")))) {
                        title = "Time to check your daily doze";
                    }
                    if (((article.getImageUrl().equals("null")) || (article.getImageUrl().equals("")))) {
                        icon = "NoImage";
                    }
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext(), title, icon, i, 100);
                }

                @Override
                public void onFailure(@NonNull Call<NewsModel> call, @NonNull Throwable t) {

                }
            });

        }
    }


}
