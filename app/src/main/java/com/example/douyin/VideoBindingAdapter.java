package com.example.douyin;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.douyin.view.fragment.InterFragment;
import com.example.douyin.viewmodel.HomeViewModel;


public class VideoBindingAdapter {
    @BindingAdapter("imageResource")
    public static void loadImage(ImageView imageView,String url) {
        Log.d("ljxxhswy", "url:" + url);
        Glide.with(imageView).clear(imageView);
        Glide.with(imageView.getContext())
                .load(url)

                .into(imageView);

    }
    @BindingAdapter("visibility")
    public static void visibilityToGone(View view,Boolean vis){
        if(vis){
            view.setVisibility(View.VISIBLE);
        }
        else{
            view.setVisibility(View.GONE);
        }
    }


}
