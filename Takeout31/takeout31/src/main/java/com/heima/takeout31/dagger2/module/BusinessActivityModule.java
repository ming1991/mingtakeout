package com.heima.takeout31.dagger2.module;

import com.heima.takeout31.presenter.BusinessActivityPresenter;
import com.heima.takeout31.ui.activity.BusinessActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lidongzhi on 2016/12/10.
 */
@Module
public class BusinessActivityModule {

    BusinessActivity mBusinessActivity;

    public BusinessActivityModule(BusinessActivity businessActivity) {
        mBusinessActivity = businessActivity;
    }

    @Provides
    BusinessActivityPresenter providerBusinessActivityPresenter(){
        return new BusinessActivityPresenter(mBusinessActivity);
    }
}
