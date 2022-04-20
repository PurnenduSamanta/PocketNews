package com.purnendu.PocketNews.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {


    @GET("top-headlines")
    Call<ResponseNewsModel> getCategorisedNews(@Query("country") String countryCode,
                                               @Query("category") String category,
                                               @Query("apiKey") String API_KEY);


    @GET("everything")
    Call<ResponseNewsModel> getEverythingFromSearch(@Query("q") String keyWord,
                                                    @Query("apiKey") String API_KEY);

}
