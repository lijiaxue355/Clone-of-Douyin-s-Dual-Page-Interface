package com.example.douyin;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.douyin.data.model.Video;
import com.example.douyin.view.fragment.InterFragment;
import com.example.douyin.viewmodel.HomeViewModel;

import java.util.List;


public class VideoBindingAdapter {


    @BindingAdapter(value = {"imageResource","murl","list","position"},requireAll = false)
    public static void loadImage(ImageView imageView, Video video, String urls, MutableLiveData<List<Video>> list, int position) {
        Glide.with(imageView.getContext()).clear(imageView);
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        if(lp instanceof ConstraintLayout.LayoutParams){
            ConstraintLayout.LayoutParams clp = (ConstraintLayout.LayoutParams) lp;
            float ratio = video.getAspectRatio();
            if(ratio > 0f){
                clp.height = 0;
                clp.dimensionRatio = String.format("1:%.5f", ratio);
                imageView.setLayoutParams(clp);
            } else {
                clp.height = 0;
                clp.dimensionRatio = "1:1";
                imageView.setLayoutParams(clp);
            }
        }

        RequestOptions opts = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .dontAnimate();

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(opts)
                .load(urls)
                .placeholder(new ColorDrawable(Color.parseColor("#E0E0E0")))
                .error(new ColorDrawable(Color.parseColor("#CCCCCC")))
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        int iw = resource.getIntrinsicWidth();
                        int ih = resource.getIntrinsicHeight();
                        if(iw > 0 && ih > 0){
                            float r = (float) ih / (float) iw;
                            video.setAspectRatio(r);
                            ViewGroup.LayoutParams lp2 = imageView.getLayoutParams();
                            if(lp2 instanceof ConstraintLayout.LayoutParams){
                                ConstraintLayout.LayoutParams clp2 = (ConstraintLayout.LayoutParams) lp2;
                                clp2.height = 0;
                                clp2.dimensionRatio = String.format( "1:%.5f", r);
                                imageView.setLayoutParams(clp2);
                                View parent = (View) imageView.getParent();
                                if(parent!=null){
                                    parent.requestLayout();
                                }
                            }
                        }
                        return false;
                    }
                })
                .into(imageView);
        int futurePosition = position +3;
        if(list!=null && list.getValue()!=null){
            if(futurePosition < list.getValue().size()){
                for (int i = position + 1; i <= futurePosition; i++) {
                    Glide.with(imageView.getContext())
                            .load(list.getValue().get(i).getVideoFirstImgUrl())
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .skipMemoryCache(false)
                            .preload();
                }
            }
        }

    }



    @BindingAdapter("imageUrlWithRatio")
    public static void loadImageWithRatio(ImageView imageView, String url){
        Glide.with(imageView).clear(imageView);
        RequestOptions opts = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .dontAnimate();
        Glide.with(imageView)
                .setDefaultRequestOptions(opts)
                .load(url)
                .into(imageView);
    }

//    @BindingAdapter("imageUrlWithRatio")
//    public static void loadImageWithRatio(ImageView imageView, String url){
////        imageView.setImageResource(Integer.parseInt(url));
//        Glide.with(imageView).clear(imageView);
//        RequestOptions opts = new RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .skipMemoryCache(false)
//                .dontAnimate();
//        Glide.with(imageView)
//                .setDefaultRequestOptions(opts)
//                .load(url)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
////                        int iw = resource.getIntrinsicWidth();
//                        int ih = resource.getIntrinsicHeight();
//                        if( ih > 0){
//                            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
//                            if(lp instanceof ConstraintLayout.LayoutParams){
//                                ConstraintLayout.LayoutParams clp = (ConstraintLayout.LayoutParams) lp;
//                                 clp.height = ih;
//                                 imageView.setLayoutParams(clp);
//                            }
//                        }
//                        return false;
//                    }
//                })
//                .into(imageView);
//    }
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
