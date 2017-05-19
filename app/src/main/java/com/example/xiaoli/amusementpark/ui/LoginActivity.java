package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    LoginActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/9  14:46
 *    描述：       登录页面
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.MainActivity;
import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.User;
import com.example.xiaoli.amusementpark.entity.UserRequest;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.ShareUtils;
import com.example.xiaoli.amusementpark.utils.UtilTools;
import com.example.xiaoli.amusementpark.view.CustomProgress;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;


import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ShimmerTextView tv_title;
    private Button btn_login;
    private CheckBox cb;
    private TextView tv_rest,tv_register;
    private MaterialEditText et_user,et_password;
    private CustomProgress dialog;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView() {
        dialog=new CustomProgress(getApplicationContext(),R.style.Custom_Progress);
        btn_login= (Button) findViewById(R.id.btn_login);
        tv_rest= (TextView) findViewById(R.id.tv_reset);
        tv_register= (TextView) findViewById(R.id.tv_register);
        btn_login.setOnClickListener(this);
        tv_rest.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        et_user= (MaterialEditText) findViewById(R.id.et_user);
        et_password= (MaterialEditText) findViewById(R.id.et_password);
        tv_title= (ShimmerTextView) findViewById(R.id.tv_title);
        UtilTools.setFont2(this,tv_title);
        Shimmer shimmer=new Shimmer();
        shimmer.setDuration(3000).setDirection(Shimmer.ANIMATION_DIRECTION_LTR);
        shimmer.start(tv_title);
        cb= (CheckBox) findViewById(R.id.cb);
        //设置选中状态
        boolean isChecked= ShareUtils.getBoolean(this,"keeppass",false);
        cb.setChecked(isChecked);
        if(isChecked){
            //设置密码
            et_user.setText(ShareUtils.getString(this,"name",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_reset:
                startActivity(new Intent(this,ResetPasswordActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.btn_login:
                final String phone=et_user.getText().toString().trim();
                final String pass=et_password.getText().toString().trim();
                //判断是否为空
                if(!TextUtils.isEmpty(phone) & !TextUtils.isEmpty(pass)){
                    dialog.show(this, false, null);
                    //post请求简洁版实现
                    HttpParams params = new HttpParams();
                    params.put("user_name",phone);
                    params.put("user_password",pass);
                    RxVolley.post("http://120.25.249.201/sql/login.php?", params, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            super.onSuccess(t);
                            Gson gson=new Gson();
                            UserRequest userRequest=gson.fromJson(t,UserRequest.class);
                            if (userRequest.getResultCode()==200){
                                dialog.del();
                                ShareUtils.putBoolean(LoginActivity.this,"isLogin",true);
                                //跳转
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                //解析用户数据
                                getUserInfo(userRequest);
                            }else if (userRequest.getResultCode()==2){
                                dialog.del();
                                et_user.setError("密码错误!");
                            }else if (userRequest.getResultCode()==1){
                                dialog.del();
                                et_user.setError("用户不存在!");
                            }
                        }
                    });
                }else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getUserInfo(UserRequest userRequest) {
        UserRequest.UserBean userBaen=userRequest.getDatas();
        Gson gson=new Gson();
        String user_info=gson.toJson(userBaen);
        ShareUtils.putString(this,"user_info",user_info);
    }

    //假设我输入用户和密码，但不点击登陆，直接退出
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolean(this,"keeppass",cb.isChecked());
        //是否记住密码
        if(cb.isChecked()){
            //记住用户名和密码
            ShareUtils.putString(this,"name",et_user.getText().toString().trim());
            ShareUtils.putString(this,"password",et_password.getText().toString().trim());
        }else{
            ShareUtils.deleShare(this,"name");
            ShareUtils.deleShare(this,"password");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent("exitApp");
            intent.putExtra("closeAll", 1);
            sendBroadcast(intent);//发送广播
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
