package com.heima.takeout31.dagger2.module;

import com.heima.takeout31.presenter.GoodsFragmentPresenter;
import com.heima.takeout31.ui.fragment.GoodsFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lidongzhi on 2016/12/10.
 */
@Module
public class GoodsFragmentModule {

    GoodsFragment mGoodsFragment;

    public GoodsFragmentModule(GoodsFragment goodsFragment) {
        mGoodsFragment = goodsFragment;
    }

    @Provides
    GoodsFragmentPresenter providerGoodsFragmentPresenter(){
        return new GoodsFragmentPresenter(mGoodsFragment);
    }
}
