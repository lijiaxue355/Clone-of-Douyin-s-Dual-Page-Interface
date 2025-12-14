package com.example.douyin.view.adapts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.douyin.R;
import com.example.douyin.data.model.Comments;
import com.example.douyin.data.model.Video;
import com.example.douyin.databinding.CommentsRecyBinding;
import com.example.douyin.databinding.ItemFirstpageShowRecyBinding;
import com.example.douyin.view.fragment.RecyFragment;

import java.util.LinkedList;
import java.util.List;
 //评论区的适配器
public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.MyViewHolder> {
    public void setLists(List<Comments> list) {
        this.list = list;
    }

    List<Comments> list = new LinkedList<>();
    CommentsRecyBinding binding;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comments_recy, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.binding.setComments(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CommentsRecyBinding binding;

        public MyViewHolder(CommentsRecyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public void updataAdapter(List<Comments> list) {

        SUpdataCommentDiffCallBack sUpdataCommentDiffCallBack = new SUpdataCommentDiffCallBack(list, this.list);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(sUpdataCommentDiffCallBack);
        this.list = list;
        diffResult.dispatchUpdatesTo(this);

    }
}
