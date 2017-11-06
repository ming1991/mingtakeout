package com.heima.takeout31.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heima.takeout31.R;
import com.heima.takeout31.model.dao.RecepitAddressBean;
import com.heima.takeout31.model.net.GoodsInfo;
import com.heima.takeout31.model.net.Seller;
import com.heima.takeout31.util.PriceFormater;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2016/12/13.
 */
public class ConfirmActivity extends AppCompatActivity {

    private static final int GET_ADDRESS = 10000;
    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.iv_location)
    ImageView mIvLocation;
    @InjectView(R.id.tv_name)
    TextView mTvName;
    @InjectView(R.id.tv_sex)
    TextView mTvSex;
    @InjectView(R.id.tv_phone)
    TextView mTvPhone;
    @InjectView(R.id.tv_label)
    TextView mTvLabel;
    @InjectView(R.id.tv_address)
    TextView mTvAddress;
    @InjectView(R.id.rl_location)
    RelativeLayout mRlLocation;
    @InjectView(R.id.iv_arrow)
    ImageView mIvArrow;
    @InjectView(R.id.iv_icon)
    ImageView mIvIcon;
    @InjectView(R.id.tv_seller_name)
    TextView mTvSellerName;
    @InjectView(R.id.ll_select_goods)
    LinearLayout mLlSelectGoods;
    @InjectView(R.id.tv_deliveryFee)
    TextView mTvDeliveryFee;
    @InjectView(R.id.tv_CountPrice)
    TextView mTvCountPrice;
    @InjectView(R.id.tvSubmit)
    TextView mTvSubmit;
    private Seller mSeller;
    private List<GoodsInfo> mCartList;
    private float mCountPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.inject(this);
        processIntent();
    }

    private void processIntent() {
        if(getIntent()!=null){
            mSeller = (Seller) getIntent().getSerializableExtra("seller");
            mCartList = (List<GoodsInfo>) getIntent().getSerializableExtra("cartList");
            mTvSellerName.setText(mSeller.getName());
            mTvDeliveryFee.setText(PriceFormater.format(Float.parseFloat(mSeller.getDeliveryFee())));
            mCountPrice = 0.0f;
            for(GoodsInfo goodsInfo : mCartList){
                mCountPrice += goodsInfo.getNewPrice() * goodsInfo.getCount();
            }
            mTvCountPrice.setText(PriceFormater.format(mCountPrice + Float.parseFloat(mSeller.getDeliveryFee())));
        }
    }

    @OnClick({R.id.ib_back, R.id.rl_location, R.id.tvSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.rl_location:
                //调到选择地址页面
                Intent intent2 = new Intent(this,RecepitAddressActivity.class);
                startActivityForResult(intent2, GET_ADDRESS);

                break;
            case R.id.tvSubmit:
                //跳转到支付页面
                Intent intent = new Intent(this,OnlinePaymentActivity.class);
                intent.putExtra("seller", mSeller);
                intent.putExtra("cartList", (Serializable) mCartList);
                intent.putExtra("countPrice", mCountPrice);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200){
            //成功啦
            RecepitAddressBean addressBean = (RecepitAddressBean) data.getSerializableExtra("addressBean");
            mTvName.setText(addressBean.name);
            mTvSex.setText(addressBean.sex);
            mTvPhone.setText(addressBean.phone + "," + addressBean.phoneOther);
            mTvAddress.setText(addressBean.address + "," + addressBean.detailAddress);
        }

    }
}
