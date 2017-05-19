package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    WebViewActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/19  12:19
 *    描述：       景点介绍
 */

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.view.CustomDialog;

import net.frakbot.jumpingbeans.JumpingBeans;

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private TextView title_bar;
    private ImageView iv_back;
    private Toolbar mtoolbar;
    private WebView mWebView;
    private CustomDialog dialog;
    private TextView tv_load;
    private JumpingBeans jumpingBeans;
    private String url="http://120.25.249.201/html/tj.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
    }

    private void initView() {
        //弹出框
        dialog=new CustomDialog(this, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,R.layout.load_dialog,R.style.Theme_dialog,
                Gravity.CENTER,R.style.pop_anim_style);
        dialog.setCancelable(false);
        tv_load= (TextView) dialog.findViewById(R.id.tv_load);
        jumpingBeans=JumpingBeans.with(tv_load).appendJumpingDots().build();
        dialog.show();
        title_bar= (TextView) findViewById(R.id.title_bar);
        title_bar.setText("上海欢乐谷");
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        mtoolbar= (Toolbar) findViewById(R.id.mtoolbar);
        mtoolbar.setBackgroundResource(R.color.blue2);

        mWebView= (WebView) findViewById(R.id.mWebView);
        //支持JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //控制器
        mWebView.getSettings().setBuiltInZoomControls(true);
        //接口回掉
        mWebView.setWebChromeClient(new WebViewClient());
        //加载网页
        mWebView.loadUrl(url);
        //本地显示
        mWebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                //我接受这个事件
                return true;
            }
        });
    }
    public class WebViewClient extends WebChromeClient {
        //进度变化的监听
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress==100){
                dialog.dismiss();
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
