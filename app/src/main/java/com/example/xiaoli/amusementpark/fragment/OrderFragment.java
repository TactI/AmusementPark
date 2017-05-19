package com.example.xiaoli.amusementpark.fragment;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.fragment
 *    文件名：    MainFragment
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/16  16:44
 *    描述：       首页
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.UserRequest;
import com.example.xiaoli.amusementpark.ui.MyDetailActivity;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.PicassoUtils;
import com.example.xiaoli.amusementpark.utils.ShareUtils;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderFragment extends Fragment{
    private LinearLayout mLinearlayout;
    private CircleImageView circleview;
    private TextView user_id,tv_nickname;
    private StringBuffer user_name;
    private String user_nickname,imageurl;



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
                    PicassoUtils.loadImageViewHolder(getActivity(),imageurl,R.drawable.timg,R.drawable.error,circleview);
                    //ShareUtils.putBoolean(getActivity(),"isUp",false);
                    user_id.setText("用户: "+user_name);
                    tv_nickname.setText("昵称: "+user_nickname);
                    break;
            }
        }
    };
    private void findView(View view) {
        //new RxVolley.Builder().httpMethod(RxVolley.Method.POST).shouldCache(false);
        circleview= (CircleImageView) view.findViewById(R.id.circleview);
        user_id= (TextView) view.findViewById(R.id.user_id);
        tv_nickname= (TextView) view.findViewById(R.id.tv_nickname);
        handler.sendEmptyMessage(1);
        mLinearlayout= (LinearLayout) view.findViewById(R.id.mLinearlayout);
        mLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),MyDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
