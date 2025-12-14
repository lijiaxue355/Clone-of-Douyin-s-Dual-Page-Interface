package com.example.douyin.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.douyin.MainActivity;
import com.example.douyin.R;
import com.example.douyin.data.model.Video;
import com.example.douyin.databinding.FirspageShowVp2Binding;
import com.example.douyin.view.adapts.HomeRecyclerAdapter;
import com.example.douyin.viewmodel.HomeViewModel;

import java.util.LinkedList;
import java.util.List;

// 说明：首页外流列表页面：负责展示首页瀑布流、绑定数据与跳转内流；
public class RecyFragment extends Fragment {
    FirspageShowVp2Binding binding;
    HomeViewModel homeViewModel;
    HomeRecyclerAdapter adapter;
    InterFragment interFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.firspage_show_vp2, container, false);
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(homeViewModel);
        homeViewModel.getIfProgress().setValue(true);
        // 首次拉取视频列表数据
        homeViewModel.firstVideoList();
        adapter = new HomeRecyclerAdapter();
        // 观察视频列表 LiveData，使用 DiffUtil 进行最小刷新
        homeViewModel.getMutVideoList().observe(getViewLifecycleOwner(), new Observer<List<Video>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Video> videos) {
                if (videos != null && !videos.isEmpty()) {
                    binding.refresh.setRefreshing(false);
                    Log.d("lyhlyhlyh", videos.get(0).isLike() + " ");
                    adapter.updataAdapter(videos);
                    adapter.setLists(videos, RecyFragment.this, binding.recyShowFirstpage);
                    homeViewModel.getIfProgress().setValue(false);
                }
            }
        });
        // 下拉刷新：触发仓库重新请求
        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homeViewModel.refreshVideoList();
            }
        });
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        List<Item> list = new LinkedList<>();
//        for (int i = 0; i < 5; i++) {
//            list.add(new Item("精品小猫",R.drawable.newmao,"100万","爱猫者",R.drawable.title4));
//            list.add(new Item("好看的帽子",R.drawable.newmaozi,"10万","爱帽者",R.drawable.title4));
//        }

        // 初始化瀑布流布局管理器与适配器绑定
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        binding.recyShowFirstpage.setLayoutManager(layoutManager);
        binding.recyShowFirstpage.setAdapter(adapter);
        binding.recyShowFirstpage.setItemAnimator(null);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // 点击首页卡片，跳转到内流 Fragment
    public void setHomeToInterFragment(View view, int position) {
        if (homeViewModel == null) {
            homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        }
        homeViewModel.getMutPosition().setValue(position);

        interFragment = new InterFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, interFragment).addToBackStack(null).commit();
    }

    @Override
    public void onStart() {
        Window window = getActivity().getWindow();
        window.setStatusBarColor(Color.WHITE);
        super.onStart();
    }
}
