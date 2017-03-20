package com.example.xiaoli.amusementpark.utils;
/*
 *    项目名：    AmusementPark
 *    包名：      com.xiaoli.myapplication.utils
 *    文件名：    ShareUtils
 *    创建者：    XiaoLi
 *    创建时间：  2017/1/15  20:07
 *    描述：      SharedPreferences的封装
 */

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils {
    public static final String  NAME="congig";
    //键  值
    public static void putString(Context mContext,String key,String value){
        SharedPreferences sp= mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    //键  默认值
    public static String getString(Context mContext,String key,String defValue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    //键  值
    public static void putInt(Context mContext,String key,int value){
        SharedPreferences sp= mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
    //键  默认值
    public static int getInt(Context mContext, String key, int defValue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    //键  值
    public static void putBoolean(Context mContext,String key,Boolean value){
        SharedPreferences sp= mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
    //键  默认值
    public static boolean getBoolean(Context mContext, String key, Boolean defValue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }
    //删除单个
    public static void deleShare(Context mContext,String key){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
    //删除全部
    public static void deleAll(Context mContext){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
