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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiaoli.amusementpark.R;

public class PlaceFragment extends Fragment implements View.OnClickListener {
    private SearchView searchView;
    private TextView cancelView;
    private SearchView.SearchAutoComplete searchTextArea;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.place_fragment,null);
        findView(view);
        return view;
    }

    private void findView(View view) {

        searchView= (SearchView) view.findViewById(R.id.searchView);
        searchTextArea = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        cancelView= (TextView) view.findViewById(R.id.cancelView);
        cancelView.setOnClickListener(this);
        final RelativeLayout.LayoutParams rightMargin = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rightMargin.setMarginEnd(80);

        final RelativeLayout.LayoutParams zeroMargin = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        zeroMargin.setMarginEnd(0);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    searchView.setLayoutParams(rightMargin);
                    cancelView.setVisibility(View.VISIBLE);
                }else{
                    searchView.setLayoutParams(zeroMargin);
                    cancelView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancelView:
                searchTextArea.setText("");
                searchView.clearFocus();
                break;
        }
    }
}
