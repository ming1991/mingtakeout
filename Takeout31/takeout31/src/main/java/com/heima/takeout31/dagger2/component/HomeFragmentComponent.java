package com.heima.takeout31.dagger2.component;

import com.heima.takeout31.dagger2.module.HomeFragmentModule;
import com.heima.takeout31.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * Created by lidongzhi on 2016/12/7.
 */
@Component(modules = HomeFragmentModule.class)
public interface HomeFragmentComponent {
    void in(HomeFragment homeFragment);
}
