package com.example.xiaoli.amusementpark.adapter;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.adapter
 *    文件名：    HomeAdapter
 *    创建者：    XiaoLi
 *    创建时间：  2017/4/28  20:41
 *    描述：       主页面RecycleView的适配器
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.HomeJavaBean;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<HomeJavaBean>  mList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public static enum ITEM_TYPE {
        Vercial,
        Horizontal
    }
    public HomeAdapter(Context mContext,List<HomeJavaBean> mList){
        this.mContext=mContext;
        this.mList=mList;
        inflater=LayoutInflater.from(mContext);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ITEM_TYPE.Vercial.ordinal()){
            View view=inflater.inflate(R.layout.home_item1,null);
            return new VercialHolder(view);
        }else if (viewType==ITEM_TYPE.Horizontal.ordinal()){
            View view=inflater.inflate(R.layout.home_item2,null);
            return new HorizontalHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder( final RecyclerView.ViewHolder holder, int position) {
        int color = ContextCompat.getColor(mContext, mList.get(position).getColor());
        if (holder instanceof VercialHolder){
            ((VercialHolder) holder).mCardView1.setCardBackgroundColor(color);
            ((VercialHolder) holder).image1.setImageResource(mList.get(position).getImage());
            ((VercialHolder) holder).tv_text1.setText(mList.get(position).getText());
            if (onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
        }else if (holder instanceof HorizontalHolder){
            ((HorizontalHolder) holder).mCardView2.setCardBackgroundColor(color);
            ((HorizontalHolder) holder).image2.setImageResource(mList.get(position).getImage());
            ((HorizontalHolder) holder).tv_text2.setText(mList.get(position).getText());
            if (onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position==0){
            return ITEM_TYPE.Vercial.ordinal();
        }else if(position==1){
            return ITEM_TYPE.Vercial.ordinal();
        }
        else {
            return ITEM_TYPE.Horizontal.ordinal();
        }
    }
    public class VercialHolder extends RecyclerView.ViewHolder{
        private CardView mCardView1;
        private ImageView image1;
        private TextView tv_text1;
        public VercialHolder(View itemView) {
            super(itemView);
            mCardView1= (CardView) itemView.findViewById(R.id.mCardView1);
            image1= (ImageView) itemView.findViewById(R.id.image1);
            tv_text1= (TextView) itemView.findViewById(R.id.tv_text1);
        }
    }

    public class HorizontalHolder extends RecyclerView.ViewHolder {
        private CardView mCardView2;
        private ImageView image2;
        private TextView tv_text2;
        public HorizontalHolder(View itemView) {
            super(itemView);
            mCardView2= (CardView) itemView.findViewById(R.id.mCardView2);
            image2= (ImageView) itemView.findViewById(R.id.image2);
            tv_text2= (TextView) itemView.findViewById(R.id.tv_text2);
        }
    }
}
