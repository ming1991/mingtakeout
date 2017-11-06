package com.heima.takeout31.model.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by lidongzhi on 2016/12/11.
 */

public class RecepitAddressDao {

    private  Dao<RecepitAddressBean, Integer> mAddressDao;

    public RecepitAddressDao(Context context) {
        TakeoutOpenHelper openHelper = new TakeoutOpenHelper(context);
        try {
            mAddressDao = openHelper.getDao(RecepitAddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRecepitAddress(RecepitAddressBean addressBean){
        try {
            mAddressDao.create(addressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecepitAddress(RecepitAddressBean addressBean){
        try {
            mAddressDao.delete(addressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRecepitAddress(RecepitAddressBean addressBean){
        try {
            mAddressDao.update(addressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RecepitAddressBean> queryRecepitAddress(int userId){
        try {
           return mAddressDao.queryForEq("userId", userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
