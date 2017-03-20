package com.example.xiaoli.amusementpark.adapter;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.adapter
 *    文件名：    TabViewAdapter
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/16  16:59
 *    描述：      ViewPager适配器
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaoli.amusementpark.R;

import java.util.ArrayList;
import java.util.List;
public class TabViewAdapter extends FragmentPagerAdapter{
    private Context mContext;
    private List<Fragment> datas=new ArrayList<>();
    private String[] titles={"首页","景点","订单"};
    private int[] image={R.drawable.home1,R.drawable.place1,R.drawable.order1};
    private LayoutInflater inflater;

    public TabViewAdapter(FragmentManager fm,List<Fragment> datas,Context mContext) {
        super(fm);
        this.datas=datas;
        this.mContext=mContext;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
    public View getTabView(int position) {
        View view = inflater.inflate(R.layout.main_tab, null);
        TextView textview = (TextView) view.findViewById(R.id.tab_tv1);
        ImageView imageview = (ImageView) view.findViewById(R.id.tab_iv1);
        textview.setText(titles[position]);
        imageview.setImageResource(image[position]);
        return view;
    }
}
