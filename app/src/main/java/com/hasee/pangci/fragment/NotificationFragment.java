package com.hasee.pangci.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasee.pangci.Common.MessageEvent;
import com.hasee.pangci.R;
import com.hasee.pangci.adapter.NotificationAdapter;
import com.hasee.pangci.bean.NotificationBean;
import com.hasee.pangci.interfaces.RecyclerItemOnClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationFragment extends Fragment implements RecyclerItemOnClickListener{
    @BindView(R.id.notification_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.notification_fab)
    FloatingActionButton mFloatingActionButton;
    private ArrayList<NotificationBean> notificationBeanArrayList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_fragment_layout,container,false);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mRecyclerView,"长按删除消息!",Snackbar.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (notificationBeanArrayList.size()==0){
            List<NotificationBean> all = NotificationBean.findAll(NotificationBean.class);
            notificationBeanArrayList.addAll(all);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            notificationAdapter = new NotificationAdapter(notificationBeanArrayList,getActivity());
            mRecyclerView.setAdapter(notificationAdapter);
            notificationAdapter.setRecyclerItemOnClickListener(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(MessageEvent messageEvent){
        if(!messageEvent.getFlag().equals("receiver")){
            return;
        }
        //再查一次数据库
        notificationBeanArrayList.clear();
        List<NotificationBean> all = NotificationBean.findAll(NotificationBean.class);
        notificationBeanArrayList.addAll(all);
        notificationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(int position) {
        NotificationBean notificationBean = notificationBeanArrayList.get(position);
        NotificationBean.delete(NotificationBean.class,notificationBean.getId());
        notificationBeanArrayList.remove(position);
        notificationAdapter.notifyDataSetChanged();
    }
}
