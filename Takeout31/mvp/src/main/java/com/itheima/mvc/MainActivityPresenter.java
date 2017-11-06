package com.itheima.mvc;

/**
 * Created by lidongzhi on 2016/12/7.
 */

public class MainActivityPresenter {

    MainActivity mMainActivity;

    public MainActivityPresenter(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    public void login(String username, String password){
        //1.创建一个javabean
        final User user=new User();
        user.username=username;
        user.password=password;
        //2.开启一个子线程
        new Thread(){
            @Override
            public void run() {
                //3.调用Controller层去执行登录业务
                UserLoginNet net=new UserLoginNet();

                if(net.sendUserLoginInfo(user)){
                    // 登陆成功
                    mMainActivity.success();
                }else{
                    //登陆失败
                    mMainActivity.failed();
                }

            }
        }.start();
    }
}
