package com.heima.takeout31.model.net;

import java.util.List;

/**
 * 饮料类别
 */
public class GoodsTypeInfo {
    private int id;
    private String info;
    private List<GoodsInfo> list;
    private String name;
    private int count;//同一种类型选中的数量

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public GoodsTypeInfo() {
    }

    public GoodsTypeInfo(int id, String info, List<GoodsInfo> list, String name) {
        this.id = id;
        this.info = info;
        this.list = list;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<GoodsInfo> getList() {
        return list;
    }

    public void setList(List<GoodsInfo> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
