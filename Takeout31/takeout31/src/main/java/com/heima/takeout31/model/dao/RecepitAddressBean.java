package com.heima.takeout31.model.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by lidongzhi on 2016/12/11.
 */
@DatabaseTable(tableName = "t_address")
public class RecepitAddressBean implements Serializable {
    @DatabaseField(generatedId = true) //自动增长维护，auto incremnet
    public int id;
    @DatabaseField(columnName = "name")
    public String name;
    @DatabaseField(columnName = "sex")
    public String sex;
    @DatabaseField(columnName = "phone")
    public String phone;
    @DatabaseField(columnName = "phoneOther")
    public String phoneOther;
    @DatabaseField(columnName = "address")
    public String address;
    @DatabaseField(columnName = "detailAddress")
    public String detailAddress;
    @DatabaseField(columnName = "label")
    public String label;
    @DatabaseField(columnName = "userId")
    public int userId;   //外键，多对一

    public RecepitAddressBean() {
    }

    public RecepitAddressBean(int id, String name, String sex, String phone,
                              String phoneOther, String address, String detailAddress, String label, int userId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.phoneOther = phoneOther;
        this.address = address;
        this.detailAddress = detailAddress;
        this.label = label;
        this.userId = userId;
    }
}
