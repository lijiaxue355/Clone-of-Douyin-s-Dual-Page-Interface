package com.example.douyin.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.douyin.R;
import com.example.douyin.databinding.FirspageFragmentBinding;
import com.example.douyin.view.adapts.HomeVp2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {
    FirspageFragmentBinding binding;
    List<Fragment> fragmentList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.firspage_fragment,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        fragmentList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            RecyFragment fragment = new RecyFragment();
            fragmentList.add(fragment);
        }
        HomeVp2Adapter adapter = new HomeVp2Adapter(getActivity(),fragmentList);
        binding.vp2Fpf.setAdapter(adapter);

        new TabLayoutMediator(binding.tabShowfFpf, binding.vp2Fpf, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
               switch (i){
                   case 0:
                       tab.setText("经验");
                       break;
                   case 1:
                       tab.setText("热点");
                       break;
                   case 2:
                       tab.setText("直播");
                       break;
                   case 3:
                       tab.setText("团购");
                       break;
                   case 4:
                       tab.setText("西安");
                       break;
                   case 5:
                       tab.setText("精选");
                       break;
                   case 6:
                       tab.setText("关注");
                       break;
                   case 7:
                       tab.setText("商城");
                       break;
                   case 8:
                       tab.setText("推荐");
                       break;
               }
            }
        }).attach();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
