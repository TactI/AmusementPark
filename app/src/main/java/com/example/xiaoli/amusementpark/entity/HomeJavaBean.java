package com.example.xiaoli.amusementpark.entity;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.entity
 *    文件名：    HomeJavaBean
 *    创建者：    XiaoLi
 *    创建时间：  2017/4/28  20:40
 *    描述：       TODO.
 */

public class HomeJavaBean {
    private int image;
    private String text;
    private int color;

    public HomeJavaBean(int image, String text, int color) {
        this.image = image;
        this.text = text;
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
