package com.example.xiaoli.amusementpark.entity;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.entity
 *    文件名：    NickNameEvent
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/12  12:37
 *    描述：       昵称改变的事件
 */

public class NickNameEvent {
    private String nickname;

    public NickNameEvent(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
