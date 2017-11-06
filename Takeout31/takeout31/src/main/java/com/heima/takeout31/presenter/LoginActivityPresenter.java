package com.heima.takeout31.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.heima.takeout31.model.dao.TakeoutOpenHelper;
import com.heima.takeout31.model.net.ResponseInfo;
import com.heima.takeout31.model.net.User;
import com.heima.takeout31.ui.activity.LoginActivity;
import com.heima.takeout31.util.TakeoutApp;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import retrofit2.Call;

/**
 * Created by lidongzhi on 2016/12/8.
 */

public class LoginActivityPresenter extends BasePresenter {

    LoginActivity mLoginActivity;

    public LoginActivityPresenter(LoginActivity loginActivity) {
        mLoginActivity = loginActivity;
    }

    public void loginBySms(String phone, String type){
        // 15502027786 有可能是手机，也有可能是普通账号，因此必须有个type做区分，比如type=0，普通，type=1,手机
        Log.e("sms", "开始登录");
        Call<ResponseInfo> loginCall = mTakeoutService.loginBySms(phone, type);
        loginCall.enqueue(mCallback);
    }

    @Override
    protected void parserJson(String data) {
        Gson gson = new Gson();
        User user = gson.fromJson(data, User.class);
        Log.e("sms", user.getPhone() + ",登录成功！！！");
        //缓存登录成功的状态
        //1.内存缓存
        TakeoutApp.sUser = user;

        //2.硬盘缓存（sharedpreference，文件缓存，sqlite)
        //OrmLite,Orm是数据库表和javabean的映射关系
       //username = zhangshan && age == 23
        TakeoutOpenHelper takeoutOpenHelper = new TakeoutOpenHelper(mLoginActivity);
        AndroidDatabaseConnection connection = new AndroidDatabaseConnection(takeoutOpenHelper.getWritableDatabase(), true);
        Savepoint savepoint = null;
        boolean isLoginOk = false;
        try {
            Dao<User,Integer> userDao = takeoutOpenHelper.getDao(User.class);
            savepoint = connection.setSavePoint("start");
            connection.setAutoCommit(false);

//            userDao.create(user); //在user表中新建一行
//            userDao.createIfNotExists(user); //防重复处理，此业务逻辑包含两个步骤，第一个查一查，有有没有前科，没有才创建
            List<User> oldUser = userDao.queryForEq("phone", "15502027786");
            if(oldUser.size() > 0) {
                userDao.update(user);
//                TencentTjSdk.action("login", "old");
                Log.e("sms", "更新用户。。。。。");
            }else {
                userDao.create(user);
//                TencentTjSdk.action("login", "new");
                Log.e("sms", "创建用户。。。。。");
            }
            connection.commit(savepoint);
            Log.e("sms", "登录事务成功，保存到了数据库");
            isLoginOk = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(savepoint);
                Log.e("sms", "登录出错，事务回滚");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        mLoginActivity.onLogin(isLoginOk);
    }
}
