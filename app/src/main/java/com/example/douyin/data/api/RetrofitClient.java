package com.example.douyin.data.api;

import kotlinx.coroutines.InternalCoroutinesApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String URL= "https://m1.apifoxmock.com/m1/7473578-7208266-default/";
    private static Retrofit retrofit;
    private static IApiInterface apiService;
    public static IApiInterface getApiServiceAll (){
        if(retrofit == null){
            retrofit =  new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if(apiService == null){
            apiService = retrofit.create(IApiInterface.class);
        }
        return apiService;
    }
}
