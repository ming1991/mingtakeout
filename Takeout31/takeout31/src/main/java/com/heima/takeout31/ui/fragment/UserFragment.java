package com.heima.takeout31.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heima.takeout31.R;
import com.heima.takeout31.ui.activity.LoginActivity;
import com.heima.takeout31.util.TakeoutApp;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2016/12/7.
 */
public class UserFragment extends Fragment {

    @InjectView(R.id.tv_user_setting)
    ImageView mTvUserSetting;
    @InjectView(R.id.iv_user_notice)
    ImageView mIvUserNotice;
    @InjectView(R.id.login)
    ImageView mLogin;
    @InjectView(R.id.username)
    TextView mUsername;
    @InjectView(R.id.phone)
    TextView mPhone;
    @InjectView(R.id.ll_userinfo)
    LinearLayout mLlUserinfo;
    @InjectView(R.id.iv_address_manager)
    ImageView mIvAddressManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View userView = View.inflate(getContext(), R.layout.fragment_user, null);
        ButterKnife.inject(this, userView);
        return userView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //要在回调的时候再执行
        int id = TakeoutApp.sUser.getId();
        if(id == -1){
            //登录失败
        }else{
            //登录成功
            mLlUserinfo.setVisibility(View.VISIBLE);
            mUsername.setText("你好，欢迎" + TakeoutApp.sUser.getName());
            mPhone.setText(TakeoutApp.sUser.getPhone());
            mLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.login)
    public void onClick() {
        //跳转到登录页面
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
