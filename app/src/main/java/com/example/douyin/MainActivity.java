package com.example.douyin;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
        activity = this;
        initBottomNavagationView();
    }

    private void initBottomNavagationView() {
        interFragment = new InterFragment();
        getSupportFragmentManager().beginTransaction().addToBackStack(null).
                add(R.id.main,interFragment).hide(interFragment).commit();
        binding.bnvMainactivity.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.tv_firpage_menu){
                    if(homeFragment==null){
                        homeFragment = new HomeFragment();
                    }
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).
                            replace(R.id.frame_ma,homeFragment).show(homeFragment).commit();
                }
                else{

                }


                return true;
            }
        });
    }
}