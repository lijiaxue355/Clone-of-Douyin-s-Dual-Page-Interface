package com.example.douyin;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.douyin.databinding.ActivityMainBinding;
import com.example.douyin.view.fragment.HomeFragment;
import com.example.douyin.view.fragment.InterFragment;
import com.example.douyin.viewmodel.HomeViewModel;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    HomeFragment homeFragment;
    InterFragment interFragment;
    HomeViewModel homeViewModel;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //修改状态栏的颜色
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.WHITE);
        //初始化页面，使得进去的页面就是推荐页；
        activity = this;
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        getSupportFragmentManager().beginTransaction().addToBackStack(null).
                replace(R.id.frame_ma, homeFragment).show(homeFragment).commit();
        //初始化底部导航栏的监听，实现底部tab的跳转；
        initBottomNavagationView();
    }

    private void initBottomNavagationView() {
        binding.bnvMainactivity.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.tv_firpage_menu) {
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                    }
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).
                            replace(R.id.frame_ma, homeFragment).show(homeFragment).commit();
                } else {

                }


                return true;
            }
        });
    }
}