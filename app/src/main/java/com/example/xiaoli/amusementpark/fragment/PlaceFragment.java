package com.example.xiaoli.amusementpark.fragment;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.fragment
 *    文件名：    MainFragment
 *    创建者：    XiaoLi
 *    创建时间：  2017/3/16  16:44
 *    描述：      景点页面
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.Palace;
import com.example.xiaoli.amusementpark.ui.GoodsActivity;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.PicassoUtils;
import com.example.xiaoli.amusementpark.utils.ShareUtils;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PlaceFragment extends Fragment {
    private SearchView searchView;
    //private static final int DATA=1000;
    private SearchView.SearchAutoComplete searchTextArea;
    private ListView mListView;
    private List<Palace.palace> mData = new ArrayList<>(); // 这个数据会改变
    private List<Palace.palace> mBackData;
    private MyAdapter mAdapter;
    private Handler handler=new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.place_fragment,null);
        findView(view);
        return view;
    }
    private void findView(View view) {
        mListView= (ListView) view.findViewById(R.id.mListView);
        mListView.setTextFilterEnabled(true);// 开启过滤功能

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent();
                ShareUtils.putString(getActivity(),"palace_name",mData.get(i).getPalace_name());
                intent.setClass(getActivity(), GoodsActivity.class);
                startActivity(intent);
            }
        });
        //触摸listview隐藏键盘
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
        volleyqueue("http://120.25.249.201/sql/palace.php");
        searchView= (SearchView) view.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchTextArea = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        final String[] arr={"北京","上海","深圳","武汉","成都","天津"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        searchTextArea.setAdapter(arrayAdapter);
        searchTextArea.setThreshold(1);
        searchTextArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView= (TextView) view;
                String text=textView.getText().toString();
                searchView.setQuery(text,true);
            }
        });
        searchView.setQueryHint("请输入搜索内容");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //false隐藏键盘
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ListAdapter adapter=mListView.getAdapter();
                if (adapter instanceof Filterable){
                    Filter filter = ((Filterable) adapter).getFilter();
                    if (newText == null || newText.length() == 0) {
                        filter.filter(null);
                    } else {
                        filter.filter(newText);
                    }
                }
                return true;
            }
        });
    }

    private void volleyqueue(String s) {
        RxVolley.get(s, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                L.e(t);
                Gson gson=new Gson();
                Palace palace=gson.fromJson(t,Palace.class);
                mData=palace.getDatas();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setData(mData);
                    }
                });
            }
        });
    }

    private void setData(List<Palace.palace> data) {
        mData=data;
        mBackData=data;
        mAdapter=new MyAdapter();
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onDestroyView();
    }

    // 必须实现Filterable接口
    private class MyAdapter extends BaseAdapter implements Filterable {
        private MyFilter mFilter;
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (null == convertView) {
                viewHolder=new ViewHolder();
                convertView =LayoutInflater.from(getActivity()).inflate(R.layout.place_item,null);
                viewHolder.iv_place= (ImageView) convertView.findViewById(R.id.iv_place);
                viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
                viewHolder.tv_desc= (TextView) convertView.findViewById(R.id.tv_desc);
                viewHolder.videoplayer= (JCVideoPlayerStandard) convertView.findViewById(R.id.videoplayer);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_name.setText(mData.get(position).getPalace_name());
            viewHolder.tv_price.setText("￥"+mData.get(position).getPalace_price()+"起");
            viewHolder.tv_desc.setText(mData.get(position).getPalace_desc());
            PicassoUtils.loadImageViewHolder(getActivity(),mData.get(position).getImg_url(),R.drawable.timg,R.drawable.error,viewHolder.iv_place);
            viewHolder.videoplayer.setUp(mData.get(position).getVideo_url()
                    , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, mData.get(position).getVideo_title());
            PicassoUtils.loadImageViewHolder(getActivity(),mData.get(position).getVideo_image(),R.drawable.timg,R.drawable.error,viewHolder.videoplayer.thumbImageView);
            return convertView;
        }
        class ViewHolder{
            private ImageView iv_place;
            private TextView tv_name;
            private TextView tv_price;
            private TextView tv_desc;
            private JCVideoPlayerStandard videoplayer;
        }


        @Override
        public Filter getFilter() {
            if (null == mFilter) {
                mFilter = new MyFilter();
            }
            return mFilter;
        }
        // 自定义Filter类
        class MyFilter extends Filter {
            @Override
        // 该方法在子线程中执行
        // 自定义过滤规则
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                List<Palace.palace> newValues = new ArrayList<>();
                // 如果搜索框内容为空，就恢复原始数据
                if (TextUtils.isEmpty(charSequence)) {
                    newValues = mBackData;
                } else {
                    // 过滤出新数据
                    for (Palace.palace placeBean : mBackData) {
                        if (placeBean.getPalace_name().contains(charSequence)){
                            newValues.add(placeBean);
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData = (List<Palace.palace>) results.values;
                if (results.count > 0) {
                    mAdapter.notifyDataSetChanged(); // 通知数据发生了改变
                } else {
                    mAdapter.notifyDataSetInvalidated(); // 通知数据失效
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
