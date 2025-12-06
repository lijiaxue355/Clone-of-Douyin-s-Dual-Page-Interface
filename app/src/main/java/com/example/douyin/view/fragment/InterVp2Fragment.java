package com.example.douyin.view.fragment;

import static androidx.fragment.app.FragmentManager.TAG;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.example.douyin.R;
import com.example.douyin.Utils;
import com.example.douyin.data.model.Video;
import com.example.douyin.databinding.InterVp2FragmentBinding;
import com.example.douyin.viewmodel.HomeViewModel;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class InterVp2Fragment extends Fragment{
    InterVp2FragmentBinding binding;
    private ExoPlayer player;
    private HomeViewModel viewModel;
    private Video video;
    private String url;
    private Boolean playerState = false;
    GestureDetector gestureDetector;
    private int position;
    private Handler handler = new Handler(Looper.getMainLooper());
//    public InterVp2Fragment(Video video) {
//        this.video = video;
//    }

    public InterVp2Fragment(int position) {
        this.position = position;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(player==null){
            player = new ExoPlayer.Builder(requireContext()).build();
            binding.playerview.setPlayer(player);
            player.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
            url = video.getVideoUrl();
            if(url != null){
                player.setMediaItem(MediaItem.fromUri(Uri.parse(url)));
                player.prepare();
            }
        }
        binding.setVideo(video);
        setGesture();
        if(playerState){
            player.play();
        }
        super.onViewCreated(view, savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.inter_vp2_fragment,container,false);
        viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        if(viewModel.getMutVideoList().getValue()!=null){
            video = viewModel.getMutVideoList().getValue().get(position);
        }
        return binding.getRoot();
    }

    public void setPlayerState(boolean playerState){
        this.playerState = playerState;
        Log.d("swyxhljx","position:"+position+"playstate"+playerState);
        if(playerState){
            if(player!=null){
                Log.d("swyxhljx","position:"+position+"isplaying");
                player.seekTo(0);
                player.play();
            }
            else{
                Log.d("swyxhljx","null");
            }
        }
        else{
            if(player!=null){
                player.pause();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(player!=null){
            binding.playerview.setPlayer(null);
            player.release();
            player = null;
        }
        binding = null;
    }
    private void setGesture() {

        if(gestureDetector==null){
            gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDoubleTap(@NonNull MotionEvent e) {
                    String newlike = "";
                    if(!video.isLike()){
                        newlike = viewModel.addLikeCount(position);
                        video.setLike(newlike);
                        binding.setVideo(video);
                    }
                    binding.ivDoublelike.setVisibility(View.VISIBLE);
                    viewModel.updataLikeState(position,true);
                    handler.postDelayed(runnable,600);
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
                    if(playerState){
                        binding.ivPause.setVisibility(View.VISIBLE);
                        playerState = false;
                        player.pause();
                    }
                    else{
                        binding.ivPause.setVisibility(View.GONE);
                        playerState = true;
                        player.play();
                    }
                    return super.onSingleTapConfirmed(e);
                }
            });
        }
        binding.playerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            Animation animator = AnimationUtils.loadAnimation(getContext(),R.anim.doublelike_scale);
//            Animation animators = AnimationUtils.loadAnimation(getContext(),R.anim.doublelike_alpha);
//            binding.ivDoublelike.setAnimation(animator);
//            binding.ivDoublelike.setAnimation(animators);
//            animator.start();
//            animators.start();
            //使用set
            AnimationSet animatorSet = new AnimationSet(true);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1,2,1,2,100,100);
            scaleAnimation.setDuration(300);
            AlphaAnimation animation = new AlphaAnimation(1,0);
            animation.setDuration(300);

            animatorSet.addAnimation(scaleAnimation);
            animatorSet.addAnimation(animation);

            binding.ivDoublelike.startAnimation(animatorSet);

            binding.ivDoublelike.setVisibility(View.GONE);
        }
    };
}
