package com.example.douyin.data.model;

import com.google.gson.annotations.SerializedName;

//评论区实体类
public class Comments {
    @SerializedName("commentId")
    private int commentId;
    @SerializedName("commentTitle")
    private String commentTitle;
    @SerializedName("commentName")
    private String commentName;
    @SerializedName("comment")
    private String comment;
    @SerializedName("commentTime")
    private String commentTime;
    @SerializedName("commentIsLike")
    private boolean commentIsLike;
    @SerializedName("comentLike")
    private String comentLike;
    @SerializedName("commentLocation")
    private String commentLocation;

    public Comments(String comentLike, String comment, int commentId, boolean commentIsLike, String commentLocation, String commentName, String commentTime, String commentTitle) {
        this.comentLike = comentLike;
        this.comment = comment;
        this.commentId = commentId;
        this.commentIsLike = commentIsLike;
        this.commentLocation = commentLocation;
        this.commentName = commentName;
        this.commentTime = commentTime;
        this.commentTitle = commentTitle;
    }

    public Comments() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComentLike() {
        return comentLike;
    }

    public void setComentLike(String comentLike) {
        this.comentLike = comentLike;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isCommentIsLike() {
        return commentIsLike;
    }

    public void setCommentIsLike(boolean commentIsLike) {
        this.commentIsLike = commentIsLike;
    }

    public String getCommentLocation() {
        return commentLocation;
    }

    public void setCommentLocation(String commentLocation) {
        this.commentLocation = commentLocation;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }
}
