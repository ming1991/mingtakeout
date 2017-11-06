package com.heima.takeout31.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.takeout31.R;
import com.heima.takeout31.model.net.Order;
import com.heima.takeout31.ui.activity.OrderDetailActivity;
import com.heima.takeout31.util.OrderObservable;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2016/12/8.
 */
public class OrderRvAdapter extends RecyclerView.Adapter implements Observer {

    private Context mContext;
    private List<Order> mOrderList;


    public OrderRvAdapter(Context context, List<Order> orderList) {
        mContext = context;
        mOrderList = orderList;
        OrderObservable.getInstance().addObserver(this);
    }

    public void setOrderList(List<Order> orderList) {
        mOrderList = orderList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = View.inflate(mContext, R.layout.item_order_item, null);
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_order_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mOrderList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mOrderList!=null){
            return mOrderList.size();
        }
        return 0;
    }

    @Override
    public void update(Observable observable, Object data) {
        HashMap<String,String> map = (HashMap<String, String>) data;
        String orderId = map.get("orderId");
        String type = map.get("type");
        for(int i=0;i<mOrderList.size();i++){
            Order order = mOrderList.get(i);
            if(order.getId().equals(orderId)){
                order.setType(type);
//                notifyDataSetChanged();
                notifyItemChanged(i);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.iv_order_item_seller_logo)
        ImageView mIvOrderItemSellerLogo;
        @InjectView(R.id.tv_order_item_seller_name)
        TextView mTvOrderItemSellerName;
        @InjectView(R.id.tv_order_item_type)
        TextView mTvOrderItemType;
        @InjectView(R.id.tv_order_item_time)
        TextView mTvOrderItemTime;
        @InjectView(R.id.tv_order_item_foods)
        TextView mTvOrderItemFoods;
        @InjectView(R.id.tv_order_item_money)
        TextView mTvOrderItemMoney;
        @InjectView(R.id.tv_order_item_multi_function)
        TextView mTvOrderItemMultiFunction;
        private Order mOrder;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.putExtra("orderId",mOrder.getId());
                    intent.putExtra("type", mOrder.getType());
                    mContext.startActivity(intent);
                }
            });
        }

        public void setData(Order order) {
            this.mOrder = order;
            mTvOrderItemSellerName.setText(order.getSeller().getName());
            mTvOrderItemType.setText(getOrderTypeInfo(order.getType()));
        }
    }

    private String getOrderTypeInfo(String type) {
        /**
         * 订单状态
         * 10 未支付 20 已提交订单 30 商家接单  40 配送中,等待送达 50已送达 60 取消的订单
         */
//            public static final String ORDERTYPE_UNPAYMENT = "10";
//            public static final String ORDERTYPE_SUBMIT = "20";
//            public static final String ORDERTYPE_RECEIVEORDER = "30";
//            public static final String ORDERTYPE_DISTRIBUTION = "40";
//            public static final String ORDERTYPE_SERVED = "50";
//            public static final String ORDERTYPE_CANCELLEDORDER = "60";

        String typeInfo = "";
        switch (type) {
            case OrderObservable.ORDERTYPE_UNPAYMENT:
                typeInfo = "未支付";
                break;
            case OrderObservable.ORDERTYPE_SUBMIT:
                typeInfo = "已提交订单";
                break;
            case OrderObservable.ORDERTYPE_RECEIVEORDER:
                typeInfo = "商家接单";
                break;
            case OrderObservable.ORDERTYPE_DISTRIBUTION:
                typeInfo = "配送中";
                break;
            case OrderObservable.ORDERTYPE_SERVED:
                typeInfo = "已送达";
                break;
            case OrderObservable.ORDERTYPE_CANCELLEDORDER:
                typeInfo = "取消的订单";
                break;
        }
        return typeInfo;
    }

}
