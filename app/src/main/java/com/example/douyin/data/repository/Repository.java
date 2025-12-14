package com.example.douyin.data.repository;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.douyin.data.api.ApiResponse;
import com.example.douyin.data.api.IApiInterface;
import com.example.douyin.data.api.RetrofitClient;
import com.example.douyin.data.model.Comments;
import com.example.douyin.data.model.Video;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//   数据仓库
//   通过 Retrofit 请求接口，成功后写入 MutableLiveData 供 UI 观察
//   提供首次加载与刷新两类列表获取
public class Repository {
    private List<Video> list = new LinkedList<>();
    private IApiInterface apiService;
    MutableLiveData<List<Video>> mutableLiveData = new MutableLiveData();

    public Repository() {
        apiService = RetrofitClient.getApiServiceAll();
    }

    // 首次加载视频列表（异步请求，结果写入 LiveData）
    public MutableLiveData<List<Video>> getMutVideoList() {


        apiService.getList().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    list = apiResponse.getData();
                    Log.d("ljxxhswy", list.size() + "d");
                    mutableLiveData.setValue(list);
                } else {
                    Log.d("ljxxhswy", list.size() + "");
                    mutableLiveData.setValue(new LinkedList<>());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("ljxxhswy", list.size() + "fail");
                mutableLiveData.setValue(new LinkedList<>());
            }
        });
        Log.d("ljxxhswy", "li");
        return mutableLiveData;
    }

    // 刷新视频列表（异步请求，结果写入 LiveData）
    public MutableLiveData<List<Video>> getRefushMutVideoList() {
        apiService.getRefushList().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    list = apiResponse.getData();
                    Log.d("ljxxhswy", list.size() + "d");
                    mutableLiveData.setValue(list);
                } else {
                    Log.d("ljxxhswy", list.size() + "");
                    mutableLiveData.setValue(new LinkedList<>());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("ljxxhswy", list.size() + "fail");
                mutableLiveData.setValue(new LinkedList<>());
            }
        });
        Log.d("ljxxhswy", "li");
        return mutableLiveData;
    }

    public List<Video> getList() {
        return list;
    }


}
