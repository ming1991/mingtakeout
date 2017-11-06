package com.heima.takeout31.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heima.takeout31.R;
import com.heima.takeout31.model.net.GoodsTypeInfo;
import com.heima.takeout31.ui.fragment.GoodsFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2016/12/10.
 */
public class GoodsTypeRvAdapter extends RecyclerView.Adapter {
    private GoodsFragment mGoodsFragment;
    private Context mContext;
    private List<GoodsTypeInfo> mGoodsTypeInfoList;

    public GoodsTypeRvAdapter(Context context, GoodsFragment goodsFragment) {
        mContext = context;
        this.mGoodsFragment = goodsFragment;
    }

    public void setGoodsTypeInfoList(List<GoodsTypeInfo> goodsTypeInfoList) {
        mGoodsTypeInfoList = goodsTypeInfoList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_type, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    /**
     * 绑定holder,设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mGoodsTypeInfoList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(mGoodsTypeInfoList!=null){
            return mGoodsTypeInfoList.size();
        }
        return 0;
    }

    public  int selectIndex = 0;
    class ViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        @InjectView(R.id.tvCount)
        TextView mTvCount;
        @InjectView(R.id.type)
        TextView mType;
        private int mPostion;
        private GoodsTypeInfo mGoodsTypeInfo;

        ViewHolder(View view) {
            super(view);
            this.mView = view;
            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectIndex = mPostion;
                    notifyDataSetChanged();
                    //TODO:2.让右侧列表选中对应的position
                    int typeId = mGoodsTypeInfo.getId();
                    int postion = mGoodsFragment.mGoodsFragmentPresenter.getGoodsPostionByTypeId(typeId);
                    mGoodsFragment.mSlhlv.setSelection(postion);
                }
            });
        }

        public void setData(GoodsTypeInfo goodsTypeInfo, int position) {
            this.mGoodsTypeInfo = goodsTypeInfo;
            if(position == selectIndex){
                //选中的白底、粗体黑字
                mView.setBackgroundColor(Color.WHITE);
                mType.setTextColor(Color.BLACK);
                mType.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                //灰底、普通字体
                mType.setTextColor(Color.GRAY);
                mView.setBackgroundColor(Color.parseColor("#b9dedcdc"));
                mType.setTypeface(Typeface.DEFAULT);
            }
            this.mPostion = position;
            mType.setText(goodsTypeInfo.getName());
            mTvCount.setText(goodsTypeInfo.getCount() + "");
            if(goodsTypeInfo.getCount()>0){
                mTvCount.setVisibility(View.VISIBLE);
            }else{
                mTvCount.setVisibility(View.INVISIBLE);
            }
        }
    }
}
