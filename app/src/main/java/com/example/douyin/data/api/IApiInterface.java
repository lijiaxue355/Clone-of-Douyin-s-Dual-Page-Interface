package com.example.douyin.data.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IApiInterface {
    @GET("xx/ceshi")
    Call<ApiResponse> getList();
    @GET("xx/refush")
    Call<ApiResponse> getRefushList();
}
