package com.heima.takeout31.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lidongzhi on 2016/12/10.
 */
public class BusniessFragmentAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragmentList;
    private String[] mTitles = new String[]{"商品","评论","商家"};

    public BusniessFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        if(mFragmentList!=null){
            return mFragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(mFragmentList!=null){
            return mFragmentList.size();
        }
        return 0;
    }
}
