package com.example.xiaoli.amusementpark.utils;
/*
 *    项目名：    SmartButler
 *    包名：      com.xiaoli.myapplication.utils
 *    文件名：    L
 *    创建者：    XiaoLi
 *    创建时间：  2017/1/14  21:12
 *    描述：       Log封装类
 */

import android.util.Log;

public class L {
    //开关
    public static final boolean DEBUG=true;
    //TAG
    public static final String TAG="AmusementPark";
    //五个等级 DIWE
    public static void d(String text){
        if (DEBUG){
            Log.d(TAG,text);
        }
    }
    public static void i(String text){
        if (DEBUG){
            Log.i(TAG,text);
        }
    }
    public static void w(String text){
        if (DEBUG){
            Log.w(TAG,text);
        }
    }
    public static void e(String text){
        if (DEBUG){
            Log.e(TAG,text);
        }
    }
}
