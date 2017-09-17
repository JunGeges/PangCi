package com.hasee.pangci.app;

import com.hasee.pangci.Common.Constant;

import org.litepal.LitePalApplication;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

/**
 * Created by 高俊 on 2017/9/9.
 */

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, Constant.APPLICATIONID);
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();
        BmobPush.startWork(this);
    }
}
