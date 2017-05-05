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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.DataEvent;
import com.example.xiaoli.amusementpark.ui.MyDetailActivity;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.PicassoUtils;
import com.example.xiaoli.amusementpark.utils.ShareUtils;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderFragment extends Fragment{
    private LinearLayout mLinearlayout;
    private CircleImageView circleview;
    private TextView user_id,tv_nickname;
    private String local_name;
    private StringBuffer user_name;
    private String user_nickname,imageurl;
    private MyThread myThread=new MyThread();
    private static final int RESULT_CANCELED = 0;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.order_fragment,null);
        findView(view);
        return view;
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    ShareUtils.putString(getActivity(),"md_username", String.valueOf(user_name));
                    ShareUtils.putString(getActivity(),"user_nickname",user_nickname);
                    ShareUtils.putString(getActivity(),"imageurl",imageurl);
                    if (!ShareUtils.getBoolean(getActivity(),"isUp",false)){
                        PicassoUtils.loadImageView(getActivity(),imageurl,circleview);
                    }
                    else {
//                        Uri uri=Uri.parse(imageurl);
//                        Picasso.with(getActivity()).invalidate(uri);
                        PicassoUtils.loadImageViewNoCache(getActivity(),imageurl,circleview);
                    }
                    ShareUtils.putBoolean(getActivity(),"isUp",false);
                    user_id.setText("用户: "+user_name);
                    tv_nickname.setText("昵称: "+user_nickname);
                    break;
            }
        }
    };
    private void findView(View view) {
        //new RxVolley.Builder().httpMethod(RxVolley.Method.POST).shouldCache(false);
        local_name=ShareUtils.getString(getActivity(),"user_name","");
        circleview= (CircleImageView) view.findViewById(R.id.circleview);
        user_id= (TextView) view.findViewById(R.id.user_id);
        tv_nickname= (TextView) view.findViewById(R.id.tv_nickname);
        myThread.run();
        //initData();
        mLinearlayout= (LinearLayout) view.findViewById(R.id.mLinearlayout);
        mLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("md_username",ShareUtils.getString(getActivity(),"md_username",""));
                intent.putExtra("user_nickname",ShareUtils.getString(getActivity(),"user_nickname",""));
                intent.putExtra("imageurl",ShareUtils.getString(getActivity(),"imageurl",""));
                intent.setClass(getActivity(),MyDetailActivity.class);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
            }
        });
    }

//    private void initData() {
//        PicassoUtils.loagImageViewSize(getActivity(),imageurl,100,100,circleview);
//        user_id.setText("用户: "+user_name);
//        tv_nickname.setText("昵称: "+user_nickname);
//    }

    class MyThread extends Thread implements Runnable{
        @Override
        public void run() {
            HttpParams params = new HttpParams();
            params.put("user_name",local_name);
            RxVolley.post("http://120.25.249.201/sql/getphoto.php",params, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    L.e(t);
                    try {
                        JSONObject jsonObject=new JSONObject(t);
                        user_name=new StringBuffer(jsonObject.getString("user_name"));
                        user_name=user_name.replace(3,7,"****");
                        user_nickname=jsonObject.getString("nick_name");
                        imageurl=jsonObject.getString("url");
                        handler.sendEmptyMessage(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onFailure(VolleyError error) {
                    Toast.makeText(getActivity(),"网络连接失败!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==Activity.RESULT_FIRST_USER){
            if (resultCode==RESULT_CANCELED){
                if (ShareUtils.getBoolean(getActivity(),"isUp",false)){
                    myThread.run();
                }
            }
        }
    }
}
