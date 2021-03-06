package com.heima.testmap31;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;

import java.util.List;

/**
 * Created by lidongzhi on 2016/12/13.
 */
public class AroundRvAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<PoiItem> mPoiItems;

    public AroundRvAdapter(Context context) {
        mContext = context;
    }

    public void setPoiItems(List<PoiItem> poiItems) {
        mPoiItems = poiItems;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_select_receipt_address, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mPoiItems.get(position));
    }

    @Override
    public int getItemCount() {
        if(mPoiItems!=null){
            return mPoiItems.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @butterknife.InjectView(R.id.iv)
        ImageView mIv;
        @butterknife.InjectView(R.id.tv_title)
        TextView mTvTitle;
        @butterknife.InjectView(R.id.tv_snippet)
        TextView mTvSnippet;

        ViewHolder(View view) {
            super(view);
            butterknife.ButterKnife.inject(this, view);
        }

        public void setData(PoiItem poiItem) {
            mTvTitle.setText(poiItem.getTitle());
            mTvSnippet.setText(poiItem.getSnippet());
        }
    }
}
