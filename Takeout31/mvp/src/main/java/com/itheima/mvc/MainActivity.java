package com.itheima.mvc;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itheima.mvc.dagger2.component.DaggerMainActivityComponnet;
import com.itheima.mvc.dagger2.module.MainActivityModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    // 业务代码：
    // 界面业务
    // 业务流程的处理

    private EditText mUsername;
    private EditText mPassword;
    private ProgressDialog dialog;

    @Inject
    MainActivityPresenter mMainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.xml布局属于MVC的V层
        //2.addView(view)才是真正的属于MVC的V层
//        TextView tv = new TextView(this);
//        addView();

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        dialog = new ProgressDialog(this);

        //TODO:需要解耦，破除组合关系 (1.清单文件，依赖注入，反射；2.静态工厂；3.单例模式；
        // TODO:4.使用官方推荐的注解方式Dagger2，也是依赖注入，反射）
//        mMainActivityPresenter = new MainActivityPresenter(this);
        DaggerMainActivityComponnet.builder().mainActivityModule(new MainActivityModule(this)).build().in(this);
    }

    /**
     * 按钮点击
     *
     * @param view
     */
    public void login(View view) {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        boolean checkUserInfo = checkUserInfo(username,password);

        if(checkUserInfo){
            dialog.show();
            //TODO:从此行开始就有了非view的代码，使用MVP来解决，P全称Presenter
            mMainActivityPresenter.login(username, password);
        }else{
            Toast.makeText(MainActivity.this, R.string.check_info, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检验用户输入——界面相关逻辑处理
     * @param username
     * @param password
     * @return
     */
    private boolean checkUserInfo(String username, String password) {
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }

    /**
     * 登陆成功
     */
    public void success(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 登陆成功
                dialog.dismiss();
                Toast.makeText(MainActivity.this, getString(R.string.welcome)+mUsername.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 登陆失败
     */
    public void failed(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 登陆失败
                dialog.dismiss();
                Toast.makeText(MainActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
