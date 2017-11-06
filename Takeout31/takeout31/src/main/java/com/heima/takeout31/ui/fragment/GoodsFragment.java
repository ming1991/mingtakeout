package com.heima.takeout31.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heima.takeout31.R;
import com.heima.takeout31.dagger2.component.DaggerGoodsFragmentComponent;
import com.heima.takeout31.dagger2.module.GoodsFragmentModule;
import com.heima.takeout31.presenter.GoodsFragmentPresenter;
import com.heima.takeout31.ui.activity.BusinessActivity;
import com.heima.takeout31.ui.adapter.GoodsAdapter;
import com.heima.takeout31.ui.adapter.GoodsTypeRvAdapter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2016/12/10.
 */
public class GoodsFragment extends Fragment {

    @InjectView(R.id.rv_goods_type)
    public RecyclerView mRvGoodsType;
    @InjectView(R.id.slhlv)
    public se.emilsjolander.stickylistheaders.StickyListHeadersListView mSlhlv;

    @Inject
    public GoodsFragmentPresenter mGoodsFragmentPresenter;
    public GoodsTypeRvAdapter mGoodsTypeRvAdapter;
    public GoodsAdapter mGoodsInfoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View goodsView = View.inflate(getContext(), R.layout.fragment_goods, null);
        ButterKnife.inject(this, goodsView);
        DaggerGoodsFragmentComponent.builder().goodsFragmentModule(new GoodsFragmentModule(this)).build().in(this);
        mGoodsTypeRvAdapter = new GoodsTypeRvAdapter(getContext(), this);
        mRvGoodsType.setAdapter(mGoodsTypeRvAdapter);
        mRvGoodsType.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        mGoodsInfoAdapter = new GoodsAdapter(getContext(),this);
        mSlhlv.setAdapter(mGoodsInfoAdapter);
        return goodsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int sellerId = ((BusinessActivity) getActivity()).mSeller.getId();
        mGoodsFragmentPresenter.loadGoodsInfo(String.valueOf(sellerId));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
