package com.example.xiaoli.amusementpark.fragment;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.fragment
 *    文件名：    MainFragment
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/16  16:44
 *    描述：       首页
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.UserRequest;
import com.example.xiaoli.amusementpark.ui.AboutActivity;
import com.example.xiaoli.amusementpark.ui.MyDetailActivity;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.PicassoUtils;
import com.example.xiaoli.amusementpark.utils.ShareUtils;
import com.example.xiaoli.amusementpark.utils.ToastUtils;
import com.example.xiaoli.amusementpark.view.CustomDialog;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;


import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderFragment extends Fragment implements View.OnClickListener {
    private LinearLayout mLinearlayout;
    private CircleImageView circleview;
    private TextView user_id,tv_nickname;
    private StringBuffer user_name;
    private String user_nickname,imageurl;
    //版本号,url
    private int versionCode;
    private String url;
    //更新界面
    private CustomDialog dialog;
    //4个TextView
    private TextView order,continue_order,order_go,order_write;
    //我的足迹等
    private RelativeLayout mRelative1,mRelative2,mRelative3;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.order_fragment,null);
        findView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ShareUtils.getBoolean(getActivity(),"isUp",false)){
            String user_info=ShareUtils.getString(getActivity(),"user_info","");
            Gson gson=new Gson();
            UserRequest.UserBean userBean=gson.fromJson(user_info, UserRequest.UserBean.class);
            PicassoUtils.loadImageView(getActivity(),userBean.getIcon_url(),circleview);
            tv_nickname.setText("昵称:"+userBean.getNick_name());
            ShareUtils.putBoolean(getActivity(),"isUp",false);
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String user_info=ShareUtils.getString(getActivity(),"user_info","");
                    Gson gson=new Gson();
                    UserRequest.UserBean userBean=gson.fromJson(user_info, UserRequest.UserBean.class);
                    user_name=new StringBuffer(userBean.getUser_name());
                    user_name=user_name.replace(3,7,"****");
                    imageurl=userBean.getIcon_url();
                    user_nickname=userBean.getNick_name();
                    PicassoUtils.loadImageViewHolder(getActivity(),imageurl+"?"+(Math.random()*100),R.drawable.timg,R.drawable.error,circleview);
                    user_id.setText("用户: "+user_name);
                    tv_nickname.setText("昵称: "+user_nickname);
                    break;
            }
        }
    };
    private void findView(View view) {
        dialog=new CustomDialog(getActivity(), LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,R.layout.check_dialog,R.style.Theme_dialog,Gravity.CENTER,R.style.pop_anim_style);
        order= (TextView) view.findViewById(R.id.order);
        order.setOnClickListener(this);
        continue_order= (TextView) view.findViewById(R.id.continue_order);
        continue_order.setOnClickListener(this);
        order_go= (TextView) view.findViewById(R.id.order_go);
        order_go.setOnClickListener(this);
        order_write= (TextView) view.findViewById(R.id.order_write);
        order_write.setOnClickListener(this);
        mRelative1= (RelativeLayout) view.findViewById(R.id.mRelative1);
        mRelative1.setOnClickListener(this);
        mRelative2= (RelativeLayout) view.findViewById(R.id.mRelative2);
        mRelative2.setOnClickListener(this);
        mRelative3= (RelativeLayout) view.findViewById(R.id.mRelative3);
        mRelative3.setOnClickListener(this);
        circleview= (CircleImageView) view.findViewById(R.id.circleview);
        user_id= (TextView) view.findViewById(R.id.user_id);
        tv_nickname= (TextView) view.findViewById(R.id.tv_nickname);
        handler.sendEmptyMessage(1);
        mLinearlayout= (LinearLayout) view.findViewById(R.id.mLinearlayout);
        mLinearlayout.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.mLinearlayout:
                Intent intent=new Intent();
                intent.setClass(getActivity(),MyDetailActivity.class);
                startActivity(intent);
            break;
            case R.id.mRelative1:
                break;
            case R.id.mRelative2:
                dialog.show();
                //startActivity(new Intent(getActivity(),AboutActivity.class));
                    try {
                    getVersionCode();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                new RxVolley.Builder().shouldCache(false).cacheTime(0);
                RxVolley.get("http://120.25.249.201/sql/config.json", new HttpCallback() {
                    @Override
                    public void onFailure(VolleyError error) {
                        super.onFailure(error);
                        dialog.dismiss();
                        ToastUtils.showToastShort(getActivity(),"请求失败!");
                    }
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        L.e(t);
                        parsingJson(t);
                    }
                });
                break;
            case R.id.mRelative3:
                break;
            case R.id.order:
                break;
            case R.id.continue_order:
                break;
            case R.id.order_write:
                break;
            case R.id.order_go:
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            int code=jsonObject.getInt("versionCode");
            url=jsonObject.getString("url");
            if (code>versionCode){
                dialog.dismiss();
               showUpdateDialog(jsonObject.getString("content"));
            }else{
                dialog.dismiss();
                ToastUtils.showToastShort(getActivity(),"当前是最新版本！");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //提示dialog
    private void showUpdateDialog(String content) {
        new AlertDialog.Builder(getActivity()).setTitle("有新版本啦！").setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(getActivity(),AboutActivity.class);
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    //获取版本号
    private void getVersionCode() throws PackageManager.NameNotFoundException {
       PackageManager pm=getActivity().getPackageManager();
        PackageInfo info= pm.getPackageInfo(getActivity().getPackageName(),0);
        versionCode=info.versionCode;
    }
}
