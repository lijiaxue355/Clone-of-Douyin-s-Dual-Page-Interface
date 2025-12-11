package com.example.douyin.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Video {

    @SerializedName("id")
    private int id;

    @SerializedName("videoUrl")
    private String videoUrl;

    @SerializedName("anthorImgUrl")
    private String anthorImgUrl;

    @SerializedName("anthorName")
    private String anthorName;

    @SerializedName("videoContent")
    private String videoContent;

    @SerializedName("videoFirstImgUrl")
    private String videoFirstImgUrl;

    @SerializedName("like")
    private String like;

    @SerializedName("comment")
    private String comment;

    @SerializedName("stars")
    private String stars;

    @SerializedName("toOthor")
    private String toOthor;

    @SerializedName("musicImg")
    private String musicImg;

    @SerializedName("isLike")
    private boolean isLike;
    @SerializedName("isStar")
    private boolean isStar;
    @SerializedName("commits")
    private List<Comments> commits;




    private Runnable runnable;
    private int chchedHeight = -1;
    private  int titleHeight = -1;
    private float aspectRatio = 0f;
    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }

    public Video(String anthorImgUrl, String anthorName, float aspectRatio,  String comment, List<Comments> commits, int id, boolean isLike, boolean isStar, String like, String musicImg, String stars, int titleHeight, String toOthor, String videoContent, String videoFirstImgUrl, String videoUrl) {
        this.anthorImgUrl = anthorImgUrl;
        this.anthorName = anthorName;
        this.aspectRatio = aspectRatio;

        this.comment = comment;
        this.commits =commits;
        this.id = id;
        this.isLike = isLike;
        this.isStar = isStar;
        this.like = like;
        this.musicImg = musicImg;

        this.stars = stars;
        this.titleHeight = titleHeight;
        this.toOthor = toOthor;
        this.videoContent = videoContent;
        this.videoFirstImgUrl = videoFirstImgUrl;
        this.videoUrl = videoUrl;
    }

    public Video() {
    }

    public int getTitleHeight() {
        return titleHeight;
    }

    public void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
    }

    public List<Comments> getCommits() {
        return commits;
    }

    public void setCommits(List<Comments> commits) {
        this.commits = commits;
    }

    public int getChchedHeight() {
        return chchedHeight;
    }

    public void setChchedHeight(int chchedHeight) {
        this.chchedHeight = chchedHeight;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getAnthorImgUrl() {
        return anthorImgUrl;
    }

    public void setAnthorImgUrl(String anthorImgUrl) {
        this.anthorImgUrl = anthorImgUrl;
    }

    public String getAnthorName() {
        return anthorName;
    }

    public void setAnthorName(String anthorName) {
        this.anthorName = anthorName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLike() {
        return isLike;
    }
    public void setIsLike(boolean isLike){
        this.isLike = isLike;
    }
    public void setLike(boolean like) {
        isLike = like;
    }


    public String getMusicImg() {
        return musicImg;
    }

    public void setMusicImg(String musicImg) {
        this.musicImg = musicImg;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLike() {
        return like;
    }

    public Video setLike(String like) {
        this.like = like;
        return null;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getToOthor() {
        return toOthor;
    }

    public void setToOthor(String toOthor) {
        this.toOthor = toOthor;
    }

    public String getVideoContent() {
        return videoContent;
    }

    public void setVideoContent(String videoContent) {
        this.videoContent = videoContent;
    }

    public String getVideoFirstImgUrl() {
        return videoFirstImgUrl;
    }

    public void setVideoFirstImgUrl(String videoFirstImgUrl) {
        this.videoFirstImgUrl = videoFirstImgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
