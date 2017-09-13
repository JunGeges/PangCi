package com.hasee.pangci.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hasee.pangci.R;
import com.hasee.pangci.Common.MessageEvent;
import com.hasee.pangci.bean.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.login_confirm_tv)
    TextView mLoginTextView;
    @BindView(R.id.login_account_et)
    EditText mAccountEditText;
    @BindView(R.id.login_password_et)
    EditText mPassWordEditText;
    @BindView(R.id.login_not_account_tv)
    TextView mNotAccountTextView;
    private ProgressDialog mProgressDialog;
    @BindView(R.id.login_tool_bar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mLoginTextView.setOnClickListener(this);
        mNotAccountTextView.setOnClickListener(this);
        mToolbar.setTitle("登录");
        mToolbar.inflateMenu(R.menu.login_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(LoginActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
                return false;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_confirm_tv:
                //登录成功跳主页
                checkIsExitUser();
                break;

            case R.id.login_not_account_tv:
                //没有账号跳注册
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void checkIsExitUser() {
        //判断是否存在此用户
        String account = mAccountEditText.getText().toString();
        String password = mPassWordEditText.getText().toString();

        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userAccount", account);
        bmobQuery.addWhereEqualTo("userPassword", password);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        //保存到sp 下次进来直接登录
                        SharedPreferences login_info = getSharedPreferences("LOGIN_INFO", MODE_PRIVATE);
                        SharedPreferences.Editor edit = login_info.edit();
                        for (int i = 0; i < list.size(); i++) {//数据唯一 注册做了限制
                            User userTemp = list.get(i);
                            //查下来得区分是否是充值会员 充值会员有时间
                            if (userTemp.getMemberLevel().equals("青铜")) {
                                //青铜会员
                                //存进sp
                                edit.putInt("headImg", userTemp.getUserHeadImg());
                                edit.putString("account", userTemp.getUserAccount());
                                edit.putString("password", userTemp.getUserPassword());
                                edit.putString("memberLevel", userTemp.getMemberLevel());
                                //存储登录状态
                                edit.putBoolean("isLogin",true);
                                edit.apply();
                            } else {
                                //黄金 白金 钻石会员
                                //存进sp
                                edit.putInt("headImg", userTemp.getUserHeadImg());
                                edit.putString("account", userTemp.getUserAccount());
                                edit.putString("password", userTemp.getUserPassword());
                                edit.putString("memberLevel", userTemp.getMemberLevel());
                                edit.putString("memberStartDate", userTemp.getMemberStartDate().getDate());
                                edit.putString("memberEndDate", userTemp.getMemberEndDate().getDate());
                                //存储登录状态
                                edit.putBoolean("isLogin",true);
                                edit.apply();
                            }
                            EventBus.getDefault().post(new MessageEvent(userTemp));
                        }
                        finish();
                        //不显示不知道为啥？？？
 /*                       runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initProgressDialog();
                            }
                        });*/
                    } else {
                        Toast.makeText(LoginActivity.this, "账号或密码输入错误!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("TAG", "error: " + e.getMessage());
                    Toast.makeText(LoginActivity.this, "非法操作,请重试" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1)
    public void handleEvent(MessageEvent event) {
        Log.i("LOGIN_TAG", event.getUser().toString());
        EventBus.getDefault().cancelEventDelivery(event);
        //获取来自注册页面的数据
        User userNew = event.getUser();//刚注册的用户
        mAccountEditText.setText(userNew.getUserAccount());
        mPassWordEditText.setText(userNew.getUserPassword());
        checkIsExitUser();
    }

    private void initProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage("正在登录...");
        }
        mProgressDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
