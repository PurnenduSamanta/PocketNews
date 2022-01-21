package com.purnendu.PocketNews.Others;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.purnendu.PocketNews.Activities.AlertDialog;
import com.purnendu.PocketNews.Adapters.CustomAdapter;
import com.purnendu.PocketNews.Model.NewsModel;
import com.purnendu.PocketNews.Retrofit.API_KEYS;
import com.purnendu.PocketNews.Retrofit.Api;
import com.purnendu.PocketNews.Retrofit.Client;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchingKeyword {


    private ArrayList<NewsModel.articles> newsList;
    private final Api api;

    public SearchingKeyword() {
        newsList = new ArrayList<>();
        api = Client.getRetrofitInstance().create(Api.class);
    }

    public void fetch(String keyWord, final RecyclerView recycler, final Context context) {


        Call<NewsModel> call = api.getEverythingFromSearch(keyWord, API_KEYS.secondKey);

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(@NonNull Call<NewsModel> call, @NonNull Response<NewsModel> response) {

                if (!response.isSuccessful())
                    return;

                if (response.body() == null)
                    return;

                if (response.body().getData().isEmpty()) {
                    String message = "No results found";
                    AlertDialog alertDialog = new AlertDialog(context, message);
                } else {
                    newsList = Operations.AddOperation(response.body().getData());
                    Collections.reverse(newsList);
                    CustomAdapter customAdapter = new CustomAdapter(context, newsList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    recycler.setLayoutManager(layoutManager);
                    recycler.setAdapter(customAdapter);
                }
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
