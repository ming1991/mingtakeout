package com.heima.takeout31.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.heima.takeout31.R;
import com.heima.takeout31.dagger2.component.DaggerBusinessActivityComponent;
import com.heima.takeout31.dagger2.module.BusinessActivityModule;
import com.heima.takeout31.model.net.GoodsInfo;
import com.heima.takeout31.model.net.Seller;
import com.heima.takeout31.presenter.BusinessActivityPresenter;
import com.heima.takeout31.ui.adapter.BusniessFragmentAdapter;
import com.heima.takeout31.ui.adapter.CartRvAdapter;
import com.heima.takeout31.ui.fragment.CommentsFragment;
import com.heima.takeout31.ui.fragment.GoodsFragment;
import com.heima.takeout31.ui.fragment.SellerFragment;
import com.heima.takeout31.util.PriceFormater;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2016/12/10.
 */
public class BusinessActivity extends AppCompatActivity {

    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.ib_menu)
    ImageButton mIbMenu;
    @InjectView(R.id.vp)
    ViewPager mVp;
    @InjectView(R.id.bottomSheetLayout)
    BottomSheetLayout mBottomSheetLayout;
    @InjectView(R.id.imgCart)
    ImageView mImgCart;
    @InjectView(R.id.tvSelectNum)
    TextView mTvSelectNum;
    @InjectView(R.id.tvCountPrice)
    TextView mTvCountPrice;
    @InjectView(R.id.tvSendPrice)
    TextView mTvSendPrice;
    @InjectView(R.id.tvDeliveryFee)
    TextView mTvDeliveryFee;
    @InjectView(R.id.tvSubmit)
    TextView mTvSubmit;
    @InjectView(R.id.bottom)
    LinearLayout mBottom;
    @InjectView(R.id.fl_Container)
    FrameLayout mFlContainer;
    @InjectView(R.id.tabs)
    TabLayout mTabs;
    public Seller mSeller;

    @Inject
    public BusinessActivityPresenter mBusinessActivityPresenter;
    private RecyclerView mRvCart;
    private CartRvAdapter mCartRvAdapter;
    public boolean mHasSelectInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.inject(this);
        DaggerBusinessActivityComponent.builder().businessActivityModule(new BusinessActivityModule(this)).build().in(this);
        processIntent();
        initFragments();
        BusniessFragmentAdapter adapter = new BusniessFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mVp.setAdapter(adapter);
        mTabs.setupWithViewPager(mVp);
    }

    private void processIntent() {
        if(getIntent()!=null){
            mSeller = (Seller) getIntent().getSerializableExtra("seller");
            mTvDeliveryFee.setText("另需配送费" + PriceFormater.format(Float.parseFloat(mSeller.getDeliveryFee())));
            mTvSendPrice.setText(PriceFormater.format(Float.parseFloat(mSeller.sendPrice)) + "元起送");

            mHasSelectInfo = getIntent().getBooleanExtra("hasSelectInfo", false);
        }

    }

    public List<Fragment> mFragmentList = new ArrayList<>();

    private void initFragments() {
        mFragmentList.add(new GoodsFragment());
        mFragmentList.add(new CommentsFragment());
        mFragmentList.add(new SellerFragment());
    }

    @OnClick({R.id.ib_back, R.id.tvSubmit,R.id.bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tvSubmit:
                //提交订单
                Intent intent = new Intent(this,ConfirmActivity.class);
                intent.putExtra("seller", mSeller);
                intent.putExtra("cartList", (Serializable) mBusinessActivityPresenter.getCartData());
                startActivity(intent);
                break;
            case R.id.bottom:
                showCart();
                break;
        }
    }

    View bottomSheetView;
    public void showCart() {
        if (bottomSheetView == null) {
            //加载要显示的布局
            bottomSheetView = LayoutInflater.from(this).inflate(R.layout.cart_list, (ViewGroup) getWindow().getDecorView(), false);
            mRvCart = (RecyclerView) bottomSheetView.findViewById(R.id.rvCart);
            TextView tvClearCart = (TextView) bottomSheetView.findViewById(R.id.tvClear);
            tvClearCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearCart();
                }
            });
            mCartRvAdapter = new CartRvAdapter(this);
            mRvCart.setAdapter(mCartRvAdapter);
            mRvCart.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//            mRvCart.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL));
        }
        //判断BottomSheetLayout内容是否显示
        if (mBottomSheetLayout.isSheetShowing()) {
            //关闭内容显示
            mBottomSheetLayout.dismissSheet();
        } else {
            //显示BottomSheetLayout里面的内容
            List<GoodsInfo> cartList = mBusinessActivityPresenter.getCartData();
            mCartRvAdapter.setCartList(cartList);
            if(cartList.size()>0) {
                mBottomSheetLayout.showWithSheetView(bottomSheetView);
            }
        }
    }

    private void clearCart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("清空购物车");
        builder.setPositiveButton("是的，我要减肥", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //2.更新购物车UI
                mBusinessActivityPresenter.clearCart();

                //1.是要更新购物车自己
                mCartRvAdapter.notifyDataSetChanged();

                showCart();
            }
        });
        builder.setNegativeButton("不，我还要继续吃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void removeImageButton(ImageButton ib) {
        mFlContainer.removeView(ib);
    }

    public void addImageButton(ImageButton ib, int width, int height) {
        mFlContainer.addView(ib, width, height);
    }

    public int[] getImgCartLocation() {
        int[] location = new int[2];
        mImgCart.getLocationInWindow(location);
        return location;
    }

    public void updateCartUi(int cartCount, float countPrice) {
        if(cartCount>0) {
            mTvSelectNum.setText(cartCount + "");
            mTvSelectNum.setVisibility(View.VISIBLE);
        }else{
            mTvSelectNum.setVisibility(View.GONE);
        }
        mTvCountPrice.setText(PriceFormater.format(countPrice));

        if(countPrice >= Float.parseFloat(mSeller.sendPrice)){
            //显示可以结算
            mTvSubmit.setVisibility(View.VISIBLE);
            mTvSendPrice.setVisibility(View.GONE);
        }else{
            mTvSubmit.setVisibility(View.GONE);
            mTvSendPrice.setVisibility(View.VISIBLE);
        }
    }
}
