package com.heima.takeout31;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.heima.takeout31.ui.fragment.HomeFragment;
import com.heima.takeout31.ui.fragment.MoreFragment;
import com.heima.takeout31.ui.fragment.OrderFragment;
import com.heima.takeout31.ui.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_fragment_container)
    FrameLayout mMainFragmentContainer;
    @InjectView(R.id.main_bottome_switcher_container)
    LinearLayout mMainBottomeSwitcherContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initFragments();
        initBottom();
        changeUi(0);
    }

    List<Fragment> mFragmentList = new ArrayList<>();
    private void initFragments() {
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new OrderFragment());
        mFragmentList.add(new UserFragment());
        mFragmentList.add(new MoreFragment());
    }

    private void initBottom() {
        //选中的为蓝色（禁用），没选的为黑色（启用）
        int count = mMainBottomeSwitcherContainer.getChildCount();
        for(int i=0;i<count;i++){
            final View child = mMainBottomeSwitcherContainer.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = mMainBottomeSwitcherContainer.indexOfChild(child);
                    changeUi(index);
                }
            });
        }
    }

    /**
     * 切换到第N个孩子
     * @param index
     */
    private void changeUi(int index) {
        //选中的为蓝色（禁用），没选的为黑色（启用）
        int count = mMainBottomeSwitcherContainer.getChildCount();
        for(int i=0;i<count;i++){
            View child = mMainBottomeSwitcherContainer.getChildAt(i);
            if(i == index){
                //选中的孩子，居然禁用了
                setEnable(child, false);
            }else{
                //被抛弃的孩子,反而启用
                setEnable(child, true);
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,mFragmentList.get(index)).commit();
    }

    private void setEnable(View child, boolean isEnable) {
        child.setEnabled(isEnable);
        if(child instanceof ViewGroup) {
            ViewGroup selectChild = (ViewGroup) child;
            int count = selectChild.getChildCount();
            for(int j=0;j<count;j++){
               View item = selectChild.getChildAt(j);
                setEnable(item, isEnable);
            }
        }
    }


}
