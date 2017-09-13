package com.hasee.pangci.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hasee.pangci.R;
import com.hasee.pangci.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MemberCenterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ll_zhifubao)
    LinearLayout llZhifubao;
    @BindView(R.id.ll_weixin)
    LinearLayout llWeixin;
    @BindView(R.id.member_center_tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.tv_user_name)
    TextView mUserNameTv;
    @BindView(R.id.tv_user_member_level)
    TextView mMemberLevelTv;
    @BindView(R.id.iv_user_icon)
    CircleImageView mHeadIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_center);
        ButterKnife.bind(this);
        getIntentData();
        llWeixin.setOnClickListener(this);
        llZhifubao.setOnClickListener(this);

        mToolbar.setTitle("会员中心");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "onClick: " + v.toString());
                finish();
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Log.i("intentData", intent.getFlags() + "");
        if (intent.getFlags() == 1) {
            Bundle bundle = intent.getExtras();
            User user = (User) bundle.getSerializable("user");
            mUserNameTv.setText(user.getUserAccount());
            mMemberLevelTv.setText("当前等级:" + user.getMemberLevel());
            mHeadIcon.setImageResource(user.getUserHeadImg());
        } else {
            mUserNameTv.setText("未登录");
            mMemberLevelTv.setText("请先登录");
            mHeadIcon.setImageResource(R.drawable.normal_login);
        }
    }

    private void showDialog(String tv1, String tv2) {

        final AlertDialog hintDialog = new AlertDialog.Builder(MemberCenterActivity.this, R.style.ShowDialog).create();
        View inflate_dialog = LayoutInflater.from(MemberCenterActivity.this).inflate(R.layout.pay_dialog_hint, null);
        hintDialog.show();
        hintDialog.setContentView(inflate_dialog);

        TextView tv_1 = (TextView) inflate_dialog.findViewById(R.id.tv_1);
        TextView tv_2 = (TextView) inflate_dialog.findViewById(R.id.tv_2);
        tv_1.setText(tv1);
        tv_2.setText(tv2);
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
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_weixin:
                showDialog("1.请关注微信公众号\"壹号电影", "2.点击该公众号菜单栏“胖次有你”找到充值一栏即可开通");
                break;

            case R.id.ll_zhifubao:
                showDialog("1.复制收款支付宝账号:15386492280", "2.转账支付请务必留言账号，收到后我们将在24小时内为留言账号开通会员");
                break;
        }
    }
}
