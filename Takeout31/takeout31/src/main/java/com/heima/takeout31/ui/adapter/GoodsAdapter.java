package com.heima.takeout31.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.takeout31.R;
import com.heima.takeout31.model.dao.CacheSelectedInfo;
import com.heima.takeout31.model.net.GoodsInfo;
import com.heima.takeout31.model.net.GoodsTypeInfo;
import com.heima.takeout31.ui.activity.BusinessActivity;
import com.heima.takeout31.ui.fragment.GoodsFragment;
import com.heima.takeout31.util.Constants;
import com.heima.takeout31.util.PriceFormater;
import com.heima.takeout31.util.TakeoutApp;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by lidongzhi on 2016/12/10.
 */

public class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private GoodsFragment mGoodsFragment;
    private Context mContext;
    private List<GoodsInfo> mGoodsInfoList;

    public GoodsAdapter(Context context, GoodsFragment fragment) {
        mContext = context;
        this.mGoodsFragment = fragment;
    }

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        mGoodsInfoList = goodsInfoList;
    }

    @Override
    public int getCount() {
        if (mGoodsInfoList != null) {
            return mGoodsInfoList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mGoodsInfoList != null) {
            return mGoodsInfoList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_goods, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(mGoodsInfoList.get(position));
        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View headView = View.inflate(mContext,R.layout.item_type_header,null);
        //TODO:当条目为头部的时候，名字应该是从左侧列表中取出
        GoodsInfo goodsInfo = mGoodsInfoList.get(position);
        ((TextView) headView).setText(goodsInfo.getTypeName());
        return headView;
    }

    @Override
    public long getHeaderId(int position) {
        GoodsInfo goodsInfo = mGoodsInfoList.get(position);
        return goodsInfo.getTypeId();
    }

    class ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView mIvIcon;
        @InjectView(R.id.tv_name)
        TextView mTvName;
        @InjectView(R.id.tv_zucheng)
        TextView mTvZucheng;
        @InjectView(R.id.tv_yueshaoshounum)
        TextView mTvYueshaoshounum;
        @InjectView(R.id.tv_newprice)
        TextView mTvNewprice;
        @InjectView(R.id.tv_oldprice)
        TextView mTvOldprice;
        @InjectView(R.id.ib_minus)
        ImageButton mIbMinus;
        @InjectView(R.id.tv_count)
        TextView mTvCount;
        @InjectView(R.id.ib_add)
        ImageButton mIbAdd;
        private GoodsInfo mGoodsInfo;

        @OnClick({R.id.ib_add,R.id.ib_minus})
        public void onClick(View view){
           boolean isAdd = false;
           switch (view.getId()){
               case R.id.ib_add:
                   doAddOperation();
                   isAdd = true;
                   break;
               case R.id.ib_minus:
                   doMinusOperation();
                   break;
           }
            //3.更改红点数量
            int typeId = mGoodsInfo.getTypeId();
            //找到在左侧列表中该typeId对应的postion
            int position = mGoodsFragment.mGoodsFragmentPresenter.getTypePositionByTypeId(typeId);
            GoodsTypeInfo goodsTypeInfo =  mGoodsFragment.mGoodsFragmentPresenter.mGoodsTypeInfoList.get(position);
            int redDotCount = goodsTypeInfo.getCount();
            if(isAdd){
                redDotCount++;
            }else{
                redDotCount--;
            }
            goodsTypeInfo.setCount(redDotCount);
            mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();

            //4.更改购物车下方展示栏效果
            //关键点商品的count值大于0为购物车中,使用presenter获取购物车商品
            ((BusinessActivity) mGoodsFragment.getActivity()).mBusinessActivityPresenter.updateCartData();

        }

        private void doMinusOperation() {
            //改变数据
            int count = mGoodsInfo.getCount();
            if(count == 1){
                mGoodsInfo.setCount(0);
                mTvCount.setText("0");
                //从无到有执行动画
                AnimationSet animationSet = getHideAnimation();
                mIbMinus.startAnimation(animationSet);
                mTvCount.startAnimation(animationSet);
                //只有一个，删除缓存信息
                TakeoutApp.sInstance.deleteCacheSelectedInfo(mGoodsInfo.getId());
            }else{
                count--;
                mGoodsInfo.setCount(count);
                mTvCount.setText(count + "");
                //更新缓存信息
                TakeoutApp.sInstance.updateCacheSelectedInfo(mGoodsInfo.getId(), Constants.MINUS);
            }
            notifyDataSetChanged();

            //
        }

        private AnimationSet getHideAnimation() {
            AnimationSet animationSet = new AnimationSet(false);
            Animation alphaAnimation = new AlphaAnimation(1,0);
            animationSet.addAnimation(alphaAnimation);
            RotateAnimation rotateAnimation = new RotateAnimation(720,0,Animation.RELATIVE_TO_SELF,0.5F,
                    Animation.RELATIVE_TO_SELF,0.5F);
            animationSet.addAnimation(rotateAnimation);
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,0,
                    Animation.RELATIVE_TO_SELF,2,
                    Animation.RELATIVE_TO_SELF,0,
                    Animation.RELATIVE_TO_SELF,0);
            animationSet.addAnimation(translateAnimation);
            animationSet.setDuration(500);
            return animationSet;
        }

        private void doAddOperation() {
            //改变数据
            int count = mGoodsInfo.getCount();
            if(count == 0){
                mGoodsInfo.setCount(1);
                mTvCount.setText("1");
                //从无到有执行动画
                AnimationSet animationSet = getShowAnimation();
                mIbMinus.startAnimation(animationSet);
                mTvCount.startAnimation(animationSet);

                //新增一个缓存信息
                TakeoutApp.sInstance.addCacheSelectedInfo(new CacheSelectedInfo(
                        mGoodsInfo.getSellerId(),mGoodsInfo.getTypeId(),mGoodsInfo.getId(), 1));
            }else{
                count++;
                mGoodsInfo.setCount(count);
                mTvCount.setText(count + "");

                //更新缓存信息
                TakeoutApp.sInstance.updateCacheSelectedInfo(mGoodsInfo.getId(), Constants.ADD);
            }
            notifyDataSetChanged();

            //TODO:在原来加号的位置再动态添加一个+号，新的与旧的大小一样，样式，位置完全一样，
            ImageButton ib = new ImageButton(mContext);
            ib.setBackgroundResource(R.drawable.button_add);
            //TODO:不能直接使用getX()/getY(),getX()时获取相对于父view的距离
            int[] location = new int[2];
            mIbAdd.getLocationInWindow(location);
            ib.setX(location[0]);
            ib.setY(location[1]);
            ((BusinessActivity) mGoodsFragment.getActivity()).addImageButton(ib,mIbAdd.getWidth(),mIbAdd.getHeight());
            int[] destLocation = ((BusinessActivity) mGoodsFragment.getActivity()).getImgCartLocation();
            AnimationSet animationSet = getParabolaAnimation(ib,location, destLocation);
            ib.startAnimation(animationSet);
        }

        private AnimationSet getParabolaAnimation(final ImageButton ib, int[] srcLocation, int[] destLocation) {
            AnimationSet animationSet = new AnimationSet(false);
            TranslateAnimation translateAnimationX = new TranslateAnimation(
                    Animation.ABSOLUTE,0,
                    Animation.ABSOLUTE,destLocation[0]-srcLocation[0],
                    Animation.ABSOLUTE,0,
                    Animation.ABSOLUTE,0);
            animationSet.addAnimation(translateAnimationX);
            //要使用delta值，使用 destLocation - srcLocation写法
            TranslateAnimation translateAnimationY = new TranslateAnimation(
                    Animation.ABSOLUTE,0,
                    Animation.ABSOLUTE,0,
                    Animation.ABSOLUTE,0,
                    Animation.ABSOLUTE,destLocation[1] - srcLocation[1]);
            translateAnimationY.setInterpolator(new AccelerateInterpolator());
            animationSet.addAnimation(translateAnimationY);
            animationSet.setDuration(500);
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //移除+号
                    ((BusinessActivity) mGoodsFragment.getActivity()).removeImageButton(ib);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            return animationSet;
        }

        private AnimationSet getShowAnimation() {
            AnimationSet animationSet = new AnimationSet(false);
            Animation alphaAnimation = new AlphaAnimation(0,1);
            animationSet.addAnimation(alphaAnimation);
            RotateAnimation rotateAnimation = new RotateAnimation(0,720,Animation.RELATIVE_TO_SELF,0.5F,
                    Animation.RELATIVE_TO_SELF,0.5F);
            animationSet.addAnimation(rotateAnimation);
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,2,
                    Animation.RELATIVE_TO_SELF,0,
                    Animation.RELATIVE_TO_SELF,0,
                    Animation.RELATIVE_TO_SELF,0);
            animationSet.addAnimation(translateAnimation);
            animationSet.setDuration(500);
            return animationSet;
        }

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

        public void setData(GoodsInfo goodsInfo) {
            this.mGoodsInfo = goodsInfo;
            mTvName.setText(goodsInfo.getName());
            Picasso.with(mContext).load(goodsInfo.getIcon()).into(mIvIcon);
            mTvZucheng.setText(goodsInfo.getForm());
            mTvYueshaoshounum.setText("月售" + goodsInfo.getMonthSaleNum() + "份");
            //根据手机设置，决定显示的货币符号
            mTvNewprice.setText(PriceFormater.format(goodsInfo.getNewPrice()));
            if(goodsInfo.getOldPrice()>0) {
                mTvOldprice.setText(PriceFormater.format(goodsInfo.getOldPrice()));
                mTvOldprice.setTypeface(Typeface.DEFAULT_BOLD);
                mTvOldprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                mTvOldprice.setVisibility(View.GONE);
            }

            mTvCount.setText(goodsInfo.getCount() + "");
            if(goodsInfo.getCount()>0){
                mTvCount.setVisibility(View.VISIBLE);
                mIbMinus.setVisibility(View.VISIBLE);
            }else{
                mTvCount.setVisibility(View.INVISIBLE);
                mIbMinus.setVisibility(View.INVISIBLE);
            }
        }
    }


}
