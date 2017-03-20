package com.example.xiaoli.amusementpark.utils;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.utils
 *    文件名：    UtilTools
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/9  14:39
 *    描述：       TODO.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.TextView;

public class UtilTools{
    //设置字体
    public static void setFont1(Context mContext, TextView textView){
        Typeface typeface= Typeface.createFromAsset(mContext.getAssets(),"fonts/YYG.TTF");
        textView.setTypeface(typeface);
    }
    public static void setFont2(Context mContext, TextView textView){
        Typeface typeface= Typeface.createFromAsset(mContext.getAssets(),"fonts/HWXW.TTF");
        textView.setTypeface(typeface);
    }
    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
}
