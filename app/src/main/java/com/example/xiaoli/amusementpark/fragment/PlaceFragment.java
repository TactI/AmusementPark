package com.example.xiaoli.amusementpark.fragment;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.fragment
 *    文件名：    MainFragment
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/16  16:44
 *    描述：      景点页面
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xiaoli.amusementpark.R;

public class PlaceFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.place_fragment,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
    }
}
