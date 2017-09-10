package com.hasee.pangci.app;

import android.app.Application;

import com.hasee.pangci.Utils.Constant;

import cn.bmob.v3.Bmob;

/**
 * Created by 高俊 on 2017/9/9.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, Constant.APPLICATIONID);
    }
}
