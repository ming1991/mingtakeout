package com.heima.takeout31.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heima.takeout31.R;

/**
 * Created by lidongzhi on 2016/12/7.
 */
public class MoreFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View orderView = View.inflate(getContext(), R.layout.fragment_, null);
        ((TextView) orderView.findViewById(R.id.tv)).setText("更多");
        return orderView;
    }
}
