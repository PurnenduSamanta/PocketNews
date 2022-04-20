package com.purnendu.PocketNews.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {


    private static Retrofit retrofit;

    public static String BASE_URL = "https://newsapi.org/v2/";

    public  static  Retrofit getRetrofitInstance()
    {

        if(retrofit==null)
        {
            retrofit=new  Retrofit.Builder()
                    .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit;
    }
}
