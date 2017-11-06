package com.itheima;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.itheima.mvc.MainActivityPresenter;

import javax.inject.Inject;

/**
 * Created by lidongzhi on 2016/12/7.
 */

public class OrderActivity extends AppCompatActivity {
    @Inject
    MainActivityPresenter mMainActivityPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取用户订单的时候必须先登录
//        MainActivity mainActivity = new MainActivity(); //她是小三，不合法，因为不是清单文件生成的
//        MainActivityPresenter mainActivityPresenter = new MainActivityPresenter(mainActivity);

    }
}
