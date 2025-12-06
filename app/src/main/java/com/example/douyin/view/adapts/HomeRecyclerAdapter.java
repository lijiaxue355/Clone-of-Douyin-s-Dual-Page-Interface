package com.example.douyin.view.adapts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.douyin.R;
import com.example.douyin.data.model.Video;
import com.example.douyin.databinding.ItemFirstpageShowRecyBinding;
import com.example.douyin.view.fragment.InterFragment;
import com.example.douyin.view.fragment.RecyFragment;


import java.util.LinkedList;
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {
    List<Video> list = new LinkedList<>();
    RecyFragment fragment;
    RecyclerView recyclerView;
    public HomeRecyclerAdapter() {
    }

    public void  setLists(List<Video> list, RecyFragment fragment,RecyclerView rv) {
        this.list = list;
        this.fragment =  fragment;
        this.recyclerView = rv;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFirstpageShowRecyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_firstpage_show_recy,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.binding.setItem(list.get(position));
       holder.binding.setPosition(position);
       holder.binding.setRecyfragment(fragment);
//       recyclerView.invalidateItemDecorations();
    }

    @Override
    public int getItemCount() {
        return list==null?0: list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
          ItemFirstpageShowRecyBinding binding;


          public MyViewHolder( ItemFirstpageShowRecyBinding binding) {
              super(binding.getRoot());
              this.binding = binding;
          }

      }
}
