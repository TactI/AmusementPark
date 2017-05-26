package com.example.xiaoli.amusementpark;



import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.adapter.TabViewAdapter;
import com.example.xiaoli.amusementpark.fragment.MainFragment;
import com.example.xiaoli.amusementpark.fragment.OrderFragment;
import com.example.xiaoli.amusementpark.fragment.PlaceFragment;
import com.example.xiaoli.amusementpark.ui.BaseActivity;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{
    //按下Back键的时间
    private long firsttime=0;
    //ViewPager
    private ViewPager mViewPager;
    //Tablayout
    private TabLayout mTabLayout;
    //适配器
    private TabViewAdapter adapter;

    // fragment
    private List<Fragment> mFragment;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initData();
        initView();
    }
    private void initData() {
        mFragment=new ArrayList<>();
        mFragment.add(new MainFragment());
        mFragment.add(new PlaceFragment());
        mFragment.add(new OrderFragment());
    }

    private void initView() {
//        tv_title= (TextView) findViewById(R.id.tv_title);
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        mTabLayout= (TabLayout) findViewById(R.id.mTabLayout);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());
        //适配器
        adapter=new TabViewAdapter(getSupportFragmentManager(),mFragment,this);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);//将tablayout与viewpager建立关系
        for(int i = 0;i < mTabLayout.getTabCount();i++){//判断tablayout有几个菜单数量
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null){
                tab.setCustomView(adapter.getTabView(i));//将adapter设置好的获取图片和文字的方法设置到视图中
            }
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - firsttime) > 2000) {
            ToastUtils.showToastShort(getApplicationContext(), "再按一次退出程序");
            firsttime = System.currentTimeMillis();
        } else {
            Intent intent = new Intent("exitApp");
            intent.putExtra("closeAll", 1);
            sendBroadcast(intent);//发送广播
        }
    }
}
