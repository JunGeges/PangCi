package com.hasee.pangci.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;

/**
 * Created by Administrator on 2017/9/12.
 */

public class CommonUtils {
    //检查字符串是否为null or ＂＂
    public static boolean checkStrIsNull(String... str) {
        for (int i = 0; i < str.length; i++) {
            if (TextUtils.isEmpty(str[i])) {
                return true;
            }
        }
        return false;
    }

    //获取设备的唯一标识码IMEI
    public static String getPhoneImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    //dp转px
    public static int dp2px(int dp,Context context){
        return  (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }

    public static void openURL(String url,Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    public static ProgressDialog buildProgressDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }
}
