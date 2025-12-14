package com.example.douyin.data.api;

import retrofit2.Call;
import retrofit2.http.GET;

//定义retrofit2请求接口

public interface IApiInterface {
    @GET("xx/ceshi")
    Call<ApiResponse> getList();

    @GET("xx/refush")
    Call<ApiResponse> getRefushList();
}
