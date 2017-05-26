package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    RegisterActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/13  17:57
 *    描述：       注册页
 */

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.User;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.StaticClass;
import com.example.xiaoli.amusementpark.utils.ToastUtils;
import com.example.xiaoli.amusementpark.utils.UtilTools;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.listener.SaveListener;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_phone,et_code;
    private ImageView iv_back;
    private TextView title_bar;
    private EditText et_password,et_confirm;
    private Button  get_send,btn_register;
    private static final int  HANDLER=1000;
    private int time=60;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER:
                    time--;
                    if (time>=0){
                        get_send.setText("("+time+")");
                        handler.sendEmptyMessageDelayed(HANDLER,1000);
                    }else{
                        get_send.setClickable(true);
                        get_send.setBackgroundResource(R.drawable.sms_shape01);
                        get_send.setText("重新发送");
                        time=60;
                        et_phone.setEnabled(true);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initBmobSms();
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
        iv_back.setOnClickListener(this);
        title_bar= (TextView) findViewById(R.id.title_bar);
        title_bar.setText("注册");
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_code= (EditText) findViewById(R.id.et_code);
        et_password= (EditText) findViewById(R.id.et_password);
        et_confirm= (EditText) findViewById(R.id.et_confirm);
        get_send= (Button) findViewById(R.id.get_send);
        get_send.setOnClickListener(this);
        btn_register= (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    private void initBmobSms() {
        BmobSMS.initialize(this,StaticClass.BMOB_APP_ID);
    }

    @Override
    public void onClick(View view) {
        final String password=et_password.getText().toString();
        final String confirm=et_confirm.getText().toString();
        switch (view.getId()){
            case R.id.get_send:
                String phone1=et_phone.getText().toString();
                String code1=et_code.getText().toString();
                if (phone1.length()==11 && UtilTools.isMobile(phone1)){
                    BmobSMS.requestSMSCode(this, phone1, "短信验证", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e==null){
                                //发送成功时，让获取验证码按钮不可点击，且为灰色
                                et_phone.setEnabled(false);
                                get_send.setClickable(false);
                                get_send.setBackgroundResource(R.drawable.sms_shape02);
                                ToastUtils.showToastShort(RegisterActivity.this, "验证码发送成功，请尽快使用");
                                //倒计时
                                handler.sendEmptyMessage(HANDLER);
                            }else {
                                ToastUtils.showToastShort(RegisterActivity.this, "验证码发送失败，请检查网络连接");
                                L.e(e.toString());
                            }
                        }
                    });
                } else{
                    ToastUtils.showToastShort(this,"请输入有效合法的手机号！");
                }
                break;
            case R.id.btn_register:
                final String phone2=et_phone.getText().toString();
                final String code2=et_code.getText().toString();
                if(TextUtils.isEmpty(code2) ||TextUtils.isEmpty(password) ||TextUtils.isEmpty(confirm)){
                    ToastUtils.showToastShort(RegisterActivity.this, "输入框不能为空!");
                }else{
                    if (!password.equals(confirm)){
                        ToastUtils.showToastShort(RegisterActivity.this, "两次输入的密码不一致!");
                    }
                    else{
                        BmobSMS.verifySmsCode(this, phone2, code2, new VerifySMSCodeListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    //注册操作
                                    User user=new User();
                                    user.setUsername(phone2);
                                    user.setPassword(password);
                                    user.signUp(new SaveListener<User>() {
                                        @Override
                                        public void done(User user, cn.bmob.v3.exception.BmobException e) {
                                            if (e==null){
                                                ToastUtils.showToastShort(RegisterActivity.this, "注册成功!");
                                                finish();
                                            }else {
                                                ToastUtils.showToastShort(RegisterActivity.this, "注册失败!");
                                            }
                                        }
                                    });
                                }
                                else {
                                    ToastUtils.showToastShort(RegisterActivity.this, "验证码错误");
                                }
                            }
                        });
                    }
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

}