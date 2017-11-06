package com.heima.takeout31.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heima.takeout31.R;
import com.heima.takeout31.model.net.GoodsInfo;
import com.heima.takeout31.model.net.GoodsTypeInfo;
import com.heima.takeout31.ui.activity.BusinessActivity;
import com.heima.takeout31.ui.fragment.GoodsFragment;
import com.heima.takeout31.util.Constants;
import com.heima.takeout31.util.PriceFormater;
import com.heima.takeout31.util.TakeoutApp;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2016/12/11.
 */
public class CartRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GoodsInfo> mCartList;
    private final GoodsFragment mGoodsFragment;

    public CartRvAdapter(Context context) {
        mContext = context;
        mGoodsFragment = (GoodsFragment) ((BusinessActivity) mContext).mFragmentList.get(0);
    }

    public void setCartList(List<GoodsInfo> cartList) {
        mCartList = cartList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mCartList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(mCartList!=null){
            return mCartList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView mTvName;
        @InjectView(R.id.tv_type_all_price)
        TextView mTvTypeAllPrice;
        @InjectView(R.id.ib_minus)
        ImageButton mIbMinus;
        @InjectView(R.id.tv_count)
        TextView mTvCount;
        @InjectView(R.id.ib_add)
        ImageButton mIbAdd;
        @InjectView(R.id.ll)
        LinearLayout mLl;
        private GoodsInfo mGoodsInfo;
        private int mPostion;

        @OnClick({R.id.ib_minus,R.id.ib_add})
        public void onClick(View view){
            boolean isAdd =false;
            switch (view.getId()){
                case R.id.ib_add:
                    doAddOperation();
                    isAdd = true;
                    break;
                case R.id.ib_minus:
                    doMinusOperation();
                    break;
            }
            //2.更新购物车下方
            ((BusinessActivity) mContext).mBusinessActivityPresenter.updateCartData();
            //3.更新右侧列表需要拿到右侧adapter
            int typeId = mGoodsInfo.getTypeId();
            //找到在左侧列表中该typeId对应的postion
            int position = mGoodsFragment.mGoodsFragmentPresenter.getTypePositionByTypeId(typeId);
            GoodsTypeInfo goodsTypeInfo =  mGoodsFragment.mGoodsFragmentPresenter.mGoodsTypeInfoList.get(position);
            int redDotCount = goodsTypeInfo.getCount();
            if(isAdd){
                redDotCount++;
            }else{
                redDotCount--;
            }
            goodsTypeInfo.setCount(redDotCount);
            mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();

            mGoodsFragment.mGoodsInfoAdapter.notifyDataSetChanged();
        }

        private void doMinusOperation() {
            int count = mGoodsInfo.getCount();
            if(count==1) {
                //要把馒头这一行取消可见
                mCartList.remove(mPostion);
                //只有一个，删除缓存信息
                TakeoutApp.sInstance.deleteCacheSelectedInfo(mGoodsInfo.getId());
            }else{
                //更新缓存信息
                TakeoutApp.sInstance.updateCacheSelectedInfo(mGoodsInfo.getId(), Constants.MINUS);
            }
            count--;
            mGoodsInfo.setCount(count);
            notifyDataSetChanged();

            if(mCartList.size() == 0){
                ((BusinessActivity) mContext).showCart();
            }
        }

        private void doAddOperation() {
            int count = mGoodsInfo.getCount();
            //更新缓存信息
            TakeoutApp.sInstance.updateCacheSelectedInfo(mGoodsInfo.getId(), Constants.ADD);
            count++;
            mGoodsInfo.setCount(count);
            notifyDataSetChanged();
        }

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setData(GoodsInfo goodsInfo, int position) {
            this.mGoodsInfo = goodsInfo;
            this.mPostion = position;
            mTvName.setText(goodsInfo.getName());
            mTvCount.setText(goodsInfo.getCount() + "");
            mTvTypeAllPrice.setText(PriceFormater.format(goodsInfo.getCount() * goodsInfo.getNewPrice()));
        }
    }
}
