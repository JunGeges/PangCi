package com.hasee.pangci.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasee.pangci.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date:2017/9/10
 * @author: 廖呈彪
 * @description:
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.viewHolder> {

    private List vlist;
    private Context context;


    public CommonAdapter(List vlist,Context context) {
        this.vlist = vlist;
        this.context=context;


    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, null);
        viewHolder holder = new viewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog hintDialog = new AlertDialog.Builder(context,R.style.ShowDialog).create();
                View inflate_dialog = LayoutInflater.from(context).inflate(R.layout.play_dialog_hint, null);
                hintDialog.show();
                hintDialog.setContentView(inflate_dialog);
                TextView tvCancle = (TextView) inflate_dialog.findViewById(R.id.tv_cancle);
                TextView tvComfirm = (TextView) inflate_dialog.findViewById(R.id.tv_comfirm);
                tvCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hintDialog.dismiss();
                    }
                });
                tvComfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hintDialog.dismiss();
                        Intent intent= new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("https://bobo.kkpp.space/share/tXmx9kdJHd469UTv");
                        intent.setData(content_url);
                        context.startActivity(intent);

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return vlist != null ? vlist.size() : 0;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_look_num)
        TextView tvLookNum;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.update_time)
        TextView updateTime;
        @BindView(R.id.iv_play)
        ImageView ivPlay;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
