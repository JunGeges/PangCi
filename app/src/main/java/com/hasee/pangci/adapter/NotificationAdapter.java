package com.hasee.pangci.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hasee.pangci.R;
import com.hasee.pangci.bean.NotificationBean;
import com.hasee.pangci.interfaces.RecyclerItemOnClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 高俊 on 2017/9/16.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<NotificationBean> notificationBeanArrayList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public NotificationAdapter(ArrayList<NotificationBean> notificationBeanArrayList, Context context) {
        this.notificationBeanArrayList = notificationBeanArrayList;
        this.mContext = context;
        mLayoutInflater=LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_notification_recycler_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        NotificationBean notificationBean = notificationBeanArrayList.get(position);
        holder.mContentTv.setText(notificationBean.getNotificationContent());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                recyclerItemOnClickListener.onItemClick(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationBeanArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_content_tv)
        TextView mContentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private RecyclerItemOnClickListener recyclerItemOnClickListener;

    public void setRecyclerItemOnClickListener(RecyclerItemOnClickListener recyclerItemOnClickListener){
        this.recyclerItemOnClickListener=recyclerItemOnClickListener;
    }
}
