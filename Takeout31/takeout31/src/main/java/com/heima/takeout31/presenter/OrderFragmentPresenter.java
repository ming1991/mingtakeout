package com.heima.takeout31.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heima.takeout31.model.net.Order;
import com.heima.takeout31.model.net.ResponseInfo;
import com.heima.takeout31.ui.fragment.OrderFragment;

import java.util.List;

import retrofit2.Call;

/**
 * Created by lidongzhi on 2016/12/8.
 */

public class OrderFragmentPresenter extends BasePresenter {

    OrderFragment mOrderFragment;
    public OrderFragmentPresenter(OrderFragment orderFragment) {
        mOrderFragment = orderFragment;
    }

    public void getOrderList(String userId){
       Call<ResponseInfo> orderCall =  mTakeoutService.getOrderList(userId);
       orderCall.enqueue(mCallback);
    }

    @Override
    protected void parserJson(String data) {
        Gson gson = new Gson();
        List<Order> orderList = gson.fromJson(data, new TypeToken<List<Order>>(){}.getType());
        mOrderFragment.mAdapter.setOrderList(orderList);
        //关闭转圈圈
        mOrderFragment.mSrlOrder.setRefreshing(false);
    }
}
