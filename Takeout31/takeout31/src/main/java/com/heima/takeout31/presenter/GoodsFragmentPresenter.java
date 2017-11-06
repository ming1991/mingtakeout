package com.heima.takeout31.presenter;

import android.widget.AbsListView;

import com.google.gson.Gson;
import com.heima.takeout31.model.net.BusinessInfo;
import com.heima.takeout31.model.net.GoodsInfo;
import com.heima.takeout31.model.net.GoodsTypeInfo;
import com.heima.takeout31.model.net.ResponseInfo;
import com.heima.takeout31.ui.activity.BusinessActivity;
import com.heima.takeout31.ui.fragment.GoodsFragment;
import com.heima.takeout31.util.TakeoutApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.heima.takeout31.util.TakeoutApp.sInstance;

/**
 * Created by lidongzhi on 2016/12/10.
 */

public class GoodsFragmentPresenter extends BasePresenter {

    private GoodsFragment mGoodsFragment;
    public List<GoodsInfo> mAllGoodsInfoList;
    public List<GoodsTypeInfo> mGoodsTypeInfoList;
    private final boolean mHasSelectInfo;

    public GoodsFragmentPresenter(GoodsFragment goodsFragment) {
        mGoodsFragment = goodsFragment;
        mHasSelectInfo = ((BusinessActivity) mGoodsFragment.getActivity()).mHasSelectInfo;
    }

    public void loadGoodsInfo(String sellerId){
        Call<ResponseInfo> goodsInfoCall =  mTakeoutService.loadGoodsInfo(sellerId);
        goodsInfoCall.enqueue(mCallback);
    }

    @Override
    protected void parserJson(String data) {
        //逐个查看商品是否是购物车缓存的

        mAllGoodsInfoList = new ArrayList<>();

        Gson gson = new Gson();
        BusinessInfo businessInfo = gson.fromJson(data, BusinessInfo.class);
        //先拿类别信息，再拿具体商品信息
        mGoodsTypeInfoList = businessInfo.getList();

        for(int i = 0; i< mGoodsTypeInfoList.size(); i++){
           GoodsTypeInfo typeInfo = mGoodsTypeInfoList.get(i);
            int typeSelectCount =0;
            if(mHasSelectInfo){
                typeSelectCount = sInstance.queryCacheSelectedInfoByTypeId(typeInfo.getId());
                typeInfo.setCount(typeSelectCount);
            }
            List<GoodsInfo> goodsInfos = typeInfo.getList();
            for(int j=0;j<goodsInfos.size();j++){
                GoodsInfo goodsInfo = goodsInfos.get(j);
                if(typeSelectCount>0){
                   int selectCount =  TakeoutApp.sInstance.queryCacheSelectedInfoByGoodsId(goodsInfo.getId());
                    goodsInfo.setCount(selectCount);
                }
                mAllGoodsInfoList.add(goodsInfo);
                //TODO:给外键赋值，做成双向绑定效果，实现多对一，服务端ORM框架（hibernate,ibatis)
                goodsInfo.setTypeId(typeInfo.getId());
                goodsInfo.setTypeName(typeInfo.getName());
            }
        }

        //请求到数据后刷新adapter
        mGoodsFragment.mGoodsTypeRvAdapter.setGoodsTypeInfoList(mGoodsTypeInfoList);
        mGoodsFragment.mGoodsInfoAdapter.setGoodsInfoList(mAllGoodsInfoList);

        //异步拿到数据以后才能监听listview滚动事件
        mGoodsFragment.mSlhlv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //当firstVisiableItem发生变化（是指商品类型发生变化）后，改变左边的选中
               int oldTypeId = mGoodsTypeInfoList.get(mGoodsFragment.mGoodsTypeRvAdapter.selectIndex).getId();
                //1.获取右侧的typeId
               int newTypeId = mAllGoodsInfoList.get(firstVisibleItem).getTypeId();
                if(newTypeId!=oldTypeId) {
                    int newSelectIndex = getTypePositionByTypeId(newTypeId);
                    //让左侧列表选中新的index
                    mGoodsFragment.mGoodsTypeRvAdapter.selectIndex = newSelectIndex;
                    mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public int getTypePositionByTypeId(int typeId) {
        int index = -1;
        for(int i = 0; i< mGoodsTypeInfoList.size(); i++){
           GoodsTypeInfo goodsTypeInfo = mGoodsTypeInfoList.get(i);
            if(goodsTypeInfo.getId() == typeId){
                index = i;
                break; //我们要的是第一个粗粮主食，所以break
            }
        }
        return index;
    }

    /**
     * 根据左侧类型，找到右侧postion,比如找粗粮主食，从上往下数，知道第93个馒头才是
     * @param typeId
     */
    public int getGoodsPostionByTypeId(int typeId) {
        int index = -1;
        for(int j = 0; j< mAllGoodsInfoList.size(); j++){
            GoodsInfo goodsInfo = mAllGoodsInfoList.get(j);
            if(goodsInfo.getTypeId() == typeId){
                index = j;
                break; //我们要的是第一个粗粮主食，所以break
            }
        }
        return index;
    }
}
