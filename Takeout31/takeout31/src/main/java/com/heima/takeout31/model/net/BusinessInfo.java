package com.heima.takeout31.model.net;

import java.util.List;

/**
 * 田老师红烧肉这家店
 */
public class BusinessInfo {
    private List<GoodsTypeInfo> list;

    public BusinessInfo() {
    }

    public BusinessInfo(List<GoodsTypeInfo> list) {
        this.list = list;
    }

    public List<GoodsTypeInfo> getList() {
        return list;
    }

    public void setList(List<GoodsTypeInfo> list) {
        this.list = list;
    }
}
