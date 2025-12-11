package com.example.douyin.view.fragment;

import android.graphics.Color;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    InterVp2Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        if(getArguments()!=null){
//            Bundle bundle = getArguments();
//            String name = bundle.getString("name");
//            binding.consTopInter.setTransitionName(name);
//        }
//        TransitionSet transitionSet = new TransitionSet()
//                .addTransition(new ChangeBounds())
//                .addTransition(new ChangeTransform())
//                .addTransition(new ChangeImageTransform())
//                .setDuration(300);
//        setSharedElementEnterTransition(transitionSet);
//        setSharedElementReturnTransition(transitionSet);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (getArguments() != null) {
//            Bundle bundle = getArguments();
//            String name = bundle.getString("name");
//            View view = null;
//            if (getActivity() != null) {
//                view = getActivity().findViewById(R.id.cv_transitionname);
//            }
//            if (view != null) {
//                view.setTransitionName(name);
//            }
//        }
//        TransitionSet transitionSet = new TransitionSet()
//                .addTransition(new ChangeBounds())
//                .addTransition(new ChangeTransform())
//                .addTransition(new ChangeImageTransform())
//                .setDuration(300);
//        setSharedElementEnterTransition(transitionSet);
//        setSharedElementReturnTransition(transitionSet);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));
        binding = DataBindingUtil.inflate(inflater, R.layout.inter_fragment, container, false);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        viewModel.getIsBNVVisable().setValue(false);

        viewModel.getMutVideoList().observe(getViewLifecycleOwner(), new Observer<List<Video>>() {
            @Override
            public void onChanged(List<Video> videos ) {

                if (videos == null || videos.isEmpty()) {
                    binding.pb.setVisibility(View.VISIBLE);
                } else {
                   binding.interrefresh.setRefreshing(false);
                    binding.pb.setVisibility(View.GONE);
                    if (adapter == null) {
                        adapter = new InterVp2Adapter(getActivity(), videos);
                        binding.interFragment.setAdapter(adapter);
                    } else {
                        boolean same = isStructureSame(list, videos);
                        if(!same){
                            Log.d("whycantrefush","same");
                            adapter.updataAdapter(videos);
                        }
                    }
                    list = videos;
                }
            }
        });

        binding.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(Color.WHITE);
                getParentFragmentManager().popBackStack();
            }
        });


        binding.interrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              viewModel.refreshVideoList();
            }

        });

        viewModel.getVp2().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.interFragment.setUserInputEnabled(aBoolean);
            }
        });
        viewModel.getIfRefesh().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.interrefresh.setEnabled(aBoolean);
            }
        });

        binding.interFragment.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            private int prePosition = -1;

            @Override
            public void onPageSelected(int position) {
                Log.d("swyxhljx", "listenposition" + position);
                if (prePosition != position) {
                    //把之前的状态设置为暂停
                    if (prePosition != -1) {
                        InterVp2Fragment interVp2Fragment = (InterVp2Fragment) adapter.getAtFragment(prePosition);
                        if (interVp2Fragment != null) {
                            interVp2Fragment.setPlayerState(false);
                        }
                    }
                    //把现在的状态改为开始播放
                    InterVp2Fragment interVp2Fragments = (InterVp2Fragment) adapter.getAtFragment(position);
                    if (interVp2Fragments != null) {
                        interVp2Fragments.setPlayerState(true);
                    }
                    prePosition = position;

                }
                super.onPageSelected(position);
            }
        });


        // 初始化第一个播放
        binding.interFragment.post(new Runnable() {
            @Override
            public void run() {
                int position = 0;
                if (list != null && !list.isEmpty()) {

                    if (viewModel.getMutPosition().getValue() != null) {
                        position = viewModel.getMutPosition().getValue();
                        Log.d("haoguiyi", viewModel.getMutPosition().getValue() + "");
                    }

                    binding.interFragment.setCurrentItem(position, false);

                }
            }
        });

        return binding.getRoot();
    }

    private boolean isStructureSame(List<Video> a, List<Video> b){
        if(a== null || a.isEmpty()){
            return true;
        }
        if(a.size()!=b.size()){
            return false;
        }
        for(int i=0;i<a.size();i++){
            if(a.get(i).getId()!=b.get(i).getId()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        if (adapter != null) {
            int curPosition = binding.interFragment.getCurrentItem();
            InterVp2Fragment curFragment = (InterVp2Fragment) adapter.getAtFragment(curPosition);
            if (curFragment != null) {
                curFragment.setPlayerState(false);
            }
        }
        binding = null;
        super.onDestroyView();
    }
}
