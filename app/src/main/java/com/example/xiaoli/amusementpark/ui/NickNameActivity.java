package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    NickNameActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/10  19:32
 *    描述：       修改昵称
 */

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.NickNameEvent;
import com.example.xiaoli.amusementpark.entity.UserRequest;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.ShareUtils;
import com.example.xiaoli.amusementpark.utils.ToastUtils;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.greenrobot.eventbus.EventBus;

public class NickNameActivity extends BaseActivity{
    private Button btn_upname;
    private TextView tv_nickname;
    private String user_name,nick_name;
    private ImageView iv_back;
    private TextView title_bar;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
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
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_bar= (TextView) findViewById(R.id.title_bar);
        title_bar.setText("修改昵称");
        String user_info= ShareUtils.getString(this,"user_info","");
        Gson gson=new Gson();
        UserRequest.UserBean userBean=gson.fromJson(user_info, UserRequest.UserBean.class);
        user_name=userBean.getUser_name();
        nick_name=userBean.getNick_name();
        btn_upname= (Button) findViewById(R.id.btn_upname);
        tv_nickname= (TextView) findViewById(R.id.tv_nickname);
        tv_nickname.setText(nick_name);
        btn_upname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_nickname.getText().length()!=0){
                    if (tv_nickname.getText().toString().trim().equals(nick_name)){
                        ToastUtils.showToastShort(getApplicationContext(),"昵称未改变!");
                    }
                    else {
                        HttpParams params=new HttpParams();
                        params.put("user_name",user_name);
                        params.put("nick_name",tv_nickname.getText().toString().trim());
                        RxVolley.post("http://120.25.249.201/sql/upname.php", params, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                Gson gson=new Gson();
                                UserRequest userRequest=gson.fromJson(t,UserRequest.class);
                                if (userRequest.getResultCode()==200){
                                    //解析用户数据
                                    UserRequest.UserBean userBaen=userRequest.getDatas();
                                    Gson gson2=new Gson();
                                    String user_info=gson2.toJson(userBaen);
                                    ShareUtils.putString(getApplicationContext(),"user_info",user_info);
                                    ToastUtils.showToastShort(getApplicationContext(),"修改成功!");
                                    ShareUtils.putBoolean(getApplicationContext(),"isUp",true);
                                    EventBus.getDefault().post(new NickNameEvent(tv_nickname.getText().toString().trim()));
                                    finish();
                                }else if (userRequest.getResultCode()==1){
                                    ToastUtils.showToastShort(getApplicationContext(),"修改失败!");
                                }else if (userRequest.getResultCode()==0){
                                    ToastUtils.showToastShort(getApplicationContext(),"未收到数据!");
                                }
                            }
                        });

                    }
                }else{
                    ToastUtils.showToastShort(getApplicationContext(),"输入框不能为空!");
                }
            }
        });
    }

}
