package com.purnendu.PocketNews.Retrofit;

import com.purnendu.PocketNews.Model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("top-headlines")
    Call<NewsModel> getTrendingNews(@Query("country") String countryCode,
                                    @Query("apiKey") String API_KEY);

    @GET("top-headlines")
    Call<NewsModel> getCategorisedNews(@Query("country") String countryCode,
                                       @Query("category") String category,
                                       @Query("apiKey") String API_KEY);


    @GET("everything")
    Call<NewsModel> getEverythingFromSearch(@Query("q") String keyWord,
                                            @Query("apiKey") String API_KEY);

}
