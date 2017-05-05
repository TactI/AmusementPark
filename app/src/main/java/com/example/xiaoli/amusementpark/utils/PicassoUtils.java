package com.example.xiaoli.amusementpark.utils;
/*
 *    项目名：    SmartButler
 *    包名：      com.xiaoli.myapplication.utils
 *    文件名：    PicassoUtils
 *    创建者：    XiaoLi
 *    创建时间：  2017/2/8  13:53
 *    描述：      Picasso封装
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class PicassoUtils {
    //默认加载图片
    public  static void loadImageView(Context context, String imgUrl, ImageView imageView){
        Picasso.with(context).load(imgUrl).into(imageView);
    }
    //默认加载图片（指定大小）
    public static void loagImageViewSize(Context context,String imgUrl,int width,int height,ImageView imageView){
        Picasso.with(context)
                .load(imgUrl)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }
    //加载没有缓存的图片
    public  static void loadImageViewNoCache(Context context, String imgUrl, ImageView imageView){
        Picasso.with(context).load(imgUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);

    }
    //加载图片有默认图片
    public static void loagImageViewHolder(Context context,String imgUrl,int loadImg,int errorImg,ImageView imageView){
        Picasso.with(context)
                .load(imgUrl)
                .placeholder(loadImg)
                .error(errorImg)
                .into(imageView);
    }
    //裁剪图片
    public static void loagImageViewCrop(Context context,String imgUrl,ImageView imageView){
        Picasso.with(context).load(imgUrl).transform(new CropSquareTransformation()).into(imageView);
    }
    //按比例裁剪图片
    public  static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                //回收
                source.recycle();
            }
            return result;
        }

        @Override public String key() {
            return "lz"; }
    }
}
