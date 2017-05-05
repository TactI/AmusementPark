package com.example.xiaoli.amusementpark;



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
import com.example.xiaoli.amusementpark.utils.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
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

//    //drawerlayout控件
//    private DrawerLayout drawerlayout;
//    //NavigationView
//    private NavigationView navigation_view;
//    private View handerView;
//    private ImageView iv_user;
//
//    private ImageView leftmenu;
//    private TextView tv_title;
//    private String[] titles={"首页","景点","我的"};
//    private ImageView iv_setting;
//    @Override
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

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }

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
//        tv_title.setText(titles[mViewPager.getCurrentItem()]);
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                tv_title.setText(titles[position]);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

//        iv_setting= (ImageView) findViewById(R.id.iv_setting);
//        iv_setting.setOnClickListener(this);
//        leftmenu= (ImageView) findViewById(R.id.leftmenu);
//        leftmenu.setOnClickListener(this);
//
//        navigation_view= (NavigationView) findViewById(R.id.navigation_view);
//        navigation_view.setItemIconTintList(null);
//        handerView=navigation_view.getHeaderView(0);
//        iv_user= (ImageView) handerView.findViewById(R.id.iv_user);
//        iv_user.setOnClickListener(this);
//        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected( MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.favorite:
//
//                        break;
//                    case R.id.file:
//
//                        break;
//                    case R.id.wallet:
//
//                        break;
//                    case R.id.photo:
//
//                        break;
//                }
//                drawerlayout.closeDrawer(Gravity.LEFT);
//                return true;
//            }
//        });
//
//        drawerlayout= (DrawerLayout) findViewById(R.id.drawerlayout);

    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.leftmenu:
//                drawerlayout.openDrawer(Gravity.LEFT);
//                break;
//            case R.id.iv_setting:
//                break;
//            case R.id.iv_user:
//                drawerlayout.closeDrawer(Gravity.LEFT);
//                break;
//        }
//    }

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
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            firsttime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
