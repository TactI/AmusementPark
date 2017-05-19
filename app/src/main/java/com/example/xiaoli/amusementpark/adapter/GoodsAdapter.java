package com.example.xiaoli.amusementpark.adapter;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.adapter
 *    文件名：    GoodsAdapter
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/18  16:37
 *    描述：      页面详情的适配器
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.ui.WebViewActivity;
import com.example.xiaoli.amusementpark.utils.PicassoUtils;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater inflater;

    public GoodsAdapter(Context mContext) {
        this.mContext = mContext;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.line1:
                Intent intent=new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(mContext,WebViewActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.line2:

                break;
            case R.id.line3:

                break;
            case R.id.btn_order:

                break;
        }
    }

    public enum ITEM_TYPE {
        Top,
        Text,
        Adult,
        Student,
        Child,
        Ticket
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType== ITEM_TYPE.Top.ordinal()){
            View view=inflater.inflate(R.layout.pay_item1,null);
            return new TopHolder(view);
        }else if (viewType== ITEM_TYPE.Text.ordinal()){
            View view=inflater.inflate(R.layout.pay_item2,null);
            return new TextHolder(view);
        }else if (viewType== ITEM_TYPE.Adult.ordinal()){
            View view=inflater.inflate(R.layout.pay_adult,null);
            return new AdultHolder(view);
        }else if (viewType==ITEM_TYPE.Child.ordinal()){
            View view=inflater.inflate(R.layout.pay_child,null);
            return new ChildHolder(view);
        }else if (viewType==ITEM_TYPE.Student.ordinal()){
            View view=inflater.inflate(R.layout.pay_student,null);
            return new StudentHolder(view);
        }else if (viewType==ITEM_TYPE.Ticket.ordinal()){
            View view=inflater.inflate(R.layout.pay_item3,null);
            return new TicketHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopHolder){
            PicassoUtils.loadImageViewHolder(mContext,"http://120.25.249.201/sql/imageview/shanghai.jpg",R.drawable.timg,R.drawable.error,((TopHolder) holder).iv_img);
            ((TopHolder) holder).line1.setOnClickListener(this);
            ((TopHolder) holder).line2.setOnClickListener(this);
            ((TopHolder) holder).line3.setOnClickListener(this);
        }else if (holder instanceof TicketHolder){
            ((TicketHolder) holder).tv_ticket.setText("上海欢乐谷");
            ((TicketHolder) holder).tv_price.setText("￥120");
            ((TicketHolder) holder).btn_order.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return ITEM_TYPE.Top.ordinal();
        }else if (position==1){
            return ITEM_TYPE.Text.ordinal();
        }else if (position==2 ){
            return ITEM_TYPE.Adult.ordinal();
        }else if (position==5){
            return  ITEM_TYPE.Student.ordinal();
        }else if (position==7){
            return  ITEM_TYPE.Child.ordinal();
        }else {
            return  ITEM_TYPE.Ticket.ordinal();
        }
    }
    //顶布局
    public class TopHolder extends RecyclerView.ViewHolder{
        private ImageView iv_img;
        private RelativeLayout line1,line2,line3;
        public TopHolder(View itemView) {
            super(itemView);
            iv_img= (ImageView) itemView.findViewById(R.id.iv_img);
            line1= (RelativeLayout) itemView.findViewById(R.id.line1);
            line2= (RelativeLayout) itemView.findViewById(R.id.line2);
            line3= (RelativeLayout) itemView.findViewById(R.id.line3);
        }
    }
    //门票
    public class TextHolder extends RecyclerView.ViewHolder{
        public TextHolder(View itemView) {
            super(itemView);
        }
    }
    //成年人
    public class AdultHolder extends RecyclerView.ViewHolder {
        public AdultHolder(View itemView) {
            super(itemView);
        }
    }
    //学生
    public class StudentHolder extends RecyclerView.ViewHolder {
        public StudentHolder(View itemView) {
            super(itemView);
        }
    }
    //优待票
    public class ChildHolder extends RecyclerView.ViewHolder {
        public ChildHolder(View itemView) {
            super(itemView);
        }
    }
    //门票详情
    public class TicketHolder extends RecyclerView.ViewHolder {
        private TextView tv_ticket;
        private TextView tv_price;
        private Button btn_order;
        public TicketHolder(View itemView) {
            super(itemView);
            tv_ticket= (TextView) itemView.findViewById(R.id.tv_ticket);
            tv_price= (TextView) itemView.findViewById(R.id.tv_price);
            btn_order= (Button) itemView.findViewById(R.id.btn_order);
        }
    }
}
