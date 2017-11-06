package com.heima.takeout31.dagger2.module;

import com.heima.takeout31.presenter.OrderFragmentPresenter;
import com.heima.takeout31.ui.fragment.OrderFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lidongzhi on 2016/12/8.
 */
@Module
public class OrderFragmentModule {

    OrderFragment mOrderFragment;

    public OrderFragmentModule(OrderFragment orderFragment) {
        mOrderFragment = orderFragment;
    }

    @Provides
    OrderFragmentPresenter providerOrderFragmentPresenter(){
        return new OrderFragmentPresenter(mOrderFragment);
    }
}
