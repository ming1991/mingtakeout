package com.heima.takeout31.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.heima.takeout31.R;
import com.heima.takeout31.presenter.LoginActivityPresenter;
import com.heima.takeout31.util.SMSUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by lidongzhi on 2016/12/8.
 */
public class LoginActivity extends AppCompatActivity {

    private static final int TIME_DOWN = -1;
    private static final int TIME_OK = 0;
    @InjectView(R.id.iv_user_back)
    ImageView mIvUserBack;
    @InjectView(R.id.iv_user_password_login)
    TextView mIvUserPasswordLogin;
    @InjectView(R.id.et_user_phone)
    EditText mEtUserPhone;
    @InjectView(R.id.tv_user_code)
    TextView mTvUserCode;
    @InjectView(R.id.et_user_code)
    EditText mEtUserCode;
    @InjectView(R.id.login)
    TextView mLogin;

    LoginActivityPresenter mLoginActivityPresenter;
    private String mPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        SMSUtil.checkPermission(this);
        //1.初始化SDK
        SMSSDK.initSDK(this, "181c39656a7ae", "29eea8ebe615953942de64e153c4df34");
        //2.注册eventHandler
        SMSSDK.registerEventHandler(eventHandler);

        mLoginActivityPresenter = new LoginActivityPresenter(this);
    }

    EventHandler eventHandler = new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Log.e("sms", "提交验证码成功");
                    //TODO:验证已成功开始登录，使用手机号
//                    mLoginActivityPresenter.loginBySms(mPhone, "1");
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    Log.e("sms", "获取验证码成功");
                }else{
                    Log.e("sms", "其他事件成功");
                }
            }else{
                ((Throwable)data).printStackTrace();
                Log.e("sms", "SMS居然失败了");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @OnClick({R.id.iv_user_back, R.id.tv_user_code, R.id.login})
    public void onClick(View view) {
        mPhone = mEtUserPhone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.iv_user_back:
                finish();
                break;
            case R.id.tv_user_code:
                //校验电话号码，如果ok，获取验证码，同时开启倒计时
                if(!SMSUtil.judgePhoneNums(this, mPhone)){
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new CountDownTask()).start();
                mTvUserCode.setEnabled(false);
                //获取验证码
                SMSSDK.getVerificationCode("86", mPhone);
                break;
            case R.id.login:
                //登录，发送验证码
//                String code = mEtUserCode.getText().toString().trim();
//                Log.e("sms", "开始提交验证码：" + code);
//                SMSSDK.submitVerificationCode("86", mPhone, code);

                mLoginActivityPresenter.loginBySms(mPhone, "1");
                break;
        }
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TIME_DOWN:
                    mTvUserCode.setText("剩余时间（" + time + ")秒");
                   break;
                case TIME_OK:
                    mTvUserCode.setText("重新发送");
                    mTvUserCode.setEnabled(true);
                    time = 60;
                    break;
            }
        }
    };

    int time = 60;

    public void onLogin(boolean isLoginOk) {
        if(isLoginOk){
            finish();
        }else{
            Toast.makeText(this,"请仔细检查验证码",Toast.LENGTH_LONG).show();
        }
        //告诉userfragment是登录成功了，还是失败了
    }

    private class CountDownTask implements Runnable {
        @Override
        public void run() {
            for(;time>0;time--){
                mHandler.sendEmptyMessage(TIME_DOWN);
                SystemClock.sleep(999);
            }
            mHandler.sendEmptyMessage(TIME_OK);
        }
    }
}
