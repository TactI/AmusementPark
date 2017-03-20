package com.example.xiaoli.amusementpark.fragment;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.fragment
 *    文件名：    MainFragment
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/16  16:44
 *    描述：       首页
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.ui.BannerOneActivity;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment{
    // 声明控件
    private ViewPager mViewPager;
    private List<ImageView> mlist;
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    // 广告语
    private String[] bannerTexts = { "因为专业 所以卓越", "坚持创新 行业领跑", "诚信 专业 双赢", "精细 和谐 大气 开放" };
    // 广告图素材
    private int[] bannerImages = { R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4 };
    // 圆圈标志位
    private int pointIndex = 0;
    private static int pointClickPosition = 0; //point点击的位置
    private boolean isDraging = false;
    //监听器
    private BannerListener bannerListener;
    //TAG
    private final int OPTION_TYPE_AUTO=1100;
    private final int OPTION_TYPE_POINT=1101;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int option = msg.what;
            switch (option) {
                case OPTION_TYPE_AUTO: //option==1执行viewPage跳转到下一个
                    int currentPostion = mViewPager.getCurrentItem();//获得当前的ViewPage位置
                    mViewPager.setCurrentItem(++currentPostion);
                    handler.sendEmptyMessageDelayed(OPTION_TYPE_AUTO, 3000);//回调handler 实现自动轮播
                    break;
                case OPTION_TYPE_POINT:
                    int currentPostion2 = mViewPager.getCurrentItem();//获得当前的ViewPage位置
                    mViewPager.setAdapter(new MyViewPageAdapter());
                    mViewPager.setCurrentItem(currentPostion2 - currentPostion2 % mlist.size() + pointClickPosition);
                    handler.sendEmptyMessageDelayed(OPTION_TYPE_AUTO, 3000);//回调handler 实现自动轮播
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_fragment,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mViewPager = (ViewPager)view.findViewById(R.id.mViewPager);
        mTextView = (TextView)view.findViewById(R.id.tv_bannertext);
        mLinearLayout = (LinearLayout)view.findViewById(R.id.points);
        initData();
    }

    private void initData() {
        mlist = new ArrayList<ImageView>();
        View view;
        LinearLayout.LayoutParams params;
        for (int i = 0; i < bannerImages.length; i++) {
            // 设置广告图
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundResource(bannerImages[i]);
            mlist.add(imageView);
            // 设置圆圈点
            view = new View(getActivity());
            params = new LinearLayout.LayoutParams(10, 10);
            params.leftMargin = 10;
            params.bottomMargin=5;
            view.setBackgroundResource(R.drawable.point_bg);
            view.setLayoutParams(params);
            view.setEnabled(false);

            mLinearLayout.addView(view);
        }
        //取中间数来作为起始位置
        int index = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % mlist.size());
        //用来出发监听器
        mViewPager.setCurrentItem(index);
        bannerListener = new BannerListener();
        mViewPager.addOnPageChangeListener(bannerListener);
        mViewPager.setAdapter(new MyViewPageAdapter());
        //启动轮播
        handler.sendEmptyMessage(OPTION_TYPE_AUTO);
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            final View point = mLinearLayout.getChildAt(i);
            final int finalI = i;
            point.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pointClickPosition = finalI;
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(OPTION_TYPE_POINT, 50);
                    for (int j = 0; j < mLinearLayout.getChildCount(); j++) {
                        mLinearLayout.getChildAt(j).setEnabled(true);
                    }
                    point.setEnabled(false);
                }
            });
        }
    }
    //实现VierPager监听器接口
    class BannerListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int newPosition = position % bannerImages.length;
            mTextView.setText(bannerTexts[newPosition]);
            mLinearLayout.getChildAt(newPosition).setEnabled(true);
            mLinearLayout.getChildAt(pointIndex).setEnabled(false);
            // 更新标志位
            pointIndex = newPosition;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == mViewPager.SCROLL_STATE_DRAGGING) { //拖拽状态
                isDraging = true;
                //如果处于拖拽状态 就移除handler 避免拖拽过程中自动轮播、
                handler.removeCallbacksAndMessages(null);
            } else if (state == mViewPager.SCROLL_STATE_SETTLING) {//滑动状态

            } else if (state == mViewPager.SCROLL_STATE_IDLE) {//休闲状态
                isDraging = false;
                //拖拽结束后调用改方法 先移除handler 然后重新发送handler 启动自动 轮播
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(OPTION_TYPE_AUTO,3000);
            }
        }
    }
    class MyViewPageAdapter extends PagerAdapter {
        /**
         * 返回ViewPage总数
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = (ImageView) mlist.get(position % mlist.size());
            container.addView(imageView);
            mViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //鼠标按下的时候移除handler
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            //鼠标抬起的时候移除handler 并且重新发送handler
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(OPTION_TYPE_AUTO, 5000);
                            break;
                    }
                    return false;
                }
            });
            //为当前imageView设置点击监听
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((position%bannerTexts.length)==0){
                        startActivity(new Intent(getActivity(),BannerOneActivity.class));
                    }
                }
            });
            return imageView;
        }

        /**
         * 工系统调用 判断instantiateItem方法返回的View是否和object相同
         *
         * @param view   instantiateItem方法返回的ImageView
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}