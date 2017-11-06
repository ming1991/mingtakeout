package com.heima.takeout31.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heima.takeout31.R;
import com.heima.takeout31.dagger2.component.DaggerOrderFragmentComponent;
import com.heima.takeout31.dagger2.module.OrderFragmentModule;
import com.heima.takeout31.model.net.Order;
import com.heima.takeout31.presenter.OrderFragmentPresenter;
import com.heima.takeout31.ui.adapter.OrderRvAdapter;
import com.heima.takeout31.util.TakeoutApp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2016/12/7.
 */
public class OrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.rv_order_list)
    RecyclerView mRvOrderList;
    @InjectView(R.id.srl_order)
    public SwipeRefreshLayout mSrlOrder;

    @Inject
    OrderFragmentPresenter mOrderFragmentPresenter;
    public OrderRvAdapter mAdapter;
    List<Order> mOrderList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View orderView = View.inflate(getContext(), R.layout.fragment_order, null);
        ButterKnife.inject(this, orderView);
        DaggerOrderFragmentComponent.builder().orderFragmentModule(new OrderFragmentModule(this)).build().in(this);
        mAdapter = new OrderRvAdapter(getContext(), mOrderList);
        mRvOrderList.setAdapter(mAdapter);
        mRvOrderList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mSrlOrder.setOnRefreshListener(this);
        return orderView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int userId = TakeoutApp.sUser.getId();
        if(userId == -1){
            Toast.makeText(getContext(), "请先登录!", Toast.LENGTH_SHORT).show();
        }else {
            mOrderFragmentPresenter.getOrderList(userId + "");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        //进度条转圈圈的时候执行此方法
        int userId = TakeoutApp.sUser.getId();
        mOrderFragmentPresenter.getOrderList(userId + "");
    }
}
