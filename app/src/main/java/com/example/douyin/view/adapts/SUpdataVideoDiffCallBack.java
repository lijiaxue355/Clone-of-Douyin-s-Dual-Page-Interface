package com.example.douyin.view.adapts;

import androidx.recyclerview.widget.DiffUtil;

import com.example.douyin.data.model.Video;

import java.util.Objects;
import java.util.LinkedList;
import java.util.List;

//定义两个列表不同的差异
//  areItemsTheSame：以 id 判断是否同一项
//  areContentsTheSame：比较点赞/评论/收藏/转发等内容字段是否变化
public class SUpdataVideoDiffCallBack extends DiffUtil.Callback {
    private List<Video> oldList = new LinkedList<>();
    private List<Video> newList;

    public SUpdataVideoDiffCallBack(List<Video> newList, List<Video> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    public List<Video> getNewList() {
        return newList;
    }

    public void setNewList(List<Video> newList) {
        this.newList = newList;
    }

    public List<Video> getOldList() {
        return oldList;
    }

    public void setOldList(List<Video> oldList) {
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Video video = oldList.get(oldItemPosition);
        Video newVideo = newList.get(newItemPosition);

        return (video.isLike() == newVideo.isLike() && Objects.equals(video.getLike(), newVideo.getLike())) &&
                (Objects.equals(video.getComment(), newVideo.getComment())) &&
                (video.isStar() == newVideo.isStar() && Objects.equals(video.getStars(), newVideo.getStars())) &&
                (Objects.equals(video.getToOthor(), newVideo.getToOthor()));
    }
}
