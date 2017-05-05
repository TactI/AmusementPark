package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    SplashActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/9  14:16
 *    描述：       闪屏页
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.xiaoli.amusementpark.MainActivity;
import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.utils.ShareUtils;
import com.example.xiaoli.amusementpark.utils.StaticClass;
import com.example.xiaoli.amusementpark.utils.UtilTools;

public class SplashActivity extends AppCompatActivity{
    /**
     * 1.设置延时 2000ms
     * 2.判断是否第一次运行
     * 3.自定义字体
     * 4.Activity全屏主题
     */
    private TextView tv_splash;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否第一次运行
                    if (isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else{
                        //判断是否登陆成功过
                        if (ShareUtils.getBoolean(SplashActivity.this,"isLogin",false)){
                            startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        }
                        else {
                            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        }
                    }
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }
    //初始化View
    private void initView() {
        //设置延时 2秒
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        tv_splash= (TextView) findViewById(R.id.tv_splash);
        //设置字体
        UtilTools.setFont1(this,tv_splash);
    }
    //判断程序是否第一次运行0
    private  Boolean isFirst(){
        Boolean isFirst= ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if (isFirst){
            //标记已经运行过app
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else{
            return false;
        }
    }
    //点击无法返回
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
