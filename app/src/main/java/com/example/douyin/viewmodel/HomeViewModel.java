package com.example.douyin.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.douyin.Utils;
import com.example.douyin.data.model.Video;
import com.example.douyin.data.repository.Repository;
import com.example.douyin.view.fragment.InterFragment;

import java.util.List;


public class HomeViewModel extends ViewModel {
    public static final String TAG = "ljxxhswy";
    MutableLiveData<List<Video>> mutVideoList = new MutableLiveData<>();
    MutableLiveData<Boolean> ifProgress = new MutableLiveData<>();
    MutableLiveData<Integer> position = new MutableLiveData<>();
    MutableLiveData<ExoPlayer> player = new MutableLiveData<>();
    MutableLiveData<Boolean> isBNVVisable = new MutableLiveData<>();

    private Repository repository;
    public HomeViewModel() {
        this.repository = new Repository();
    }

    public MutableLiveData<List<Video>> getMutVideoList(){
        if(mutVideoList == null) {
            mutVideoList = new MutableLiveData<>();
        }
        return mutVideoList;
    }

    public MutableLiveData<Integer> getMutPosition(){
        if(position == null) {
            position = new MutableLiveData<>();
        }
        return position;
    }
    public MutableLiveData<ExoPlayer> getMutPlayer(){
        if(player == null) {
            player = new MutableLiveData<>();
        }
        return player;
    }
    public MutableLiveData<Boolean> getIsBNVVisable(){
        if(isBNVVisable == null) {
            isBNVVisable = new MutableLiveData<>();
        }
        return isBNVVisable;
    }
    //第一次加载数据时
    public void firstVideoList(){
//        List<Video> list =  repository.getMutVideoList().getValue();
//        mutVideoList.setValue(list);
        mutVideoList = repository.getMutVideoList();
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        if(player.getValue()!=null){
            player.getValue().release();
        }
    }
    public String addLikeCount(int position){

        String oldlike = mutVideoList.getValue().get(position).getLike();
        String newLike = Utils.addLikes(oldlike);
        mutVideoList.getValue().get(position).setLike(newLike);
        mutVideoList.setValue(mutVideoList.getValue());
        return newLike;
    }
    public String subLikeCount(int position){

        String oldlike = mutVideoList.getValue().get(position).getLike();
        String newLike = Utils.jianLikes(oldlike);
        mutVideoList.getValue().get(position).setLike(newLike);
        mutVideoList.setValue(mutVideoList.getValue());
        return newLike;
    }
    public void  updataLikeState(int position,Boolean newState){
         mutVideoList.getValue().get(position).setIsLike(newState);
         mutVideoList.setValue(mutVideoList.getValue());


    }


}
