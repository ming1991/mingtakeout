package com.heima.takeout31.dagger2.component;

import com.heima.takeout31.dagger2.module.BusinessActivityModule;
import com.heima.takeout31.ui.activity.BusinessActivity;

import dagger.Component;

/**
 * Created by lidongzhi on 2016/12/10.
 */
@Component(modules = BusinessActivityModule.class)
public interface BusinessActivityComponent {
    void in(BusinessActivity businessActivity);
}
