package com.hasee.pangci.Common;

import android.text.TextUtils;

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
}
