package com.example.xiaoli.amusementpark.entity;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.entity
 *    文件名：    WeatherBean
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/9  19:03
 *    描述：       天气的实体类
 */

public class WeatherBean {
    private String week;
    private String weather;
    private String tempearture;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTempearture() {
        return tempearture;
    }

    public void setTempearture(String tempearture) {
        this.tempearture = tempearture;
    }
}
