package com.example.douyin.view.fragment;

import static androidx.fragment.app.FragmentManager.TAG;
import static androidx.media3.common.C.TIME_UNSET;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.cache.CacheDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.ui.PlayerView;

import com.example.douyin.R;
import com.example.douyin.Utils;
import com.example.douyin.data.model.Comments;
import com.example.douyin.data.model.Video;
import com.example.douyin.databinding.InterVp2FragmentBinding;
import com.example.douyin.generated.callback.OnClickListener;
import com.example.douyin.view.adapts.CommentRecyclerAdapter;
import com.example.douyin.viewmodel.HomeViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class InterVp2Fragment extends Fragment {
    InterVp2FragmentBinding binding;
    private ExoPlayer player;
    private HomeViewModel viewModel;
    private Video video;
    private String url;
    private Boolean playerState = false;
    GestureDetector gestureDetector;
    private int position;
    ObjectAnimator animator;
    BottomSheetBehavior<View> behavior;
    CommentRecyclerAdapter adapter;
    List<Video> oldlist;

    private Handler handler = new Handler(Looper.getMainLooper());
//    public InterVp2Fragment(Video video) {
//        this.video = video;
//    }

    public InterVp2Fragment(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.inter_vp2_fragment, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        if (viewModel.getMutVideoList().getValue() != null) {
            video = viewModel.getMutVideoList().getValue().get(position);
            binding.setVideo(video);
        }
        return binding.getRoot();
    }

    @UnstableApi
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (player == null) {
            DefaultHttpDataSource.Factory httpFactory = new DefaultHttpDataSource.Factory()
                    .setAllowCrossProtocolRedirects(true);
            CacheDataSource.Factory factory = new CacheDataSource.Factory()
                    .setCache(HomeViewModel.getSimpleCheched(getActivity().getApplicationContext()))
                    .setUpstreamDataSourceFactory(httpFactory)
                    .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
            DefaultMediaSourceFactory sourceFactory = new DefaultMediaSourceFactory(factory);
            player = new ExoPlayer.Builder(requireContext())
                    .setMediaSourceFactory(sourceFactory)
                    .build();
            binding.playerview.setPlayer(player);
            player.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
            url = video.getVideoUrl();
            if (url != null) {
                MediaItem current = MediaItem.fromUri(Uri.parse(url));
                MediaItem next = null;
                if (viewModel.getMutVideoList().getValue() != null) {
                    int nextPosition = position + 1;
                    if (nextPosition < viewModel.getMutVideoList().getValue().size()) {
                        String nextUrl = viewModel.getMutVideoList().getValue().get(nextPosition).getVideoUrl();
                        if (nextUrl != null) {
                            next = MediaItem.fromUri(Uri.parse(nextUrl));
                        }
                    }
                }
                if (next != null) {
                    List<MediaItem> list = new LinkedList<>();
                    list.add(current);
                    list.add(next);
                    player.setMediaItems(list, 0, TIME_UNSET);
                } else {
                    player.setMediaItem(current);
                }
                player.prepare();
            }
        }
        setGesture();

        behavior = BottomSheetBehavior.from(binding.consComment);
        //设置hidden时高度
        behavior.setPeekHeight(0);
        //设置状态
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        //设置可以隐藏状态
        behavior.setHideable(true);
        //设置当影响变为全屏或者全屏的时候，直接，不用有一部分弹的提示
        behavior.setSkipCollapsed(true);
        //用户可以手拖动
        behavior.setDraggable(true);
        //很重要，不根据内容来尽可能的靠近屏幕顶部
        behavior.setFitToContents(false);
        //这个是设置评论区最顶部到屏幕最顶部的距离
        int expandedOffset = (int) (getResources().getDisplayMetrics().heightPixels * 0.35f);
        behavior.setExpandedOffset(expandedOffset);
        adapter = new CommentRecyclerAdapter();
         if(video!=null){
             Log.d("ljxtylyh","notnull");
             if (video.getCommits() != null) {
                 adapter.setLists(video.getCommits());
             } else {
                 adapter.setLists(new LinkedList<>());
             }
         } else {
             adapter.setLists(new LinkedList<>());
         }

        androidx.recyclerview.widget.LinearLayoutManager lm = new androidx.recyclerview.widget.LinearLayoutManager(getContext());
        lm.setOrientation(androidx.recyclerview.widget.LinearLayoutManager.VERTICAL);
        binding.rvComments.setLayoutManager(lm);
        binding.rvComments.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.ivPinglunInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });


        binding.ivLikeInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(video.isLike()){
                    viewModel.updataLikeState(position,false);
                }
                else{
                    viewModel.updataLikeState(position,true);
                }
            }
        });

        binding.ivStarInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(video.isStar()){
                    viewModel.updateStarState(false,position);
                }
                else{
                    viewModel.updateStarState(true,position);
                }
            }
        });

        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                    animateVideo(false);
