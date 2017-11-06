package com.heima.takeout31.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.heima.takeout31.R;
import com.heima.takeout31.model.net.Seller;
import com.heima.takeout31.ui.activity.BusinessActivity;
import com.heima.takeout31.util.TakeoutApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2016/12/7.
 */

public class HomeRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Seller> mNearbySellers = new ArrayList<>();
    private List<Seller> mOtherSellers = new ArrayList<>();

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_DIVISION = 1;
    public static final int TYPE_SELLER = 2;

    public static final int GROUP_SIZE = 10;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TITLE;
        } else if (position == mNearbySellers.size() + 1) {
            //第一个分割线
            return TYPE_DIVISION;
        } else if ((position - 1 - mNearbySellers.size()) % (GROUP_SIZE + 1) == 0) {
            return TYPE_DIVISION;
        } else {
            return TYPE_SELLER;
        }
    }

    public HomeRvAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(List<Seller> mNearbySellers, List<Seller> otherSellers) {
        this.mNearbySellers = mNearbySellers;
        mOtherSellers = otherSellers;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //支持3种Holder
        View itemView = null;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_TITLE) {
            itemView = View.inflate(mContext, R.layout.item_title, null);
            viewHolder = new TitleHolder(itemView);
        } else if (viewType == TYPE_DIVISION) {
            itemView = View.inflate(mContext, R.layout.item_division, null);
            viewHolder = new DivisionHolder(itemView);
        } else if (viewType == TYPE_SELLER) {
            itemView = View.inflate(mContext, R.layout.item_seller, null);
            viewHolder = new SellerHolder(itemView);
        } else {
            Toast.makeText(mContext, "竟然有第4种holder", Toast.LENGTH_SHORT).show();
        }
        return viewHolder;
    }

    //设置数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_TITLE) {
            TitleHolder titleHolder = (TitleHolder) holder;
            titleHolder.setData();
        } else if (viewType == TYPE_DIVISION) {
            DivisionHolder divisionHolder = (DivisionHolder) holder;
            divisionHolder.setData("-----------我是华丽的分割线-----------------");
        } else if (viewType == TYPE_SELLER) {
            SellerHolder sellerHolder = (SellerHolder) holder;
            //TODO:当前区分不了附近或者其他,
//            sellerHolder.setData(mDatas.get(position));
            //把position改成index,集合下标
            int index;
            if (position < mNearbySellers.size() + 1) {
                //在第一个分割线之前，肯定是附近商家
                index = position - 1;
                sellerHolder.setData(mNearbySellers.get(index));
            } else {
                index = position - 1 - mNearbySellers.size() - 1; //去除头，附近、第一个分割线
                //还需要减去其他商家内部的分割线个数
                index -= index / (GROUP_SIZE + 1);
                sellerHolder.setData(mOtherSellers.get(index));
            }
        }

    }

    //TODO:实现一个分割线的需求，区别附近商家和其他商家
    //TODO: 头布局，（附近1-附近9），分割线，（其他1-其他10），分割线，（其他11-其他20），分割线，（其他21-其他25）
    //1.支持3种布局，其中商家有附近和其他之分
    //2.确定那个position对应哪种类型，尤其是分割线
    @Override
    public int getItemCount() {
        if (mNearbySellers == null && mOtherSellers == null) {
            //用户网络奔溃或者进入无人区、无信号区，提示用户
            //TODO:重视异常情况
            Toast.makeText(mContext, "请检查手机信号", Toast.LENGTH_SHORT).show();
            return 0;
        }
        int count = 1;  //头布局
        if (mNearbySellers != null && mNearbySellers.size() > 0) {
            count += mNearbySellers.size();
            count += 1; //第一个分割线
        }
        count += mOtherSellers.size();
        count += mOtherSellers.size() / GROUP_SIZE;
        //25个是2个分割线，30个也是2个，但是31个就是3个分割线，也就是刚好整除，需要减去一个
        if (mOtherSellers.size() % GROUP_SIZE == 0) {
            count -= 1;
        }
        return count;
    }

    class SellerHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tvCount)
        TextView mTvCount;
        @InjectView(R.id.tv_title)
        TextView mTvTitle;
        @InjectView(R.id.ratingBar)
        RatingBar mRatingBar;
        @InjectView(R.id.tv_home_sale)
        TextView mTvHomeSale;
        @InjectView(R.id.tv_home_send_price)
        TextView mTvHomeSendPrice;
        @InjectView(R.id.tv_home_distance)
        TextView mTvHomeDistance;
        private Seller mSeller;

        SellerHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BusinessActivity.class);
                    intent.putExtra("seller", mSeller);
                    int  selectInfoCount = TakeoutApp.sInstance.queryCacheSelectedInfoBySellerId(mSeller.getId());
                    boolean hasSelectInfo = false;
                    if(selectInfoCount>0){
                        hasSelectInfo = true;
                    }else{
                        hasSelectInfo = false;
                    }
                    intent.putExtra("hasSelectInfo", hasSelectInfo);
                    mContext.startActivity(intent);
                }
            });
        }

        public void setData(Seller seller) {
            this.mSeller = seller;
            mTvTitle.setText(seller.getName());
            mRatingBar.setRating(Float.parseFloat(seller.getScore()));
            mTvHomeSale.setText(seller.getSale());
            mTvHomeSendPrice.setText("￥" + seller.sendPrice + "起送/配送费￥" + seller.getDeliveryFee());
            mTvHomeDistance.setText(seller.getDistance());
        }
    }

    class TitleHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.slider)
        SliderLayout mSliderLayout;


        TitleHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setData() {
            testData(mContext);
        }

        private void testData(Context context) {
            HashMap<String, String> url_maps = new HashMap<String, String>();
            url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
            url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
            url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
            url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

            for (String desc : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(itemView.getContext());
                textSliderView
                        .description(desc)
                        .image(url_maps.get(desc));
                mSliderLayout.addSlider(textSliderView);
            }
        }
    }

    class DivisionHolder extends RecyclerView.ViewHolder {

        DivisionHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setData(String data) {

        }
    }
}
