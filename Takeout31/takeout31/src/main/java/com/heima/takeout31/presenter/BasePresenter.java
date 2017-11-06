package com.heima.takeout31.presenter;

import android.widget.Toast;

import com.heima.takeout31.model.net.ResponseInfo;
import com.heima.takeout31.model.net.TakeoutService;
import com.heima.takeout31.util.Constants;
import com.heima.takeout31.util.TakeoutApp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 通用联网业务
 */

public abstract class BasePresenter {

    protected   Retrofit mRetrofit;
    protected TakeoutService mTakeoutService;

    public BasePresenter() {
         mRetrofit =  new Retrofit.Builder()
                .baseUrl(Constants.HOST).addConverterFactory(GsonConverterFactory.create())
                .build();

        mTakeoutService = mRetrofit.create(TakeoutService.class);
    }

    protected Callback mCallback = new Callback<ResponseInfo>() {
        @Override
        public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
           ResponseInfo responseInfo =  response.body();
            if(responseInfo.getCode().equals("0")){
                //成功拿到数据
                parserJson(responseInfo.getData());
            }else if(responseInfo.getCode().equals("-1")){
                //连上服务器，但是没有拿到想要的数据（空数据，服务器500异常等）
                //-1位空数据，-2为500异常
                Toast.makeText(TakeoutApp.sInstance, "服务器返回了空数据", Toast.LENGTH_LONG).show();
            }else if(responseInfo.getCode().equals("-2")){
                //连上服务器，但是没有拿到想要的数据（空数据，服务器500异常等）
                //-1位空数据，-2为500异常
                Toast.makeText(TakeoutApp.sInstance, "服务器哥们，你代码又写错了", Toast.LENGTH_LONG).show();
            }else{
                //......
            }
        }

        @Override
        public void onFailure(Call<ResponseInfo> call, Throwable t) {
            Toast.makeText(TakeoutApp.sInstance, "压根没有连上服务器", Toast.LENGTH_LONG).show();
        }
    };

    protected abstract void parserJson(String data);
}
