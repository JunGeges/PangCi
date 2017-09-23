package com.hasee.pangci.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hasee.pangci.Common.CommonUtils;
import com.hasee.pangci.Common.Constant;
import com.hasee.pangci.R;
import com.hasee.pangci.bean.Resources;
import com.hasee.pangci.interfaces.RecyclerItemOnClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.viewHolder> {
    private ArrayList<Resources> mResourcesBeanArrayList;
    private Context context;
    private String videoUrl;//视频地址
    private RecyclerItemOnClickListener mRecyclerItemOnClickListener;

    public CommonAdapter(ArrayList<Resources> vlist, Context context) {
        this.mResourcesBeanArrayList = vlist;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        if (position == mResourcesBeanArrayList.size() - 1 || position == mResourcesBeanArrayList.size() - 2) {
            //给最后一个view添加底部margin
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CommonUtils.dp2px(160,context),CommonUtils.dp2px(200,context));
            layoutParams.setMargins(CommonUtils.dp2px(10,context), CommonUtils.dp2px(10,context), 0, CommonUtils.dp2px(10,context));
            holder.itemView.setLayoutParams(layoutParams);

        }
        final Resources resourcesBean = mResourcesBeanArrayList.get(position);
        holder.tvLookNum.setText(resourcesBean.getContentLike());
        holder.tvTitle.setText(resourcesBean.getTitle());
        holder.updateTime.setText(resourcesBean.getCreatedAt());
        //根据coverhttptype区分图片请求头
        switch (resourcesBean.getCoverhttptype()) {
            case "0":
                //封面地址
                Glide.with(context).load(Constant.ICONZEROHEADERURL + resourcesBean.getCover()).placeholder(R.drawable.vip).error(R.drawable.ic_load_empty).into(holder.ivCover);
                break;

            case "1":
                Glide.with(context).load(resourcesBean.getCover()).error(R.drawable.ic_load_empty).into(holder.ivCover);
                break;
        }


        holder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences login_info = context.getSharedPreferences("LOGIN_INFO", MODE_PRIVATE);
                String mMemberLevel = login_info.getString("memberLevel", "青铜");
                boolean isLogin = login_info.getBoolean("isLogin", false);
                Resources resource = mResourcesBeanArrayList.get(position);
                //根据httpsType区别视频请求头
                switch (resource.getHttpstype()) {
                    case "0":
                        //视频地址
                        videoUrl = Constant.MOVIEZEROHEADERURL + resourcesBean.getContentId();
                        break;
                    case "1":
                        videoUrl = Constant.MOVIEONEHEADERURL + resourcesBean.getContentId();
                        break;
                    case "2":
                        videoUrl = Constant.WPHEADERURL + resourcesBean.getContentId();
                        break;
                }
                //判断是否已经登录
                if (!isLogin) {
                    buildDialog("请登录之后观看!");
                    return;
                }
                //判断是否是免费还是收费
                if (resourcesBean.getAuthority().equals("common")) {
                    //免费直接看
                    CommonUtils.openURL(videoUrl,context);
                    Log.i("Adapter", position + "---" + videoUrl);
                } else {
                    //1.收费
                    //2.看是否是付费会员
                    if (mMemberLevel.equals("青铜")) {
                        //没付费会员提示开通会员
                        buildDialog("请先升级更高级会员观看!");
                    } else {
                        //付费会员直接看 看会员是否到期
                        if (login_info.getBoolean("isExpire", false)) {
                            buildDialog("您的会员已到期,请续费后观看!");
                            return;
                        }
                        CommonUtils.openURL(videoUrl,context);
                        Log.i("Adapter", position + "---" + videoUrl);
                    }
                }
            }
        });

    }

    private void buildDialog(String promptContent) {
        final AlertDialog hintDialog = new AlertDialog.Builder(context, R.style.ShowDialog).create();
        View inflate_dialog = LayoutInflater.from(context).inflate(R.layout.play_dialog_hint, null);
        hintDialog.show();
        hintDialog.setContentView(inflate_dialog);
        TextView tvContent = (TextView) inflate_dialog.findViewById(R.id.tv_content);
        tvContent.setText(promptContent);
        TextView tvConfirm = (TextView) inflate_dialog.findViewById(R.id.tv_comfirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResourcesBeanArrayList != null ? mResourcesBeanArrayList.size() : 0;
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

    public void setRecyclerItemOnClickListener(RecyclerItemOnClickListener itemOnClickListener) {
        mRecyclerItemOnClickListener = itemOnClickListener;
    }

}
