package com.heima.takeout31.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heima.takeout31.model.net.ResponseInfo;
import com.heima.takeout31.model.net.Seller;
import com.heima.takeout31.ui.fragment.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

/**
 * Created by lidongzhi on 2016/12/7.
 */

public class HomeFragmentPresenter extends BasePresenter {

    HomeFragment mHomeFragment;

    public HomeFragmentPresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    public void getHomeInfo(){
        Call<ResponseInfo> homeCall = mTakeoutService.getHomeInfo();
        homeCall.enqueue(mCallback);
    }

    @Override
    protected void parserJson(String data) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(data);
            String nearby = jsonObject.getString("nearbySellerList");
            List<Seller> nearbySellers = gson.fromJson(nearby, new TypeToken<List<Seller>>(){}.getType());
            String other = jsonObject.getString("otherSellerList");
            List<Seller> otherSellers = gson.fromJson(other, new TypeToken<List<Seller>>(){}.getType());
            mHomeFragment.mAdapter.setDatas(nearbySellers,otherSellers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
