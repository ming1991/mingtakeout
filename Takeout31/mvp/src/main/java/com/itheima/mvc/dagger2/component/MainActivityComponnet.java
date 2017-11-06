package com.itheima.mvc.dagger2.component;

import com.itheima.mvc.MainActivity;
import com.itheima.mvc.dagger2.module.MainActivityModule;

import dagger.Component;

/**
 * Created by lidongzhi on 2016/12/7.
 */
@Component(modules = MainActivityModule.class)
public interface MainActivityComponnet {
    void in(MainActivity mainActivity);
}
