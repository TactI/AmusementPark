package com.example.xiaoli.amusementpark.utils;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.utils
 *    文件名：    ToastUtils
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/25  21:07
 *    描述：       简单封装Toast
 */

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast mToast = null;

    public static void showToast(Context context, String text, int duration) {
        cancelToast();
        mToast = Toast.makeText(context, text, duration);
        mToast.show();
    }

    public static void showToastShort(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToastShort(Context context, int resId) {
        showToastShort(context, context.getString(resId));
    }

    public static void showToastLong(Context context, String text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    public static void showToastLong(Context context, int resId) {
        showToastLong(context, context.getString(resId));
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
