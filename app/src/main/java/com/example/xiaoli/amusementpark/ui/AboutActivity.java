package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    AboutActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/23  15:27
 *    描述：       软件更新
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

public class AboutActivity extends BaseActivity{
    private TextView title_bar;
    private ImageView iv_back;
    public static final int HANDLER_LOADING=1001;
    public static final int HANDLER_OK=1002;
    public static final int HANDLER_NO=1003;
    private TextView tv_size;
    private String url,path;
    //进度条
    private NumberProgressBar number_progress_bar;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER_LOADING:
                    Bundle bundle=msg.getData();
                    long transferredBytes= bundle.getLong("transferredBytes");
                    long totalSize=bundle.getLong("totalSize");
                    tv_size.setText((float)(transferredBytes/1024)+"KB"+"/"+(float)(totalSize/1024)+"KB");
                    //设置进度
                    number_progress_bar.setProgress((int)(((float)transferredBytes/(float)totalSize)*100));
                    break;
                case HANDLER_OK:
                    tv_size.setText("下载成功");
                    //启动这个apk
                    startInstallApk();
                    break;
                case HANDLER_NO:
                    tv_size.setText("下载失败");
                    break;
            }
        }
    };
    //启动安装
    private void startInstallApk() {
        Intent i=new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");
        startActivity(i);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
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
        title_bar= (TextView) findViewById(R.id.title_bar);
        title_bar.setText("下载更新");
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        number_progress_bar= (NumberProgressBar) findViewById(R.id.number_progress_bar);
        tv_size= (TextView) findViewById(R.id.tv_size);
        url= getIntent().getStringExtra("url");
        L.i(url);
        path= FileUtils.getSDCardPath()+"/"+System.currentTimeMillis()+".apk";
        if (!TextUtils.isEmpty(url)){
            //下载
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    L.i("transferredBytes:" + transferredBytes + "totalSize:" + totalSize);
                    Message msg=new Message();
                    msg.what=HANDLER_LOADING;
                    Bundle bundle=new Bundle();
                    bundle.putLong("transferredBytes",transferredBytes);
                    bundle.putLong("totalSize",totalSize);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    handler.sendEmptyMessage(HANDLER_OK);
                }

                @Override
                public void onFailure(VolleyError error) {
                    handler.sendEmptyMessage(HANDLER_NO);
                }
            });
        }
    }
}
