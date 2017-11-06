package com.heima.takeout31.util;

import android.app.Application;

import com.heima.takeout31.model.dao.CacheSelectedInfo;
import com.heima.takeout31.model.net.User;

import java.util.concurrent.CopyOnWriteArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lidongzhi on 2016/12/7.
 */

public class TakeoutApp extends Application {

    CopyOnWriteArrayList<CacheSelectedInfo> infos = new CopyOnWriteArrayList(); //线程安全

    public int queryCacheSelectedInfoByGoodsId(int goodsId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getGoodsId() == goodsId){
                count = info.getCount();
                break;
            }
        }
        return count;
    }

    public int queryCacheSelectedInfoByTypeId(int typeId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getTypeId() == typeId){
                count = count + info.getCount();
            }
        }
        return count;
    }

    public int queryCacheSelectedInfoBySellerId(int sellerId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getSellerId() == sellerId){
                count = count + info.getCount();
            }
        }
        return count;
    }

    public void addCacheSelectedInfo(CacheSelectedInfo info) {
        infos.add(info);
    }

    public void clearCacheSelectedInfo(int sellerId){
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getSellerId() == sellerId){
                infos.remove(info);
            }
        }
    }

    public void deleteCacheSelectedInfo(int goodsId) {
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if (info.getGoodsId() == goodsId) {
                infos.remove(info);
                break;
            }
        }
    }

    public void updateCacheSelectedInfo(int goodsId, int operation) {
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if (info.getGoodsId() == goodsId) {
                switch (operation) {
                    case Constants.ADD:
                        info.setCount(info.getCount() + 1);
                        break;
                    case Constants.MINUS:
                        info.setCount(info.getCount() - 1);
                        break;
                }

            }
        }
    }

    public static TakeoutApp sInstance;
    public static User sUser;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sUser = new User();
        sUser.setId(-1); //未登录

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
