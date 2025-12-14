package com.example.douyin.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.database.DatabaseProvider;
import androidx.media3.database.StandaloneDatabaseProvider;
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor;
import androidx.media3.datasource.cache.SimpleCache;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.douyin.R;
import com.example.douyin.Utils;
import com.example.douyin.data.model.Comments;
import com.example.douyin.data.model.Video;
import com.example.douyin.data.repository.Repository;
import com.example.douyin.view.fragment.InterFragment;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


//   统一管内，外流页面的状态与交互更新；

public class HomeViewModel extends ViewModel {
    public static final String TAG = "ljxxhswy";
    MutableLiveData<List<Video>> mutVideoList = new MutableLiveData<>();
    MutableLiveData<Boolean> ifProgress = new MutableLiveData<>();
    MutableLiveData<Integer> position = new MutableLiveData<>();
    MutableLiveData<ExoPlayer> player = new MutableLiveData<>();
    MutableLiveData<Boolean> isBNVVisable = new MutableLiveData<>();
    MutableLiveData<Boolean> vp2 = new MutableLiveData<>();
    MutableLiveData<Boolean> ifRefesh = new MutableLiveData<>();
    @UnstableApi
    private static SimpleCache simpleCache;
    private Repository repository;
    public HomeViewModel() {
        this.repository = new Repository();
    }

    // 获取视频列表
    public MutableLiveData<List<Video>> getMutVideoList(){
        if(mutVideoList == null) {
            mutVideoList = new MutableLiveData<>();
        }
        return mutVideoList;
    }
    //因为底部表单和vp2的下滑有滑动冲突：
    // 在底部出现时，通过共享的viewModel来传递数据，通知Vp2所在的碎片类vp2的状态
    //最后改变vp2的可滑动状态
    //这里获得vp2状态的Livedata实例；
    public MutableLiveData<Boolean> getVp2(){
        if(vp2 == null) {
            vp2 = new MutableLiveData<>();
        }
        return vp2;
    }
    //是否在刷新；
    public MutableLiveData<Boolean> getIfRefesh(){
        if(ifRefesh == null) {
            ifRefesh = new MutableLiveData<>();
        }
        return ifRefesh;
    }
    //现在的位置，记录当前内部页面的位置
    public MutableLiveData<Integer> getMutPosition(){
        if(position == null) {
            position = new MutableLiveData<>();
        }
        return position;
    }
    //是否在加载；
    public MutableLiveData<Boolean> getIfProgress(){
        if(ifProgress == null){
            ifProgress = new MutableLiveData<>();
        }
        return ifProgress;
    }
    //原来的计划：只使用一个exoplayer实例，现在已经改，故注释掉：
//    public MutableLiveData<ExoPlayer> getMutPlayer(){
//        if(player == null) {
//            player = new MutableLiveData<>();
//        }
//        return player;
//    }
    //因为到内流，你的BottomNagavitionView不可见，这里实现对其的控制；
    public MutableLiveData<Boolean> getIsBNVVisable(){
        if(isBNVVisable == null) {
            isBNVVisable = new MutableLiveData<>();
        }
        return isBNVVisable;
    }
    // 第一次加载数据：从仓库异步拉取并返回 LiveData
    public void firstVideoList(){
//        List<Video> list =  repository.getMutVideoList().getValue();
//        mutVideoList.setValue(list);
        mutVideoList = repository.getMutVideoList();
    }
    //之前写的方案：
//    @Override
//    protected void onCleared() {
//        super.onCleared();
//        if(player.getValue()!=null){
//            player.getValue().release();
//        }
//    }
    // 计算点赞后的展示数值（不直接落库，只返回新值）
    // 最后决定通过state更新
//    public String addLikeCount(int position){
//
//        if(mutVideoList.getValue()!=null && !mutVideoList.getValue().isEmpty()
//                && position>=0 && position<mutVideoList.getValue().size()){
//            String oldlike = mutVideoList.getValue().get(position).getLike();
//            String newLike = Utils.addLikes(oldlike);
//            return newLike;
//        }
//        return "";
//    }
//    // 计算取消点赞后的展示数值（不直接落库，只返回新值）
//    public String subLikeCount(int position){
//        if(mutVideoList.getValue()!=null && !mutVideoList.getValue().isEmpty()
//                && position>=0 && position<mutVideoList.getValue().size()){
//            String oldlike = mutVideoList.getValue().get(position).getLike();
//            String newLike = Utils.jianLikes(oldlike);
//            return newLike;
//        }
//
//        return "";
//    }
    // 更新点赞状态与数值：复制视频对象与列表，替换对应位置后 setValue
    public void  updataLikeState(int position,Boolean newState){
        List<Video> old = mutVideoList.getValue();
        if(old!=null && !old.isEmpty() && position>=0 && position<old.size()){
            Video src = old.get(position);
            String base = src.getLike();
            String newLike = newState ? Utils.addLikes(base) : Utils.jianLikes(base);
            Video v = copyVideo(src);
            v.setIsLike(newState);
            v.setLike(newLike);
            List<Video> newList = new LinkedList<>(old);
            newList.set(position, v);
            mutVideoList.setValue(newList);
        }
    }
    // 计算收藏后的展示数值（不直接落库，只返回新值）
    //已废弃
//    public String addStartCount(int position){
//        if(mutVideoList.getValue()!=null && !mutVideoList.getValue().isEmpty()
//                && position>=0 && position<mutVideoList.getValue().size()){
//            String oldlike = mutVideoList.getValue().get(position).getStars();
//            String newLike = Utils.addLikes(oldlike);
//            return newLike;
//        }
//
//        return "";
//    }
//    // 计算取消收藏后的展示数值（不直接落库，只返回新值）
//    public String subStartCount(int position){
//       if(mutVideoList.getValue()!=null && !mutVideoList.getValue().isEmpty()
//               && position>=0 && position<mutVideoList.getValue().size()){
//            String oldlike = mutVideoList.getValue().get(position).getStars();
//            String newLike = Utils.jianLikes(oldlike);
//            return newLike;
//        }
//        return "";
//    }

