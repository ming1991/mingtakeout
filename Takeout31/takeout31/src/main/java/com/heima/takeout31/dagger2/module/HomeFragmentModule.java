package com.heima.takeout31.dagger2.module;

import com.heima.takeout31.presenter.HomeFragmentPresenter;
import com.heima.takeout31.ui.fragment.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lidongzhi on 2016/12/7.
 */
@Module
public class HomeFragmentModule {

    HomeFragment mHomeFragment;

    public HomeFragmentModule(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    @Provides
    HomeFragmentPresenter providerHomeFragmentPresenter(){
        return new HomeFragmentPresenter(mHomeFragment);
    }
}
