package com.heima.takeout31.dagger2.component;

import com.heima.takeout31.dagger2.module.GoodsFragmentModule;
import com.heima.takeout31.ui.fragment.GoodsFragment;

import dagger.Component;

/**
 * Created by lidongzhi on 2016/12/10.
 */
@Component(modules = GoodsFragmentModule.class)
public interface GoodsFragmentComponent {
    void in(GoodsFragment goodsFragment);
}
