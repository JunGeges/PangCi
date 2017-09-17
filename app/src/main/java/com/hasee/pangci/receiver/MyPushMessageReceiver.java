package com.hasee.pangci.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hasee.pangci.Common.MessageEvent;
import com.hasee.pangci.R;
import com.hasee.pangci.activity.LoginActivity;
import com.hasee.pangci.activity.NotificationActivity;
import com.hasee.pangci.bean.NotificationBean;
import com.hasee.pangci.bean.User;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;

/**
 * Created by Administrator on 2017/9/14.
 */

public class MyPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Log.i("bmob", "客户端收到推送内容：" + intent.getStringExtra("msg"));
            try {
                JSONObject jsonObject = new JSONObject(intent.getStringExtra("msg"));
                String content = jsonObject.getString("alert");
                buildNotification(context, content);

                //数据插到本地数据库
                NotificationBean notificationBean = new NotificationBean();
                notificationBean.setNotificationContent(content);
                notificationBean.save();
                EventBus.getDefault().post(new MessageEvent(new User(), "receiver"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void buildNotification(Context context, String str) {
        Intent intent = null;
        SharedPreferences login_info = context.getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        if (login_info.getBoolean("isLogin", false)) {
            intent = new Intent(context, NotificationActivity.class);
        } else {
            intent = new Intent(context, LoginActivity.class);
        }
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("会员信息通知!")
                .setContentText(str)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                .setContentIntent(pi)
                .setAutoCancel(true)//设置点击后消失
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        manager.notify(1, notification);
    }
}
