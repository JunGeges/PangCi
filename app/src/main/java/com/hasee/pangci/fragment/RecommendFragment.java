package com.hasee.pangci.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasee.pangci.R;

/**
 * Created by 高俊 on 2017/9/9.
 */

public class RecommendFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend_fragment_layout, container, false);
        return view;
    }
}
