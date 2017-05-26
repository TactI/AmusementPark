package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    ResetPasswordActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/15  13:46
 *    描述：       忘记密码页
 */

import android.content.Intent;
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

import com.example.xiaoli.amusementpark.MainActivity;
import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.User;
import com.example.xiaoli.amusementpark.entity.UserRequest;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.ShareUtils;
import com.example.xiaoli.amusementpark.utils.StaticClass;
import com.example.xiaoli.amusementpark.utils.ToastUtils;
import com.example.xiaoli.amusementpark.utils.UtilTools;
import com.google.gson.Gson;

import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_phone,et_code;
    private ImageView iv_back;
    private TextView title_bar;
    private EditText et_new;
    private Button get_send,btn_reset;
    private static final int  HANDLER=1001;
    private int time=60;
    private String text;
    //是否登录
    private Boolean islogin= ShareUtils.getBoolean(this,"isLogin",false);
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
        setContentView(R.layout.activity_resetpass);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initBmobSms();
        initView();
    }

    private void initBmobSms() {
        BmobSMS.initialize(this, StaticClass.BMOB_APP_ID);
    }

    private void initView() {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        title_bar= (TextView) findViewById(R.id.title_bar);
        title_bar.setText("重置密码");
        et_phone= (EditText) findViewById(R.id.et_phone);
        if (islogin){
            String user_info=ShareUtils.getString(this,"user_info","");
            Gson gson=new Gson();
            UserRequest.UserBean userBean=gson.fromJson(user_info, UserRequest.UserBean.class);
            et_phone.setText(userBean.getUser_name());
            et_phone.setEnabled(false);
        }
        et_code= (EditText) findViewById(R.id.et_code);
        et_new= (EditText) findViewById(R.id.et_new);
        get_send= (Button) findViewById(R.id.get_send);
        get_send.setOnClickListener(this);
        btn_reset= (Button) findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.get_send:
                final String phone1=et_phone.getText().toString();
                final String code1 =et_code.getText().toString();
                if (phone1.length()==11 && UtilTools.isMobile(phone1)){
                    BmobSMS.requestSMSCode(this, phone1, "短信验证", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e==null){
                                //发送成功时，让获取验证码按钮不可点击，且为灰色
                                et_phone.setEnabled(false);
                                get_send.setClickable(false);
                                get_send.setBackgroundResource(R.drawable.sms_shape02);
                                ToastUtils.showToastShort(ResetPasswordActivity.this, "验证码发送成功，请尽快使用");
                                //倒计时
                                handler.sendEmptyMessage(HANDLER);
                            }else {
                                ToastUtils.showToastShort(ResetPasswordActivity.this, "验证码发送失败，请检查网络连接");
                            }
                        }
                    });
                } else{
                    ToastUtils.showToastShort(this,"请输入有效合法的手机号！");
                }
                break;
            case R.id.btn_reset:
                final String new_pass=et_new.getText().toString();
                final String phone2=et_phone.getText().toString();
                final String code2 =et_code.getText().toString();
                if (TextUtils.isEmpty(code2) ||TextUtils.isEmpty(new_pass) ||TextUtils.isEmpty(phone2)){
                    ToastUtils.showToastShort(ResetPasswordActivity.this, "输入框不能为空");
                }else {
                    BmobSMS.verifySmsCode(this, phone2, code2, new VerifySMSCodeListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                BmobQuery<User> bmobQuery=new BmobQuery<User>();
                                bmobQuery.addWhereEqualTo("username",phone2);
                                bmobQuery.findObjects(new FindListener<User>() {
                                    @Override
                                    public void done(List<User> list, cn.bmob.v3.exception.BmobException e) {
                                        if (e==null){
                                            text=list.get(0).getObjectId();
                                            User user=new User();
                                            user.setPassword(new_pass);
                                            user.update(text, new UpdateListener() {
                                                @Override
                                                public void done(cn.bmob.v3.exception.BmobException e) {
                                                    if (e==null){
                                                        if (islogin){
                                                            ToastUtils.showToastLong(ResetPasswordActivity.this,"更新成功,请重新登录!");
                                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                        }else {
                                                            ToastUtils.showToastLong(ResetPasswordActivity.this,"更新成功!");
                                                            finish();
                                                        }
                                                    }else{
                                                        L.e(e.toString());
                                                        L.e(text);
                                                        ToastUtils.showToastShort(ResetPasswordActivity.this,"更新失败");
                                                    }
                                                }
                                            });
                                        }else{
                                            ToastUtils.showToastShort(ResetPasswordActivity.this,"该用户不存在！");
                                        }
                                    }
                                });
                            }else{
                                ToastUtils.showToastShort(ResetPasswordActivity.this,"验证码错误");
                            }
                        }
                    });
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
