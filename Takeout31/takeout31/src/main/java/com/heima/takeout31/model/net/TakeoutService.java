package com.heima.takeout31.model.net;

import com.heima.takeout31.util.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 外卖服务，包含本外卖项目的所有接口
 */
public interface TakeoutService {

    @GET(Constants.HOME)
    Call<ResponseInfo> getHomeInfo();

    @GET(Constants.LOGIN)
    Call<ResponseInfo> loginBySms(@Query("phone") String phone, @Query("type") String type);

    @GET(Constants.ORDER_LIST)
    Call<ResponseInfo> getOrderList(@Query("userId") String userId);

    @GET(Constants.BUSINESS)
    Call<ResponseInfo> loadGoodsInfo(@Query("sellerId") String sellerId);
}
