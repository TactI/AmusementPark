package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    GuideActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/9  14:46
 *    描述：       引导页面
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xiaoli.amusementpark.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    //容器
    private List<View> mList=new ArrayList<>();
    private View view1,view2,view3;
    //小圆点
    private ImageView point1,point2,point3;
    //跳过
    private ImageView tv_back;
    private Button btn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        tv_back= (ImageView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        point1= (ImageView) findViewById(R.id.point1);
        point2= (ImageView) findViewById(R.id.point2);
        point3= (ImageView) findViewById(R.id.point3);
        //设置默认图片
        setPointImg(true,false,false);
        //装载view
        view1=View.inflate(this,R.layout.pager_item_one,null);
        view2=View.inflate(this,R.layout.pager_item_two,null);
        view3=View.inflate(this,R.layout.pager_item_three,null);
        btn_start= (Button) view3.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());
        //ViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        setPointImg(true,false,false);
                        tv_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false,true,false);
                        tv_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false,false,true);
                        tv_back.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    //跳过按钮和进入主页点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.tv_back:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
    //重写适配器
    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return  mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        //预加载
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }
        //移除item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }
    //设置小圆点的选择状态
    private void setPointImg(boolean ischeck1,boolean ischeck2,boolean ischeck3){
        if (ischeck1){
            point1.setImageResource(R.drawable.point_on);
        }else {
            point1.setImageResource(R.drawable.point_off);
        }
        if (ischeck2){
            point2.setImageResource(R.drawable.point_on);
        }else {
            point2.setImageResource(R.drawable.point_off);
        }
        if (ischeck3){
            point3.setImageResource(R.drawable.point_on);
        }else {
            point3.setImageResource(R.drawable.point_off);
        }
    }
}
