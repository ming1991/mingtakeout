package com.heima.takeout31.presenter;

import com.heima.takeout31.model.net.GoodsInfo;
import com.heima.takeout31.model.net.GoodsTypeInfo;
import com.heima.takeout31.ui.activity.BusinessActivity;
import com.heima.takeout31.ui.fragment.GoodsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 只需要处理购物车的逻辑计算
 */

public class BusinessActivityPresenter extends BasePresenter {

    BusinessActivity mBusinessActivity;
    private GoodsFragment mGoodsFragment;

    public BusinessActivityPresenter(BusinessActivity businessActivity) {
        mBusinessActivity = businessActivity;
    }

    @Override
    protected void parserJson(String data) {

    }

    public List<GoodsInfo> getCartData() {
        List<GoodsInfo> mCartList = new ArrayList<>();

        GoodsFragment goodsFragment = (GoodsFragment) mBusinessActivity.mFragmentList.get(0);
        List<GoodsInfo> goodsInfoList = goodsFragment.mGoodsFragmentPresenter.mAllGoodsInfoList;
        for(int j=0;j<goodsInfoList.size();j++){
            GoodsInfo goodsInfo = goodsInfoList.get(j);
            if(goodsInfo.getCount()>0){
                mCartList.add(goodsInfo);
            }
        }
        return mCartList;
    }

    public void updateCartData() {
        List<GoodsInfo> mCartList = getCartData();
        int cartCount = 0;
        float countPrice = 0;

        for(int j=0;j<mCartList.size();j++){
            GoodsInfo goodsInfo = mCartList.get(j);
           cartCount += goodsInfo.getCount();
            countPrice += goodsInfo.getCount() * goodsInfo.getNewPrice();
        }
        //2.刷新购物车UI，购物车商品数量，总价
        mBusinessActivity.updateCartUi(cartCount, countPrice);
    }

    public void clearCart() {
        List<GoodsInfo> mCartList = getCartData();

        for(int j=0;j<mCartList.size();j++){
            GoodsInfo goodsInfo = mCartList.get(j);
            goodsInfo.setCount(0);
        }
        //2.刷新购物车UI，购物车商品数量，总价
        mBusinessActivity.updateCartUi(0, 0.0f);

        //3.4.更新两边的adpater
        //3.更新右侧列表需要拿到右侧adapter
        mGoodsFragment = (GoodsFragment) mBusinessActivity.mFragmentList.get(0);
        List<GoodsTypeInfo> goodsTypeInfoList =  mGoodsFragment.mGoodsFragmentPresenter.mGoodsTypeInfoList;
       for(int i=0;i<goodsTypeInfoList.size();i++){
           GoodsTypeInfo goodsTypeInfo = goodsTypeInfoList.get(i);
           goodsTypeInfo.setCount(0);
       }
        mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();

        mGoodsFragment.mGoodsInfoAdapter.notifyDataSetChanged();
    }
}
