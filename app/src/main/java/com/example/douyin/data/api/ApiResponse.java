package com.example.douyin.data.api;

import androidx.lifecycle.Lifecycle;

import com.example.douyin.data.model.Video;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    //响应实体类
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String messsage;
    @SerializedName("data")
    private List<Video> data;

    public ApiResponse() {
    }

    public ApiResponse(int code, List<Video> data, String messsage) {
        this.code = code;
        this.data = data;
        this.messsage = messsage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Video> getData() {
        return data;
    }

    public void setData(List<Video> data) {
        this.data = data;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
