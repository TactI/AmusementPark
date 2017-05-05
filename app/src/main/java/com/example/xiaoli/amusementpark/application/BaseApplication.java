package com.example.xiaoli.amusementpark.application;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.application
 *    文件名：    BaseApplication
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/9  14:18
 *    描述：       TODO.
 */

import android.app.Application;

import com.example.xiaoli.amusementpark.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;

public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
        //初始化Rxvolley
        //new RxVolley.Builder().httpMethod(RxVolley.Method.POST).shouldCache(false);
    }
}
