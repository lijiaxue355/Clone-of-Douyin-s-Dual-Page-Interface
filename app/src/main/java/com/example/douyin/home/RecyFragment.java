package com.example.douyin.home;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.douyin.R;
import com.example.douyin.databinding.FirspageShowVp2Binding;
import com.example.douyin.home.adapts.HomeRecyclerAdapter;

import java.util.LinkedList;
import java.util.List;

public class RecyFragment extends Fragment {
     FirspageShowVp2Binding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.firspage_show_vp2,container,false);
        return binding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Item> list = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Item("精品小猫",R.drawable.newmao,"100万","爱猫者",R.drawable.title4));
            list.add(new Item("好看的帽子",R.drawable.newmaozi,"10万","爱帽者",R.drawable.title4));
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        binding.recyShowFirstpage.setLayoutManager(layoutManager);
        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(list);
        binding.recyShowFirstpage.setAdapter(adapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
