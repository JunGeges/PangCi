package com.hasee.pangci.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hasee.pangci.R;
import com.hasee.pangci.adapter.CommonAdapter;
import com.hasee.pangci.bean.Resources;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 高俊 on 2017/9/9.
 */

public class RecommendFragment extends BaseFragment {

    @BindView(R.id.rl_video_list)
    RecyclerView rlVideoList;
    private ArrayList<Resources> mResourcesArrayList = new ArrayList<>();
    private static final String RESOURCETYPE = "recommend";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            //查询推荐类的资源
            BmobQuery<Resources> bmobQuery = new BmobQuery<>();
            bmobQuery.addWhereEqualTo("ContentType", RESOURCETYPE);
            bmobQuery.findObjects(new FindListener<Resources>() {
                @Override
                public void done(List<Resources> list, BmobException e) {
                    if (e == null) {
                        for (int i = 0; i < list.size(); i++) {
                            Resources resources = list.get(i);
                            Log.i("TAGS//////***", list.get(i).toString());
                            mResourcesArrayList.add(resources);
                        }
                    } else {
                        Toast.makeText(getActivity(), "非法操作,请重试!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            CommonAdapter adapter = new CommonAdapter(mResourcesArrayList, getActivity());
            rlVideoList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rlVideoList.setAdapter(adapter);
    }
}
