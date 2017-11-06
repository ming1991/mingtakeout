package com.heima.takeout31.ui.fragment;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heima.takeout31.R;
import com.heima.takeout31.dagger2.component.DaggerHomeFragmentComponent;
import com.heima.takeout31.dagger2.module.HomeFragmentModule;
import com.heima.takeout31.presenter.HomeFragmentPresenter;
import com.heima.takeout31.ui.adapter.HomeRvAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2016/12/7.
 */
public class HomeFragment extends Fragment {

    @InjectView(R.id.rv_home)
    RecyclerView mRvHome;
    @InjectView(R.id.home_tv_address)
    TextView mHomeTvAddress;
    @InjectView(R.id.ll_title_search)
    LinearLayout mLlTitleSearch;
    @InjectView(R.id.ll_title_container)
    LinearLayout mLlTitleContainer;
    public HomeRvAdapter mAdapter;

    @Inject
    HomeFragmentPresenter mHomeFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = View.inflate(getContext(), R.layout.fragment_home, null);
        ButterKnife.inject(this, homeView);
        mAdapter = new HomeRvAdapter(getContext());
        mRvHome.setAdapter(mAdapter);
        //配置recycleview
        mRvHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return homeView;
    }

    List<String> mNearby = new ArrayList<>();
    List<String> mOthers= new ArrayList<>();
    int sumY = 0;
    float distance = 200.0f;
    ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //测试数据，附近商家10个，其他商家25个
//        for(int i=0;i<7;i++){
//            mNearby.add("我是数据" + i);
//        }
//        for(int i=0;i<35;i++){
//            mOthers.add("我是数据" + i);
//        }
        //请求网络数据，属于presenter
//        mHomeFragmentPresenter = new HomeFragmentPresenter(this);
        DaggerHomeFragmentComponent.builder().homeFragmentModule(new HomeFragmentModule(this)).build().in(this);
        mHomeFragmentPresenter.getHomeInfo();
        mRvHome.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //d是delta,表示差值
//                Log.e("home", "dy:" + dy);
                sumY += dy;

                int bgColor;
                int startColor = 0x553190E8;
                int endColor = 0xff3190E8;
                if(sumY<=0){
                    bgColor = startColor;
                }else if(sumY > distance){
                    bgColor = endColor;
                }else{
                    //按百分比计算alpha值
                    bgColor = (int) mArgbEvaluator.evaluate(sumY / distance , startColor, endColor);
                }
                mLlTitleContainer.setBackgroundColor(bgColor);
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
