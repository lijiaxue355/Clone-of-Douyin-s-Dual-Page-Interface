package com.example.douyin.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.douyin.MainActivity;
import com.example.douyin.R;
import com.example.douyin.data.model.Video;
import com.example.douyin.databinding.FirspageShowVp2Binding;
import com.example.douyin.view.adapts.HomeRecyclerAdapter;
import com.example.douyin.viewmodel.HomeViewModel;

import java.util.LinkedList;
import java.util.List;

public class RecyFragment extends Fragment {
     FirspageShowVp2Binding binding;
    HomeViewModel homeViewModel;
    HomeRecyclerAdapter adapter;
    InterFragment interFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.firspage_show_vp2,container,false);
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        binding.setLifecycleOwner(this);
        adapter = new HomeRecyclerAdapter();
        homeViewModel.firstVideoList();
        homeViewModel.getMutVideoList().observe(getViewLifecycleOwner(), new Observer<List<Video>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Video> videos) {
                adapter.setLists(videos, RecyFragment.this,binding.recyShowFirstpage);
                adapter.notifyDataSetChanged();
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

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        binding.recyShowFirstpage.setLayoutManager(layoutManager);
        binding.recyShowFirstpage.setAdapter(adapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void setHomeToInterFragment(int position){
        Log.d("ljxxhgswy","yes");
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        if(homeViewModel==null){
            homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        }
        homeViewModel.getMutPosition().setValue(position);

            interFragment = new InterFragment();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main,interFragment).addToBackStack(null).commit();
    }
}
