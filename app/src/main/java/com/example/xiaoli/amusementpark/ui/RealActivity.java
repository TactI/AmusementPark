package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    RealActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/23  13:17
 *    描述：       认证界面
 */

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.NumberEvent;
import com.example.xiaoli.amusementpark.entity.UserRequest;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.ShareUtils;
import com.example.xiaoli.amusementpark.utils.ToastUtils;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;

import org.greenrobot.eventbus.EventBus;

public class RealActivity extends BaseActivity implements View.OnClickListener {
    private String user_name;
    private ImageView iv_back;
    private TextView title_bar;
    private EditText et_real_number;
    private EditText et_real_name;
    private Button btn_up_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certification_layout);
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
        String user_info=ShareUtils.getString(this,"user_info","");
        Gson gson=new Gson();
        UserRequest.UserBean userBean=gson.fromJson(user_info, UserRequest.UserBean.class);
        user_name=userBean.getUser_name();
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        title_bar= (TextView) findViewById(R.id.title_bar);
        title_bar.setText("身份认证");
        et_real_name= (EditText) findViewById(R.id.et_real_name);
        et_real_number= (EditText) findViewById(R.id.et_real_number);
        btn_up_number= (Button) findViewById(R.id.btn_up_number);
        btn_up_number.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String real_name=et_real_name.getText().toString();
        String user_number=et_real_number.getText().toString();
        switch (view.getId()){
            case R.id.btn_up_number:
                if (!real_name.equals("") && !user_number.equals("")){
                    if (user_number.length()==18){
                        HttpParams params=new HttpParams();
                        params.put("user_name",user_name);
                        params.put("user_number",user_number);
                        params.put("real_name",real_name);
                        RxVolley.post("http://120.25.249.201/sql/real.php", params, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                L.e(t);
                                //解析数据
                                parseJson(t);
                            }

                            @Override
                            public void onFailure(VolleyError error) {
                                ToastUtils.showToastShort(getApplicationContext(),"网络错误!");
                            }
                        });

                    }else{
                        ToastUtils.showToastShort(this,"身份证号码位数错误!");
                    }
                }else{
                    ToastUtils.showToastShort(this,"输入框不能为空!");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void parseJson(String t) {
        Gson gson=new Gson();
        UserRequest userRequest=gson.fromJson(t,UserRequest.class);
        if (userRequest.getResultCode()==200){
            //解析用户数据
            UserRequest.UserBean userBaen=userRequest.getDatas();
            Gson gson2=new Gson();
            String user_info=gson2.toJson(userBaen);
            ShareUtils.putString(this,"user_info",user_info);
            ShareUtils.putBoolean(this,"is_real",true);
            EventBus.getDefault().post(new NumberEvent());
            ToastUtils.showToastShort(this,"认证成功!");
            finish();
        }else if (userRequest.getResultCode()==1){
            ToastUtils.showToastShort(this,"认证失败!");
        }else if (userRequest.getResultCode()==0){
            ToastUtils.showToastShort(this,"未收到数据!");
        }
    }
}
