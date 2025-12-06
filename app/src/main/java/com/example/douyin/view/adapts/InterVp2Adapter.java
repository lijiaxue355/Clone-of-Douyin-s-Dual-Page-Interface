package com.example.douyin.view.adapts;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.DiffUtil;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.douyin.data.model.Video;
import com.example.douyin.view.fragment.InterFragment;
import com.example.douyin.view.fragment.InterVp2Fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InterVp2Adapter extends FragmentStateAdapter {
//    List<InterVp2Fragment> list;
    List<Video> videoList;
    Map<Integer,Fragment> fragmentMap = new HashMap<>();
    public InterVp2Adapter(@NonNull FragmentActivity fragmentActivity, List<Video> list) {
        super(fragmentActivity);
        this.videoList = list;
        initFragmentlist();
    }

    private void initFragmentlist() {
        for (int i = 0; i < videoList.size(); i++) {
            InterVp2Fragment fragment = new InterVp2Fragment(i);
            fragmentMap.put(i,fragment);
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("swyaljx",position+"incf");
        return fragmentMap.get(position);
    }

    @Override
    public int getItemCount() {
        return videoList== null ? 0: videoList.size();
    }
    public void  updataAdapter(List<Video> list){

       SUpdataVideoDiffCallBack diffCallBack = new SUpdataVideoDiffCallBack(this.videoList,list);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack);
        this.videoList = list;
        diffResult.dispatchUpdatesTo(this);
    }
    public Fragment getAtFragment(int position){
        Log.d("swyaljx",position+"inat");

        return fragmentMap.get(position);
    }



}
