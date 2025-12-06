package com.example.douyin.view.fragment;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.douyin.R;
import com.example.douyin.data.model.Video;
import com.example.douyin.databinding.InterFragmentBinding;
import com.example.douyin.view.adapts.HomeVp2Adapter;
import com.example.douyin.view.adapts.InterVp2Adapter;
import com.example.douyin.viewmodel.HomeViewModel;

import java.util.LinkedList;
import java.util.List;

public class InterFragment extends Fragment {
    public static final String TAG = "ljxxhxaz";
    InterFragmentBinding binding;
    HomeViewModel viewModel;
    List<Video> list = new LinkedList<>();
    int position;
    int prePosition;
    InterVp2Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.inter_fragment,container,false);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        viewModel.getIsBNVVisable().setValue(false);
        viewModel.getMutVideoList().observe(getViewLifecycleOwner(), new Observer<List<Video>>() {
            @Override
            public void onChanged(List<Video> videos) {
                if(videos == null){
                    binding.pb.setVisibility(View.VISIBLE);
                }
                else{
                    list = videos;
                    binding.pb.setVisibility(View.GONE);
                    if (adapter == null) {
                        adapter = new InterVp2Adapter(getActivity(),videos);
                        binding.interFragment.setAdapter(adapter);
                    }
                    else{
                        adapter.updataAdapter(videos);
                    }
                    Log.d(TAG,"hasdata");


                }
            }
        });


        binding.interFragment.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            private int prePosition = -1;
            @Override
            public void onPageSelected(int position) {
                Log.d("swyxhljx","listenposition"+position);
                //把之前的状态设置为暂停
                if(prePosition!=-1){
                    InterVp2Fragment interVp2Fragment =(InterVp2Fragment) adapter.getAtFragment(prePosition);
                    if(interVp2Fragment!=null){
                        interVp2Fragment.setPlayerState(false);
                    }
                }
                //把现在的状态改为开始播放
                InterVp2Fragment interVp2Fragments = (InterVp2Fragment) adapter.getAtFragment(position);
                if(interVp2Fragments!=null){
                    interVp2Fragments.setPlayerState(true);
                }
                prePosition = position;
                super.onPageSelected(position);
            }
        });


        // 初始化第一个播放
        binding.interFragment.post(new Runnable() {
            @Override
            public void run() {
                int position = 0;
                InterVp2Fragment interVp2Fragments;
                if(list!= null && !list.isEmpty()){

                    if( viewModel.getMutPosition().getValue()!=null){
                        position = viewModel.getMutPosition().getValue();
                    }
                    Log.d("swyaljx","position:"+position);
                    interVp2Fragments = (InterVp2Fragment) adapter.getAtFragment(position);

                    if(interVp2Fragments!=null){
                        binding.interFragment.setCurrentItem(position,false);
                    }
                }
            }
        });

//        for (int i = 0; i < viewModel.getListCount(); i++) {
//            list.add(new InterVp2Fragment());
//        }
//        InterVp2Adapter adapter = new InterVp2Adapter(getActivity(),list);
//        binding.interFragment.setAdapter(adapter);
//        if(viewModel.getMutPosition().getValue()!=null){
//            position =  viewModel.getMutPosition().getValue();
//        }
//        else {
//            position  = 0;
//        }
//
//        viewModel.getMutVideoList().observe(getViewLifecycleOwner(), new Observer<List<Video>>() {
//            @Override
//            public void onChanged(List<Video> videos) {
//                if(videos != null){
//                    viewModel.initExoPlayer();
//                }
//
//            }
//        });
//
//        binding.interFragment.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                binding.interFragment.setCurrentItem(position,true);
//            }
//        });
//        binding.interFragment.setCurrentItem(position,false);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
