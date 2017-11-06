package com.heima.takeout31.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.takeout31.R;
import com.heima.takeout31.model.dao.RecepitAddressBean;
import com.heima.takeout31.ui.activity.AddRecepitAddressActivity;
import com.heima.takeout31.ui.activity.RecepitAddressActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2016/12/11.
 */

public class RecepitAddressAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<RecepitAddressBean> mRecepitAddressBeanList;

    public RecepitAddressAdapter(Context context) {
        mContext = context;
    }

    public void setRecepitAddressBeanList(List<RecepitAddressBean> recepitAddressBeanList) {
        mRecepitAddressBeanList = recepitAddressBeanList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_receipt_address, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mRecepitAddressBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mRecepitAddressBeanList!=null){
            return mRecepitAddressBeanList.size();
        }
        return 0;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        @InjectView(R.id.tv_name)
        TextView mTvName;
        @InjectView(R.id.tv_sex)
        TextView mTvSex;
        @InjectView(R.id.tv_phone)
        TextView mTvPhone;
        @InjectView(R.id.tv_label)
        TextView mTvLabel;
        @InjectView(R.id.tv_address)
        TextView mTvAddress;
        @InjectView(R.id.iv_edit)
        ImageView mIvEdit;
        private RecepitAddressBean addressBean;

        @OnClick(R.id.iv_edit)
        public void onClick(View view){
            Intent intent = new Intent(mContext, AddRecepitAddressActivity.class);
            intent.putExtra("addressBean", addressBean);
            mContext.startActivity(intent);
        }

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent  = new Intent();
                    intent.putExtra("addressBean", addressBean);
                    ((RecepitAddressActivity) mContext).setResult(200,intent);
                    ((RecepitAddressActivity) mContext).finish();
                }
            });
        }

        public void setData(RecepitAddressBean addressBean) {
            this.addressBean = addressBean;
            mTvName.setText(addressBean.name);
            mTvSex.setText(addressBean.sex);
            mTvPhone.setText(addressBean.phone + "," + addressBean.phoneOther);
            mTvLabel.setText(addressBean.label);
            mTvLabel.setVisibility(View.VISIBLE);
            mTvLabel.setTextColor(Color.BLACK);
            mIvEdit.setVisibility(View.VISIBLE);
            mTvLabel.setBackgroundColor(Color.parseColor("#778899"));
            mTvAddress.setText(addressBean.address + ", " + addressBean.detailAddress);
        }
    }
}
