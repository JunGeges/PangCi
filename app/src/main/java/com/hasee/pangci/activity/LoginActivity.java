package com.hasee.pangci.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hasee.pangci.R;
import com.hasee.pangci.bean.User;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.login_forget_tv)
    TextView mForgetTextView;
    @BindView(R.id.login_not_account_tv)
    TextView mNotAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginTextView.setOnClickListener(this);
        mForgetTextView.setOnClickListener(this);
        mNotAccountTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_confirm_tv:
                //登录
                checkIsRegister();
                break;

            case R.id.login_not_account_tv:
                //没有账号
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;

            case R.id.login_forget_tv:
                //忘记密码
                Toast.makeText(this, "正在开发!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void checkIsRegister() {
        //判断是否存在此用户
        final String account = mAccountEditText.getText().toString();
        final String password = mPassWordEditText.getText().toString();
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userAccount", account);
        bmobQuery.addWhereEqualTo("userPassword", password);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        for (int i = 0; i < list.size(); i++) {
                            User userTemp = list.get(i);
                            User user = new User(userTemp.getUserAccount(), userTemp.getUserPassword(), userTemp.getUserRegisterTime(), userTemp.getUserHeadImg(), userTemp.getMemberLevel(), userTemp.getMemberStartDate(), userTemp.getMemberEndDate());
                            EventBus.getDefault().post(user);
                        }
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "账号或密码输入错误!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("TAG", "error: "+e.getMessage());
                    Toast.makeText(LoginActivity.this, "非法操作" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
