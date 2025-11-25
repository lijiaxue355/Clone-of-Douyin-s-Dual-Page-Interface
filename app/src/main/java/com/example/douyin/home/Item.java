package com.example.douyin.home;

public class Item {
    private String name;
    private int img;
    private String content;
    private int titleimg;
    private String like;

    public Item(String content, int img, String like, String name, int titleimg) {
        this.content = content;
        this.img = img;
        this.like = like;
        this.name = name;
        this.titleimg = titleimg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTitleimg() {
        return titleimg;
    }

    public void setTitleimg(int titleimg) {
        this.titleimg = titleimg;
    }
}
