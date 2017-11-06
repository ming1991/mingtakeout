package com.heima.takeout31.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.heima.takeout31.R;
import com.heima.takeout31.model.dao.RecepitAddressBean;
import com.heima.takeout31.model.dao.RecepitAddressDao;
import com.heima.takeout31.ui.adapter.RecepitAddressAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2016/12/11.
 */

public class RecepitAddressActivity extends AppCompatActivity {

    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.rv_receipt_address)
    RecyclerView mRvReceiptAddress;
    @InjectView(R.id.tv_add_address)
    TextView mTvAddAddress;
    private RecepitAddressDao mAddressDao;
    private RecepitAddressAdapter mAddressAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_address);
        ButterKnife.inject(this);
        mAddressDao = new RecepitAddressDao(this);
        mAddressAdapter = new RecepitAddressAdapter(this);
        mRvReceiptAddress.setAdapter(mAddressAdapter);
        mRvReceiptAddress.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //查找地址
        List<RecepitAddressBean> addressBeanList =  mAddressDao.queryRecepitAddress(31);
        if(addressBeanList!=null && addressBeanList.size()>0){
            mAddressAdapter.setRecepitAddressBeanList(addressBeanList);
        }
    }

    @OnClick({R.id.ib_back, R.id.tv_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(this,AddRecepitAddressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
