package com.hasee.pangci.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hasee.pangci.Common.Constant;
import com.hasee.pangci.R;
import com.hasee.pangci.bean.Resources;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date:2017/9/10
 * @author: 廖呈彪
 * @description:
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.viewHolder> {

    private ArrayList<Resources> mResourcesArrayList;
    private Context context;

    public CommonAdapter(ArrayList<Resources> vlist, Context context) {
        this.mResourcesArrayList = vlist;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    private String videoUrl;

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Resources resources = mResourcesArrayList.get(position);
        holder.tvLookNum.setText(resources.getContentLike());
        holder.tvTitle.setText(resources.getTitle());
        holder.updateTime.setText(resources.getCreatedAt());
        //根据httpsType区别请求头
        switch (resources.getHttpstype()) {
            case "0":
                //封面地址
                Glide.with(context).load(Constant.MOVIEZEROHEADERURL + resources.getCoverId()).error(R.drawable.icon_load_error).into(holder.ivCover);
                //视频地址
                videoUrl = Constant.MOVIEZEROHEADERURL + resources.getContentId();
                Log.i("TAG+++++++", videoUrl);
                break;
            case "1":
                Glide.with(context).load(Constant.MOVIEONEHEADERURL + resources.getCoverId()).error(R.drawable.icon_load_error).into(holder.ivCover);
                videoUrl = Constant.MOVIEONEHEADERURL + resources.getContentId();
                break;
        }


        holder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog hintDialog = new AlertDialog.Builder(context, R.style.ShowDialog).create();
                View inflate_dialog = LayoutInflater.from(context).inflate(R.layout.play_dialog_hint, null);
                hintDialog.show();
                hintDialog.setContentView(inflate_dialog);
                TextView tvCancel = (TextView) inflate_dialog.findViewById(R.id.tv_cancle);
                TextView tvConfirm = (TextView) inflate_dialog.findViewById(R.id.tv_comfirm);
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hintDialog.dismiss();
                    }
                });
                tvConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hintDialog.dismiss();
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(videoUrl);
                        intent.setData(content_url);
                        context.startActivity(intent);
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return mResourcesArrayList != null ? mResourcesArrayList.size() : 0;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;//封面
        @BindView(R.id.tv_look_num)
        TextView tvLookNum;//观看人数
        @BindView(R.id.tv_title)
        TextView tvTitle;//标题
        @BindView(R.id.update_time)
        TextView updateTime;//更新时间
        @BindView(R.id.iv_play)
        ImageView ivPlay;//播放按钮

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
