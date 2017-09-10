package com.hasee.pangci.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hasee.pangci.R;

import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private Toast mToast = null;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
        initEvent();
        initData();
    }

    public abstract void initView();

    public abstract void initEvent();

    public abstract void initData();

    public void showToast(String str) {
        if (mToast == null) {
           mToast=Toast.makeText(this,str,Toast.LENGTH_LONG);
        }else {
            mToast.setText(str);
        }
        mToast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
