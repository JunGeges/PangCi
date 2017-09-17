package com.hasee.pangci.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class RecommendFragment extends Fragment {
    private static final String TAG = "RecommendFragment";
    @BindView(R.id.rl_video_list)
    RecyclerView rlVideoList;
    private ArrayList<Resources> mResourcesBeanArrayList = new ArrayList<>();
    private static final String RESOURCETYPE = "recommend";
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            ButterKnife.bind(this,mView);
            return mView;
        }
        mView = inflater.inflate(R.layout.recommend_fragment_layout, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated----");
        //查询推荐类的资源
        if (mResourcesBeanArrayList.size() == 0) {
            BmobQuery<Resources> bmobQuery = new BmobQuery<>();
            bmobQuery.addWhereEqualTo("ContentType", RESOURCETYPE);
            bmobQuery.findObjects(new FindListener<Resources>() {
                @Override
                public void done(List<Resources> list, BmobException e) {
                    if (e == null) {
                        for (int i = 0; i < list.size(); i++) {
                            Resources resourcesBean = list.get(i);
                            Log.i("TAGS//////***", list.get(i).toString());
                            mResourcesBeanArrayList.add(resourcesBean);
                        }
                        CommonAdapter adapter = new CommonAdapter(mResourcesBeanArrayList, getActivity());
                        rlVideoList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        rlVideoList.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), "非法操作,请重试!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