    // 更新收藏状态与数值：复制视频对象与列表后提交新列表
    public void updateStarState(Boolean like,int position){
        List<Video> old = mutVideoList.getValue();
        if(old!=null && !old.isEmpty() && position>=0 && position<old.size()){
            Video src = old.get(position);
            String base = src.getStars()==null||src.getStars().isEmpty()?"0":src.getStars();
            String newStars = like ? Utils.addLikes(base) : Utils.jianLikes(base);
            Video v = copyVideo(src);
            v.setStar(like);
            v.setStars(newStars);
            List<Video> newList = new LinkedList<>(old);
            newList.set(position, v);
            mutVideoList.setValue(newList);
        }
    }
    // 新增评论：复制评论列表，在首位插入后提交新列表
    public void  updataCommentState(int position,String mes){
        List<Video> old = mutVideoList.getValue();
        if(old!=null && !old.isEmpty() && position>=0 && position<old.size()){
            Video src = old.get(position);
            int id = src.getCommits()==null?0:src.getCommits().size()+2;
            Comments comments = new Comments("0",mes, id,false,
                    "西安","ljx"," 刚刚", "http://t6y43599b.hb-bkt.clouddn.com/title/gittitle.jpg");
            Video v = copyVideo(src);
            List<Comments> newComments;
            if(src.getCommits() == null){
                newComments = new LinkedList<>(new LinkedList<>());
            }
            else{
                newComments = new LinkedList<>(src.getCommits());
            }
            newComments.add(0,comments);
            v.setCommits(newComments);
            List<Video> newList = new LinkedList<>(old);
            newList.set(position, v);
            mutVideoList.setValue(newList);
        }
    }

    private Video copyVideo(Video s){
        Video v = new Video();
        v.setId(s.getId());
        v.setVideoUrl(s.getVideoUrl());
        v.setAnthorImgUrl(s.getAnthorImgUrl());
        v.setAnthorName(s.getAnthorName());
        v.setVideoContent(s.getVideoContent());
        v.setVideoFirstImgUrl(s.getVideoFirstImgUrl());
        v.setLike(s.getLike());
        v.setComment(s.getComment());
        v.setStars(s.getStars());
        v.setToOthor(s.getToOthor());
        v.setMusicImg(s.getMusicImg());
        v.setIsLike(s.isLike());
        v.setStar(s.isStar());
        v.setCommits(s.getCommits());
        v.setChchedHeight(s.getChchedHeight());
        v.setTitleHeight(s.getTitleHeight());
        v.setAspectRatio(s.getAspectRatio());
        return v;
    }
    //记得传applocationcontext，否则万一内存泄漏
    @UnstableApi
    // 初始化并复用 ExoPlayer 的 SimpleCache（设置缓存目录与逐出策略）
    public static SimpleCache getSimpleCheched(Context context){
        if(simpleCache == null){
            File file = new File(context.getCacheDir(),"video_cache");
            LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(500 * 1024 * 1024);
            DatabaseProvider databaseProvider = new StandaloneDatabaseProvider(context);
            simpleCache = new SimpleCache(file,evictor,databaseProvider);
        }
        return simpleCache;

    }
    public void refreshVideoList(){
        mutVideoList = repository.getRefushMutVideoList();
    }
}
