package com.itheima.mvc.dagger2.module;

import com.itheima.mvc.MainActivity;
import com.itheima.mvc.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lidongzhi on 2016/12/7.
 */
@Module
public class MainActivityModule {

    MainActivity mMainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Provides
    MainActivityPresenter providerMainActivityPresenter(){
        return new MainActivityPresenter(mMainActivity);
    }
}
