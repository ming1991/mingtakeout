package com.heima.takeout31.model.dao;

/**
 * Created by lidongzhi on 2016/12/11.
 */

public class CacheSelectedInfo {
    public int sellerId; //田老师红烧肉这家店
    public int typeId; //粗粮主食
    public int goodsId; //馒头、
    public int count; //几个馒头

    public CacheSelectedInfo(int sellerId, int typeId, int goodsId, int count) {
        this.sellerId = sellerId;
        this.typeId = typeId;
        this.goodsId = goodsId;
        this.count = count;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
