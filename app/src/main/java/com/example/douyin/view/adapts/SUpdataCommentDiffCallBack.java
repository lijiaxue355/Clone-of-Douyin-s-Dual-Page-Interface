package com.example.douyin.view.adapts;

import androidx.recyclerview.widget.DiffUtil;

import com.example.douyin.data.model.Comments;

import java.util.LinkedList;
import java.util.List;

public class SUpdataCommentDiffCallBack extends DiffUtil.Callback {
    List<Comments> oldList = new LinkedList<>();
    List<Comments> newList;

    public SUpdataCommentDiffCallBack(List<Comments> newList, List<Comments> oldList) {
        this.newList = newList;
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
    //我的思路是  如果外部的视频变化了，直接更新整个列表， 如果外部的视频没有变化， 那么评论区直接去比较局部，所以这里直接返回true了；
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getCommentId() == newList.get(newItemPosition).getCommentId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getComment().equals(newList.get(newItemPosition).getComment())
                && oldList.get(oldItemPosition).getCommentTime().equals(newList.get(newItemPosition).getCommentTime())
                && oldList.get(oldItemPosition).getCommentName() .equals(newList.get(newItemPosition).getCommentName())
                && oldList.get(oldItemPosition).getCommentLocation() .equals(newList.get(newItemPosition).getCommentLocation())
                && oldList.get(oldItemPosition).getComentLike() .equals(newList.get(newItemPosition).getComentLike())
                && oldList.get(oldItemPosition).isCommentIsLike()==newList.get(newItemPosition).isCommentIsLike()
                ;
    }
}