//                    animateBottom(true);
                    viewModel.getVp2().setValue(true);
                    viewModel.getIfRefesh().setValue(true);
                }
                else if(newState == BottomSheetBehavior.STATE_EXPANDED || newState ==
                        BottomSheetBehavior.STATE_DRAGGING ||newState ==  BottomSheetBehavior.STATE_SETTLING){
                       viewModel.getVp2().setValue(false);
                    viewModel.getIfRefesh().setValue(false);
                }
            }
            @Override
            public void onSlide(@NonNull View view, float slideOffset) {
                if(slideOffset >0){
                    viewModel.getVp2().setValue(false);
                }
            }
        });


        //当视频列表数据变化时，进行粗糙的刷新，后续使用局部刷新；
        viewModel.getMutVideoList().observe(getViewLifecycleOwner(), new Observer<List<Video>>() {
            @Override
            public void onChanged(List<Video> list) {
                if(list!=null &&!list.isEmpty()){
                    Log.d("lyhlyh",list.size()+"");
                    video =  list.get(position);
                    List<Comments> cs = video.getCommits();
                    adapter.setLists(new LinkedList<>(cs));
                    adapter.notifyDataSetChanged();
                    oldlist = list;
                    binding.setVideo(video);
                    binding.tvCommentTitle.setText((cs != null ? cs.size() : 0) + "条评论");
                }
            }
        });
         binding.etWaicommentInput.setEnabled(false);
        //键盘适应高度
        View view1 = binding.getRoot();
        View inputView = binding.consInput;

        //监听WindowInsets（包括状态栏，导航栏，键盘），当键盘高度发生变化会回调这个方法；
        ViewCompat.setOnApplyWindowInsetsListener(view1, new OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                int height = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
                inputView.animate()
                        .translationY(-height)
                        .setDuration(200)
                        .start();

                inputView.setTranslationY(-height);
                return insets;
            }
        });


        //更新光标
        binding.etCommentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.etCommentInput.post(()->{
                   binding.etCommentInput.setSelection(binding.etCommentInput.getText().length());
                });
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes = String.valueOf(binding.etCommentInput.getText());
                if(!mes.isEmpty()){
                    viewModel.updataCommentState(position,mes);
                    binding.etCommentInput.setText("");
                }
                else{
                    Toast.makeText(getContext(),"表达你的态度再评论吧",Toast.LENGTH_SHORT).show();
                }
            }
        });


//         behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//             @Override
//             public void onStateChanged(@NonNull View view, int i) {
//
//             }
//             @Override
//             public void onSlide(@NonNull View view, float v) {
//
//             }
//         });

        startMusicBegin(binding.ivMusicInter);
        if (playerState) {
            player.play();
        }
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void onResume() {
        if(player!=null){
            player.play();
        }
        binding.ivPause.setVisibility(View.GONE);
        startMusicBegin(binding.ivMusicInter);
        super.onResume();
    }

    public void setPlayerState(boolean playerState) {
        this.playerState = playerState;
        Log.d("swyxhljx", "position:" + position + "playstate" + playerState);
        if (playerState) {
            if (player != null) {
                Log.d("swyxhljx", "position:" + position + "isplaying");
                player.seekTo(0);
                player.play();
            } else {
                Log.d("swyxhljx", "null");
            }
        } else {
            if (player != null) {
                player.pause();
            }
        }
    }


    @Override
    public void onPause() {
        if (player != null) {
            player.pause();
        }
        stopMusic();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.release();
            player = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        animator=null;
        binding.playerview.setPlayer(null);
        binding = null;
    }

    private void setGesture() {

        if (gestureDetector == null) {
            gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(@NonNull MotionEvent e) {
                    if (!video.isLike()) {
                        Log.d("whywhywhy","yes");
                        viewModel.updataLikeState(position, true);
                        binding.setVideo(video);
                    }
                    binding.ivDoublelike.setVisibility(View.VISIBLE);
                    handler.postDelayed(runnable, 600);
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
                    if (playerState) {
                        binding.ivPause.setVisibility(View.VISIBLE);
                        playerState = false;
                        player.pause();
                    } else {
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
            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 2, 1, 2, 100, 100);
            scaleAnimation.setDuration(300);
            AlphaAnimation animation = new AlphaAnimation(1, 0);
            animation.setDuration(300);

            animatorSet.addAnimation(scaleAnimation);
            animatorSet.addAnimation(animation);

            binding.ivDoublelike.startAnimation(animatorSet);

            binding.ivDoublelike.setVisibility(View.GONE);
        }
    };

    public void startMusicBegin(View view) {
        if (animator == null) {
            animator = new ObjectAnimator().ofFloat(view, "rotation", 0f, 360f);
            animator.setDuration(6000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
        }
        if(!animator.isStarted()){
            animator.start();
        }

    }
    public void stopMusic(){
        if(animator!=null){
            animator.cancel();
        }
    }
}
