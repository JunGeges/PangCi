package com.hasee.pangci.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasee.pangci.R;
import com.hasee.pangci.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 高俊 on 2017/9/9.
 */

public class MovieFragment extends BaseFragment {
    @BindView(R.id.rl_video_list)
    RecyclerView rlVideoList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        List<String> list=new ArrayList<>();
        for (int i = 0; i <18 ; i++) {
            list.add("dd");
        }
        //
        CommonAdapter adapter=new CommonAdapter(list,getActivity());
        rlVideoList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rlVideoList.setAdapter(adapter);
    }
}
