package com.heima.takeout31.dagger2.component;

import com.heima.takeout31.dagger2.module.OrderFragmentModule;
import com.heima.takeout31.ui.fragment.OrderFragment;

import dagger.Component;

/**
 * Created by lidongzhi on 2016/12/8.
 */
@Component(modules = OrderFragmentModule.class)
public interface OrderFragmentComponent {
    void in(OrderFragment orderFragment);
}
