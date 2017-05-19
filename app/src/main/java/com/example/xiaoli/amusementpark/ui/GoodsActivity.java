package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    GoodsActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/15  15:38
 *    描述：       景点详情页面
 */

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.adapter.GoodsAdapter;
import com.example.xiaoli.amusementpark.utils.ShareUtils;


public class GoodsActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar mtoolbar;
    private TextView title_bar;
    private ImageView iv_back;
    private RecyclerView mRecycleView;
    private GoodsAdapter myAdapter;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail);
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
        mtoolbar= (Toolbar) findViewById(R.id.mtoolbar);
        mtoolbar.setBackgroundResource(R.color.blue2);
        title_bar= (TextView) findViewById(R.id.title_bar);
        title_bar.setText(ShareUtils.getString(this,"palace_name",""));
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        mRecycleView= (RecyclerView) findViewById(R.id.mRecycleView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        myAdapter=new GoodsAdapter(this);
        mRecycleView.setAdapter(myAdapter);
        SpacesItemDecoration decoration=new SpacesItemDecoration(1);
        mRecycleView.addItemDecoration(decoration);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_back:
                finish();
            break;
        }
    }
    //设置recycleView的间隔
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpacesItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }
}
